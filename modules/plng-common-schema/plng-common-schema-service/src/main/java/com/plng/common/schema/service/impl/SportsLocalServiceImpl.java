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
import com.plng.common.schema.model.Sports;
import com.plng.common.schema.service.base.SportsLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Sports",
	service = AopService.class
)
public class SportsLocalServiceImpl extends SportsLocalServiceBaseImpl {
	
	public Sports saveSportsDetails(long employeeId,String empName,String designation,String department,String location,String session,long userId,long groupId,long companyId) {
		Sports sports = sportsPersistence.create(counterLocalService.increment(Sports.class.getName()));
		sports.setEmployeeId(employeeId);
		sports.setEmployeeName(empName);
		sports.setDesignation(designation);
		sports.setDepartment(department);
		sports.setLocation(location);
		sports.setAcadamicSession(session);
		sports.setUserId(userId);
		sports.setGroupId(groupId);
		sports.setCompanyId(companyId);
		sports.setCreateBy(userId);
		sports.setModifiedBy(userId);
		sports.setModifiedDate(new Date());
		sports.setCreateDate(new Date());
		return sportsPersistence.update(sports);
	}
	
	public List<Sports> getSportsByUserId(long userId){
		return sportsPersistence.findByUserId(userId);
	}
}