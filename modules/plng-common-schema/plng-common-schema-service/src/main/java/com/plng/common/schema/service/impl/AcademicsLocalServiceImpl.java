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
import com.plng.common.schema.model.Academics;
import com.plng.common.schema.service.base.AcademicsLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Academics",
	service = AopService.class
)
public class AcademicsLocalServiceImpl extends AcademicsLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(AcademicsLocalServiceImpl.class);

	public Academics saveAcademicsDetails(long employeeId,String empName,String designation,String department,String location,String session,long userId,long groupId,long companyId) {
		Academics academics = academicsPersistence.create(counterLocalService.increment(Academics.class.getName()));
		academics.setEmployeeId(employeeId);
		academics.setEmployeeName(empName);
		academics.setDesignation(designation);
		academics.setDepartment(department);
		academics.setLocation(location);
		academics.setAcadamicSession(session);
		academics.setUserId(userId);
		academics.setGroupId(groupId);
		academics.setCompanyId(companyId);
		academics.setCreateBy(userId);
		academics.setModifiedBy(userId);
		academics.setModifiedDate(new Date());
		academics.setCreateDate(new Date());
		return academicsPersistence.update(academics);
	}
	
	public List<Academics> findAcademicsByUserId(long userId) {
		return academicsPersistence.findByUserId(userId);
	}
	
	public  List<Academics> findAcademicsByLocation(String  location) {
	    try {
	         return academicsPersistence.findByLocation(location);
	    } catch (Exception e) {
	        _log.debug("Error while fetching data: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
}