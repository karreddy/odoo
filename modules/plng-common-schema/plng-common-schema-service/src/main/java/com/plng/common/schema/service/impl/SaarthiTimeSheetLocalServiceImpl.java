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
import com.plng.common.schema.model.SaarthiTimeSheet;
import com.plng.common.schema.service.base.SaarthiTimeSheetLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.SaarthiTimeSheet",
	service = AopService.class
)
public class SaarthiTimeSheetLocalServiceImpl
	extends SaarthiTimeSheetLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(SaarthiTimeSheetLocalServiceImpl.class);

	public SaarthiTimeSheet saveTimeSheet(long userId,String date,String month,String year ,String inTime,String outTime,String totalHrs,String purpose,String holiday,String extHours,boolean breakFast,boolean dinner,String totalHoliday,String totalExtHrs,String totalFood,String grandTotal,String reportingManager) {
		SaarthiTimeSheet saarthiTimeSheet = null;
		List<SaarthiTimeSheet> saarthiBybydateList =null;
		try {
			 saarthiBybydateList = saarthiTimeSheetPersistence.findByUserIdAndDate(userId, date);
			if(!saarthiBybydateList.isEmpty()) {
			 _log.info("entry found, updating entry. >>>");
			 saarthiTimeSheet=saarthiBybydateList.get(0);
			saarthiTimeSheet.setModifiedDate(new Date());
			saarthiTimeSheet.setMonth(month);
			saarthiTimeSheet.setYear(year);
			saarthiTimeSheet.setInTime(inTime);
			saarthiTimeSheet.setOutTime(outTime);
			saarthiTimeSheet.setTotalHrs(totalHrs);
			saarthiTimeSheet.setPurpose(purpose);
			saarthiTimeSheet.setHoliday(holiday);
			saarthiTimeSheet.setExtHours(extHours);
			saarthiTimeSheet.setBreakFast(breakFast);
			saarthiTimeSheet.setDinner(dinner);
			saarthiTimeSheet.setTotalHoliday(totalHoliday);
			saarthiTimeSheet.setTotalExtHrs(totalExtHrs);
			saarthiTimeSheet.setTotalFood(totalFood);
			saarthiTimeSheet.setGrandTotal(grandTotal);
			saarthiTimeSheet.setReportingManager(reportingManager);
			
			saarthiTimeSheetPersistence.update(saarthiTimeSheet);
			}
			else {
				 _log.info("No entry found , creating a new entry. >>>");
			long timeSheetId = counterLocalService.increment(SaarthiTimeSheet.class.getName());
			saarthiTimeSheet = saarthiTimeSheetPersistence.create(timeSheetId);
			saarthiTimeSheet.setUserId(userId);
			saarthiTimeSheet.setTimeSheetId(timeSheetId);
			saarthiTimeSheet.setCreateDate(new Date());
			saarthiTimeSheet.setModifiedDate(new Date());
			saarthiTimeSheet.setDate(date);
			saarthiTimeSheet.setInTime(inTime);
			saarthiTimeSheet.setOutTime(outTime);
			saarthiTimeSheet.setTotalHrs(totalHrs);
			saarthiTimeSheet.setPurpose(purpose);
			saarthiTimeSheet.setHoliday(holiday);
			saarthiTimeSheet.setExtHours(extHours);
			saarthiTimeSheet.setBreakFast(breakFast);
			saarthiTimeSheet.setDinner(dinner);
			saarthiTimeSheet.setTotalHoliday(totalHoliday);
			saarthiTimeSheet.setTotalExtHrs(totalExtHrs);
			saarthiTimeSheet.setTotalFood(totalFood);
			saarthiTimeSheet.setGrandTotal(grandTotal);
			saarthiTimeSheet.setMonth(month);
			saarthiTimeSheet.setYear(year);
			saarthiTimeSheet.setReportingManager(reportingManager);
			
			saarthiTimeSheetPersistence.update(saarthiTimeSheet);
}
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}	
		return saarthiTimeSheet;
	}
	
	public List<SaarthiTimeSheet> getByUserMonYear(long userId,String month,String year) {
	    try {
	        return saarthiTimeSheetPersistence.findByUserIdMonYear(userId, month, year);
	    } catch (Exception e) {
	        _log.debug("Exception while fetching date: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	public  List<SaarthiTimeSheet> getMonAndYear(String month,String year) {
	    try {
	        return saarthiTimeSheetPersistence.findByMonAndyear(month, year);
	    } catch (Exception e) {
	        _log.error("Exception while fetching data: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	
}