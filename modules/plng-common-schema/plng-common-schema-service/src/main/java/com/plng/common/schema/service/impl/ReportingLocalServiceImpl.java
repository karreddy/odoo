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
import com.plng.common.schema.model.Reporting;
import com.plng.common.schema.service.base.ReportingLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Reporting",
	service = AopService.class
)
public class ReportingLocalServiceImpl extends ReportingLocalServiceBaseImpl {
	
	private  Log _log = LogFactoryUtil.getLog(getClass());
	
	public Reporting getSupId(long empId) {
		Reporting reporting = null;
		try {
			  reporting = reportingPersistence.fetchByEmpId(empId);
		} catch (Exception e) {
			 _log.debug("Exe while fetching data: " + e.getMessage());
		}
		return reporting;
	}
	
	public Reporting getEmpId(long supid) {
		Reporting reporting = null;
		try {
			  reporting = reportingPersistence.fetchBySupId(supid);
		} catch (Exception e) {
			 _log.debug("Exe while fetching data: " + e.getMessage());
		}
		return reporting;
	}
	
}