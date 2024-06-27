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
import com.plng.common.schema.model.CompetitiveExam;
import com.plng.common.schema.service.base.CompetitiveExamLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CompetitiveExam",
	service = AopService.class
)
public class CompetitiveExamLocalServiceImpl
	extends CompetitiveExamLocalServiceBaseImpl {
	public CompetitiveExam saveCompetitiveExam(long employeeId,String empName,String designation,String department,String location,String session,long userId,long groupId,long companyId) {
		CompetitiveExam exam = competitiveExamPersistence.create(counterLocalService.increment(CompetitiveExam.class.getName()));
		exam.setEmployeeId(employeeId);
		exam.setEmployeeName(empName);
		exam.setDesignation(designation);
		exam.setDepartment(department);
		exam.setLocation(location);
		exam.setAcadamicSession(session);
		exam.setUserId(userId);
		exam.setGroupId(groupId);
		exam.setCompanyId(companyId);
		exam.setCreateBy(userId);
		exam.setModifiedBy(userId);
		exam.setModifiedDate(new Date());
		exam.setCreateDate(new Date());
		return competitiveExamPersistence.update(exam);
	}
	
	public List<CompetitiveExam> getExamByUserId(long userId){
		return competitiveExamPersistence.findByUserId(userId);
	}
}