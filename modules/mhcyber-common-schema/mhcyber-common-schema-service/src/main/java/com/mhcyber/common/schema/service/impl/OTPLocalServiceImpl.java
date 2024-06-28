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

package com.mhcyber.common.schema.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.Validator;
import com.mhcyber.common.schema.model.OTP;
import com.mhcyber.common.schema.service.base.OTPLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;


/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.mhcyber.common.schema.model.OTP",
	service = AopService.class
)
public class OTPLocalServiceImpl extends OTPLocalServiceBaseImpl {
	
	public OTP saveOTPdetails(long userId, String mobileNumber, String otp, String otpType, long companyId, long groupId, Date createDate) {
		OTP otpExist = null;
		
		otpExist = getOTPByMobileNumber(mobileNumber, otpType);
		
		if(Validator.isNull(otpExist)) {
			otpExist = otpPersistence.create(counterLocalService.increment(OTP.class.getName()));
		}
		otpExist.setUserId(userId);
		otpExist.setMobile(mobileNumber);
		otpExist.setOtpValue(otp);
		otpExist.setOtpType(otpType);
		otpExist.setCompanyId(companyId);
		otpExist.setGroupId(groupId);
		otpExist.setCreateDate(new Date());
		return otpPersistence.update(otpExist);		
	}
	
	public List<OTP> getOTPByUserId(long userId) {
		return otpPersistence.findByOtpByUserId(userId);
	}
	
	public OTP getOTPByMobileNumber(String mobileNumber, String otpType) {
		return otpPersistence.fetchByOtpByMobileNumber(mobileNumber, otpType);		
	}
	
	public List<OTP> getByMobileNumber(String mobileNumber, Date createDate) {
		return otpPersistence.findBybyMobileNo(mobileNumber, createDate);		
	}
	
	public Integer countByMobileNo(String mobileNumber, Date createDate) {
		return otpPersistence.countBybyMobileNo(mobileNumber, createDate);		
	}
	
}