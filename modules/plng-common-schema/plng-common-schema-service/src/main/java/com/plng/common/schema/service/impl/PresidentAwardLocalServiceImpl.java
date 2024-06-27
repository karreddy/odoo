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
import com.plng.common.schema.model.PresidentAward;
import com.plng.common.schema.service.base.PresidentAwardLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.PresidentAward",
	service = AopService.class
)
public class PresidentAwardLocalServiceImpl
	extends PresidentAwardLocalServiceBaseImpl {
	
	public PresidentAward savePresidentAward(long employeeId,String empName,String designation,String department,String location,String session,long userId,long groupId,long companyId) {
		PresidentAward award = presidentAwardPersistence.create(counterLocalService.increment(PresidentAward.class.getName()));
		award.setEmployeeId(employeeId);
		award.setEmployeeName(empName);
		award.setDesignation(designation);
		award.setDepartment(department);
		award.setLocation(location);
		award.setAcadamicSession(session);
		award.setUserId(userId);
		award.setGroupId(groupId);
		award.setCompanyId(companyId);
		award.setCreateBy(userId);
		award.setModifiedBy(userId);
		award.setModifiedDate(new Date());
		award.setCreateDate(new Date());
		return presidentAwardPersistence.update(award);
	}
	
	public List<PresidentAward> getPresidentByUserId(long userId){
		return presidentAwardPersistence.findByUserId(userId);
	}
}