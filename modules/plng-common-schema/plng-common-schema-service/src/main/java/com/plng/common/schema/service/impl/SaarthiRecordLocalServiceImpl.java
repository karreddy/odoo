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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.plng.common.schema.model.SaarthiRecord;
import com.plng.common.schema.service.base.SaarthiRecordLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.SaarthiRecord",
	service = AopService.class
)
public class SaarthiRecordLocalServiceImpl
	extends SaarthiRecordLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(SaarthiRecordLocalServiceImpl.class);

	public SaarthiRecord saveSaarthiRecord(long userId,String userName,String month,String year,String totalHoliday,String totalExtHrs,String totalFood,String grandTotal,String reportingManager,int status) {
		
		SaarthiRecord saarthiRecord = null;
		try {
			long recordId = counterLocalService.increment(SaarthiRecord.class.getName());
			  saarthiRecord = saarthiRecordPersistence.create(recordId);
			if(saarthiRecord !=null) {
				saarthiRecord.setUserId(userId);
				saarthiRecord.setUserName(userName);
				saarthiRecord.setMonth(month);
				saarthiRecord.setYear(year);
				saarthiRecord.setTotalHoliday(totalHoliday);
				saarthiRecord.setTotalExtHrs(totalExtHrs);
				saarthiRecord.setTotalFood(totalFood);
				saarthiRecord.setGrandTotal(grandTotal);
				saarthiRecord.setReportingManager(reportingManager);
				saarthiRecord.setStatus(status);
				
				saarthiRecordLocalService.updateSaarthiRecord(saarthiRecord);
			}
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return saarthiRecord;
	}
	
	public SaarthiRecord updateSaarthiStatus(long id, long userId, int status, ServiceContext serviceContext) {
		SaarthiRecord saarthiRecord = null;
		try {
			saarthiRecord=saarthiRecordPersistence.findByPrimaryKey(id);
		} catch (Exception e) {
			 _log.debug(e.getMessage());
		}

		if (Validator.isNotNull(saarthiRecord)) {
			saarthiRecord.setStatus(status);
			saarthiRecord = saarthiRecordPersistence.update(saarthiRecord);
		}

		try {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				assetEntryLocalService.updateEntry(SaarthiRecord.class.getName(), id, new Date(), null, true, true);
			} else {
				assetEntryLocalService.updateVisible(SaarthiRecord.class.getName(), id, false);
			}
		} catch (Exception e) {
			 _log.error(e.getMessage());
		}

		return saarthiRecord;
	}
	
	public SaarthiRecord getMonAndYear(Long userId,String month,String year) {
	    try {
	        return  saarthiRecordPersistence.findBySaarthiBYUserMonAndYear(userId, year, month);
	    } catch (Exception e) {
	        _log.error("Error while fetching data: " + e.getMessage());
	        return null;
	    }
	}
	
	public  List<SaarthiRecord> getRecordsByStatus(long status) {
	    try {
	        return saarthiRecordPersistence.findByStatus(status);
	    } catch (Exception e) {
	        _log.debug("Error while fetching data: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
}