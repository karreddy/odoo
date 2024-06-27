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
import com.plng.common.schema.exception.NoSuchContractLoginException;
import com.plng.common.schema.model.ContractLogin;
import com.plng.common.schema.service.base.ContractLoginLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.ContractLogin",
	service = AopService.class
)
public class ContractLoginLocalServiceImpl
	extends ContractLoginLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(ContractLoginLocalServiceImpl.class);

	public ContractLogin saveContractEmployee(String fName,String lName,long empId,Date dob,String empRole,String gender,
			String eMail,String pwd,Date endDate,long loc) {
		_log.info("CafeVendorPurchaseLocalServiceImpl.saveVendorpurchase()...");
		
		ContractLogin contractLogin=null;
		try {
			 long id = counterLocalService.increment(ContractLogin.class.getName());
			 contractLogin  = contractLoginPersistence.create(id);
			 contractLogin.setName(fName+" "+lName);
			 contractLogin.setUserId(empId);
			 contractLogin.setEmployeer(empRole);
			 contractLogin.setGender(gender);
			 contractLogin.setPassword(pwd);
			 contractLogin.setEndDate(endDate);
			 contractLogin.setLoc(loc);

			 contractLoginPersistence.update(contractLogin);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return contractLogin;
	}
	
	public ContractLogin getContEmpByUserId(long userId){
		ContractLogin contEmpByUserId =null;
		try {
			contEmpByUserId = contractLoginPersistence.findByUserId(userId);
		} catch (NoSuchContractLoginException e) {
			_log.error(e.getMessage());
		}
		return contEmpByUserId;
	}
	
	public ContractLogin getContEmpByLrId(long userId){
		ContractLogin contEmpByLrId =null;
		try {
			contEmpByLrId = contractLoginPersistence.findByLrUserId(userId);
		} catch (NoSuchContractLoginException e) {
			_log.error(e.getMessage());
		}
		return contEmpByLrId;
	}
}