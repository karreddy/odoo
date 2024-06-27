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
import com.plng.common.schema.model.Tax_Choice;
import com.plng.common.schema.service.base.Tax_ChoiceLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Tax_Choice",
	service = AopService.class
)
public class Tax_ChoiceLocalServiceImpl extends Tax_ChoiceLocalServiceBaseImpl {
	
	public Tax_Choice getTaxChoiceByEmpId(int employeeId) {

		Tax_Choice tax_Choice = null;
		try {
			tax_Choice = tax_ChoicePersistence.findByemployeeId(employeeId);
		} catch ( Exception e) {
			return null;
		}
		return tax_Choice;
	}

	public List<Tax_Choice> getTaxChoiceByforwardYear(String fy) {

		List<Tax_Choice> txlst = null;
		try {
			txlst = tax_ChoicePersistence.findByforwardYear(fy);
		} catch (Exception e) {
			return null;
		}
		return txlst;
	}
	
	public Tax_Choice getTaxChoiceByEmpIdAndFinancialyear(int employeeId,String fy) {

		Tax_Choice tax_Choice = null;
		try {
			tax_Choice = tax_ChoicePersistence.findByemployeeIdAndfinancialyear(employeeId, fy);
		} catch ( Exception e) {
			return null;
		}
		return tax_Choice;
	}
}