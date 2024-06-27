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
import com.plng.common.schema.model.ApplicantWorkDetails;
import com.plng.common.schema.service.base.ApplicantWorkDetailsLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Himanshu
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.ApplicantWorkDetails",
	service = AopService.class
)
public class ApplicantWorkDetailsLocalServiceImpl
	extends ApplicantWorkDetailsLocalServiceBaseImpl {
	
	public List<ApplicantWorkDetails> getApplicantWorkDetailsByApplicationNo(long applicationNumber) {
		try {
			return applicantWorkDetailsPersistence.findByapplicationNo(applicationNumber);
		} catch (Exception e) {
			return null;
		}

	}

	public List<ApplicantWorkDetails> getApplicantWorkDetailsByJobCode(String jobCode) {
		try {
			return applicantWorkDetailsPersistence.findByjobCode(jobCode);
		} catch (Exception e) {
			return null;
		}
	}
	public ApplicantWorkDetails getworkByAppNoAndUserIdAndJobCode(String jobCode, long applicationNo, long userId) {
		try {
			return applicantWorkDetailsPersistence.findByjobCodeAndUserIdAndAppNo(jobCode, userId, applicationNo);
		} catch (Exception e) {
			return null;
		}
	}
	public List<ApplicantWorkDetails> getworkByAppNoAndJobCode(String jobCode, long applicationNo) {
		try {
			return applicantWorkDetailsPersistence.findByjobCodeAndAppNo(jobCode, applicationNo);
		} catch (Exception e) {
			return null;
		}
	}
}