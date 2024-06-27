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
import com.plng.common.schema.model.CarrerRegistration;
import com.plng.common.schema.service.base.CarrerRegistrationLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.plng.common.schema.model.CarrerRegistration", service = AopService.class)
public class CarrerRegistrationLocalServiceImpl extends CarrerRegistrationLocalServiceBaseImpl {
	public CarrerRegistration saveCarrerReg(CarrerRegistration carrerRegistration) {

		carrerRegistration = carrerRegistrationLocalService.addCarrerRegistration(carrerRegistration);

		return carrerRegistration;

	}

	public List<CarrerRegistration> getCareerRegistrationByJobCode(String jobCode) {
		try {
			return carrerRegistrationPersistence.findByjobCode(jobCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CarrerRegistration getCareerRegistrationByApplicationNo(long applicationNo) {
		try {
			return carrerRegistrationPersistence.findByapplicationNo(applicationNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public CarrerRegistration getRegisteredByJobCodeAndUserId(long userId, String jobCode) {
		try {
			return carrerRegistrationPersistence.findByjobCodeAndUserId(jobCode, userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public CarrerRegistration getRegisteredByAppNoJobCodeAndUserId(long userId, String jobCode, long applicationNo) {
		try {
			return carrerRegistrationPersistence.findByApplicationNoAndUserIdAndJobCode(applicationNo, jobCode, userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}