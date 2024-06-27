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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.plng.common.schema.exception.NoSuchEmployeeDirectoryException;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.service.EmployeeDirectoryLocalServiceUtil;
import com.plng.common.schema.service.base.EmployeeDirectoryLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.EmployeeDirectory",
	service = AopService.class
)
public class EmployeeDirectoryLocalServiceImpl
	extends EmployeeDirectoryLocalServiceBaseImpl {
	public List<EmployeeDirectory> findEmployeeByDepartmentName(String department) {
		return employeeDirectoryPersistence.findByDepartment(department);
	}

	@Override
	public List<EmployeeDirectory> getusersByLocation(String location) {

		return employeeDirectoryPersistence.findByLocation(location);
	}

	@Override
	public List<EmployeeDirectory> getEmployeeByDepartments(String department) {

		return employeeDirectoryPersistence.findByDepartment(department);
	}
	public List<EmployeeDirectory> getEmployeeByDepartmentAndLocation(String department, String location) {
		
		return employeeDirectoryPersistence.findByDepartmentAndLocation(department, location);
	}

	public EmployeeDirectory getEmployeeDetailsByUserId(long userId) {

		EmployeeDirectory employeeDirectory = null;
		try {
			employeeDirectory = employeeDirectoryPersistence.findByUserId(userId);
		} catch (NoSuchEmployeeDirectoryException e) {
		}
		return employeeDirectory;
	}
	public EmployeeDirectory getDepartmentByUserId(long userId,String department) {

		EmployeeDirectory employeeDirectory = null;
		try {
			employeeDirectory = employeeDirectoryPersistence.findByDepartmentByUserId(department, userId);
		} catch (NoSuchEmployeeDirectoryException e) {
		}
		return employeeDirectory;
	}
	
	@Override
	public List<EmployeeDirectory> getAllEmpList(int start, int end) {
		return super.getEmployeeDirectories(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	
	public EmployeeDirectory editEmployeeDetails(long employeeId, String employeeName, String Designation, String location, String mobileNo, String alternateNo, String residenceNo, String faxNo, String bloodGroup, String extnNo) {
		EmployeeDirectory fetchEmployeeDirectory = EmployeeDirectoryLocalServiceUtil.fetchEmployeeDirectory(employeeId);
		fetchEmployeeDirectory.setEmployeeName(employeeName);
		fetchEmployeeDirectory.setDesignation(Designation);
		fetchEmployeeDirectory.setLocation(location);
		fetchEmployeeDirectory.setMobileNumber(mobileNo);
		fetchEmployeeDirectory.setAlternateNumber(alternateNo);
		fetchEmployeeDirectory.setResidenceNumber(residenceNo);
		fetchEmployeeDirectory.setFaxNumber(faxNo);
		fetchEmployeeDirectory.setBloodGroup(bloodGroup);
		fetchEmployeeDirectory.setExtentionNumber(extnNo);
		EmployeeDirectoryLocalServiceUtil.updateEmployeeDirectory(fetchEmployeeDirectory);
		return fetchEmployeeDirectory;
	}
	
	
	public EmployeeDirectory addEmployeeDetails(User addUser,String fname,long employeeId,String directno,String department,String presentgrade,String passportno,long fileEntryID, String employeeName, String Designation, String location, String mobileNo, String alternateNo, String residenceNo, String faxNo, String bloodGroup, String extensionno) {
		EmployeeDirectory createEmployeeDirectory = EmployeeDirectoryLocalServiceUtil.createEmployeeDirectory(CounterLocalServiceUtil.increment(EmployeeDirectory.class.getName()));
        createEmployeeDirectory.setGroupId(addUser.getGroupId());
        createEmployeeDirectory.setCompanyId(addUser.getCompanyId());
        createEmployeeDirectory.setUserId(addUser.getUserId());
        createEmployeeDirectory.setEmployeeName(fname);
        createEmployeeDirectory.setDesignation(Designation);
        createEmployeeDirectory.setExtentionNumber(extensionno);
        createEmployeeDirectory.setDirectNumber(directno);
        createEmployeeDirectory.setMobileNumber(mobileNo);
        createEmployeeDirectory.setAlternateNumber(alternateNo);
        createEmployeeDirectory.setResidenceNumber(residenceNo);
        createEmployeeDirectory.setFaxNumber(faxNo);
        createEmployeeDirectory.setLocation(location);
        createEmployeeDirectory.setDepartment(department);
        createEmployeeDirectory.setBloodGroup(bloodGroup);
        createEmployeeDirectory.setPresentGrade(presentgrade);
        createEmployeeDirectory.setPassportNumber(passportno);
        createEmployeeDirectory.setFileEntryId(fileEntryID);
        EmployeeDirectoryLocalServiceUtil.addEmployeeDirectory(createEmployeeDirectory);
		
		return createEmployeeDirectory;
	}

}