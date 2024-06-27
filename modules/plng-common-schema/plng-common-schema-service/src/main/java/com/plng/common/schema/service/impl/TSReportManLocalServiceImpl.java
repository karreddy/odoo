/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.plng.common.schema.service.impl;

import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.plng.common.schema.model.TSReportMan;
import com.plng.common.schema.model.TimeSheet;
import com.plng.common.schema.service.TimeSheetLocalService;
import com.plng.common.schema.service.base.TSReportManLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.TSReportMan",
	service = AopService.class
)
public class TSReportManLocalServiceImpl
	extends TSReportManLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(TSReportManLocalServiceImpl.class);
	
	@Override
	public TSReportMan saveTSReportMan(long empId, long supId, long mon, long year,float claim_amt, long status, Date subDate ) {
		TSReportMan tSReportMan=null;
		try {
			long tsId = counterLocalService.increment(TSReportMan.class.getName());
			tSReportMan = tsReportManPersistence.create(tsId);
			tSReportMan.setEmpid(empId);
			tSReportMan.setSupId(supId);
			tSReportMan.setMon(mon);
			tSReportMan.setYear(year);
			tSReportMan.setClaim_amt(claim_amt);
			tSReportMan.setSubDate(subDate);
			tSReportMan.setStatus(status);
			
			tsReportManPersistence.update(tSReportMan);
			
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return tSReportMan;
	}
	
	public  TSReportMan getMonAndYear(long empId,int month,int year) {
	    try {
	        return  tsReportManPersistence.findByEmpIdMonYear(empId, month, year);
	    } catch (Exception e) {
	        _log.error("Error while fetching data: " + e.getMessage());
	        return null;
	    }
	}
	
	public  List<TSReportMan> getRecordsByStatus(long status) {
	    try {
	        return tsReportManPersistence.findByStatus(status);
	    } catch (Exception e) {
	        _log.debug("Error while fetching data: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	public TSReportMan updateTsReportManStatus(long id, long userId, int status, ServiceContext serviceContext) {
		TSReportMan tSReportMan = null;
		List<TimeSheet> getmonthlyDetails=null;
		try {
			tSReportMan= tsReportManPersistence.findByPrimaryKey(id);
		} catch (Exception e) {
			 _log.debug(e.getMessage());
		}

		if (Validator.isNotNull(tSReportMan)) {
			tSReportMan.setStatus(status);
			long mon = tSReportMan.getMon();
			long year = tSReportMan.getYear();
			long empId = tSReportMan.getEmpid();
			getmonthlyDetails = _timeSheetLocalService.getmonthlyDetails(empId,(int) mon,(int) year);
			for (TimeSheet timeSheet : getmonthlyDetails) {
				timeSheet.setStatus(status);
				timeSheetPersistence.update(timeSheet);
			}
			tsReportManPersistence.update(tSReportMan);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				AssetEntryLocalServiceUtil.updateEntry(TSReportMan.class.getName(), id, new Date(), null, true, true);
			} else {
				AssetEntryLocalServiceUtil.updateVisible(TSReportMan.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e.getMessage());
		}
		return tSReportMan;
	}
	@Reference private  TimeSheetLocalService _timeSheetLocalService;
}