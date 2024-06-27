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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.HolidayCalendar;
import com.plng.common.schema.service.HolidayCalendarLocalServiceUtil;
import com.plng.common.schema.service.base.HolidayCalendarLocalServiceBaseImpl;
import com.plng.common.schema.service.persistence.HolidayCalendarPersistence;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.HolidayCalendar",
	service = AopService.class
)
public class HolidayCalendarLocalServiceImpl
	extends HolidayCalendarLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(HolidayCalendarLocalServiceImpl.class);

	@Override
	public List<HolidayCalendar> getHolidayDetails(int start, int end) {
		return super.getHolidayCalendars(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	public HolidayCalendar addHolidayDetails(long holidayId,String description,String date_,String day,String location){
		 HolidayCalendar createHolidayCalendar = HolidayCalendarLocalServiceUtil.createHolidayCalendar((long)holidayId);
        createHolidayCalendar.setDescription(description);
        createHolidayCalendar.setDate(date_);
        createHolidayCalendar.setDay(day);
        createHolidayCalendar.setLocation(location);
        HolidayCalendarLocalServiceUtil.addHolidayCalendar(createHolidayCalendar);
		return createHolidayCalendar;
		
	}
	
	public List<HolidayCalendar> getHolidaysByLocation(String location){
		List<HolidayCalendar> holidayBylocation = null;
		try {
			holidayBylocation = holidayCalendarPersistence.findByHolidayBylocation(location);
		} catch (Exception e) {
		_log.error(e.getMessage());	
		}
		
		return holidayBylocation;
		
	}
}