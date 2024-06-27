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
import com.plng.common.schema.model.Form12bb;
import com.plng.common.schema.service.base.Form12bbLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;


/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Form12bb",
	service = AopService.class
)
public class Form12bbLocalServiceImpl extends Form12bbLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(Form12bbLocalServiceImpl.class);
	
	
	public List<Form12bb> getDetailsByUserId(long userId) {
		List<Form12bb> fetchBygetUserId = form12bbPersistence.findBygetuserId(userId);
		return fetchBygetUserId;
		
	}
	public Form12bb getByUserId(long userId) {
		Form12bb findBygetUserId = null;
		try {
			findBygetUserId = form12bbPersistence.findByUserId(userId);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return findBygetUserId;
		
	}
}