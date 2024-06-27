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
import com.plng.common.schema.exception.NoSuchForm16MailException;
import com.plng.common.schema.model.Form16Mail;
import com.plng.common.schema.service.base.Form16MailLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Form16Mail",
	service = AopService.class
)
public class Form16MailLocalServiceImpl extends Form16MailLocalServiceBaseImpl {
	
	public Form16Mail getForm16ByEmpId(long employeeId) {

		Form16Mail form16Mail = null;
		try {
			form16Mail = form16MailPersistence.findByemployeeId(employeeId);
		} catch (NoSuchForm16MailException e) {
			return null;
		}
		return form16Mail;
	}
	
	public Form16Mail getForm16ByEmployeePan(String pan) {

		Form16Mail form16Mail = null;
		try {
			form16Mail = form16MailPersistence.findBypanNumber(pan);
		} catch (NoSuchForm16MailException e) {
			return null;
		}
		return form16Mail;
	}
}