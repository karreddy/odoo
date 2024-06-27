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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.plng.common.schema.model.TSReportMan;
import com.plng.common.schema.model.TimeSheet;
import com.plng.common.schema.service.TimeSheetLocalServiceUtil;
import com.plng.common.schema.service.base.TimeSheetLocalServiceBaseImpl;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.TimeSheet",
	service = AopService.class
)
public class TimeSheetLocalServiceImpl extends TimeSheetLocalServiceBaseImpl {
	
	 private static final Log _log = LogFactoryUtil.getLog(TimeSheetLocalServiceImpl.class);
	
	 @Override
	 public TimeSheet saveTimeSheet(long empId, Date date, String in_t, String out_t, String purpose, 
			 float claim_amt, float ext_hrs, boolean bf, boolean dinner, long status) {
	     TimeSheet timeSheet = null;
	     _log.info("TimeSheetLocalServiceImpl >>>>>>>>");
	     try {
	         TimeSheet existingTimeSheet = timeSheetPersistence.fetchByEmpId(empId, date);
	         _log.info("existingTimeSheet >>>>>>>>"+existingTimeSheet);
	         if (existingTimeSheet != null) {
	        	 _log.info("if  >>>>>>>>"+existingTimeSheet);
	             existingTimeSheet.setIn_t(in_t);
	             existingTimeSheet.setOut_t(out_t);
	             existingTimeSheet.setPurpose(purpose);
	             existingTimeSheet.setClaim_amt(claim_amt);
	             existingTimeSheet.setExt_hrs(ext_hrs);
	             existingTimeSheet.setBf(bf);
	             existingTimeSheet.setDinner(dinner);
	             existingTimeSheet.setStatus(status);
	             timeSheetPersistence.update(existingTimeSheet);
	             timeSheet = existingTimeSheet;
	         } else {
	        	 _log.info("else >>>>>>>>"+existingTimeSheet);
	             long tsId = counterLocalService.increment(TimeSheet.class.getName());
	             timeSheet = timeSheetPersistence.create(tsId);
	             timeSheet.setEmpid(empId);
	             timeSheet.setDate(date);
	             timeSheet.setIn_t(in_t);
	             timeSheet.setOut_t(out_t);
	             timeSheet.setPurpose(purpose);
	             timeSheet.setClaim_amt(claim_amt);
	             timeSheet.setExt_hrs(ext_hrs);
	             timeSheet.setBf(bf);
	             timeSheet.setDinner(dinner);
	             timeSheet.setStatus(status);
	             timeSheetPersistence.update(timeSheet);
	         }
	     }catch (Exception e) {
	         _log.error("Error saving timesheet: " + e.getMessage());
	     }
	     return timeSheet;
	 }
	 
		 
	 public  TimeSheet getTsByEmpAndDate(long empid,Date date) {
		    try {
		        return timeSheetPersistence.findByEmpId(empid, date);
		    } catch (Exception e) {
		        _log.error("Exception while fetching data: " + e.getMessage());
		        return null;
		    }
		}

	 public List<TimeSheet> getmonthlyDetails(long empId,int mon, int year) {
			List<TimeSheet> monthlylist =null;
		    try {
		        Calendar startDate = Calendar.getInstance();
		        startDate.set(year, mon - 1, 1, 0, 0, 0); 
		        Calendar endDate = Calendar.getInstance();
		        endDate.set(year, mon - 1, startDate.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59); 

		        DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TimeSheet.class, TimeSheetLocalServiceImpl.class.getClassLoader());
		        
		        dynamicQuery.add(RestrictionsFactoryUtil.between("date", startDate.getTime(), endDate.getTime()));
		       dynamicQuery.add(RestrictionsFactoryUtil.eq("empid", empId));
		        monthlylist = TimeSheetLocalServiceUtil.dynamicQuery(dynamicQuery);
		        
		    } catch (Exception e) {
		        _log.error("Error fetching monthly details: " + e.getMessage());
		    }
		    return monthlylist;
	 }
	 
}