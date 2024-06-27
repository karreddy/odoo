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
import com.plng.common.schema.exception.NoSuchVacancyDetailsException;
import com.plng.common.schema.model.VacancyDetails;
import com.plng.common.schema.service.base.VacancyDetailsLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.plng.common.schema.model.VacancyDetails", service = AopService.class)
public class VacancyDetailsLocalServiceImpl extends VacancyDetailsLocalServiceBaseImpl {
	public VacancyDetails addBill(VacancyDetails vacancyDetails) {

		vacancyDetails = vacancyDetailsLocalService.addVacancyDetails(vacancyDetails);

		return vacancyDetails;

	}
	
	public VacancyDetails getVacancyDetailsByJobCode(String jobCode) {
		try {
			return vacancyDetailsPersistence.findByjobCode(jobCode);
		} catch (NoSuchVacancyDetailsException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<VacancyDetails> getVacancyDetailsByStatus(String status) {
		try {
			return vacancyDetailsPersistence.findBystatus(status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}