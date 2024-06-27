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
import com.plng.common.schema.model.TSApproval;
import com.plng.common.schema.model.TimeSheet;
import com.plng.common.schema.service.base.TSApprovalLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.TSApproval",
	service = AopService.class
)
public class TSApprovalLocalServiceImpl extends TSApprovalLocalServiceBaseImpl {
	 private static final Log _log = LogFactoryUtil.getLog(TSApprovalLocalServiceImpl.class);
		@Override
		public TSApproval saveTSApproval(long empId, long supId, long mon, long year, Date subDate, long hr, Date enteredDate ) {
			TSApproval tsApproval=null;
			try {
				long tsId = counterLocalService.increment(TSApproval.class.getName());
				tsApproval = tsApprovalPersistence.create(tsId);
				tsApproval.setEmpid(empId);
				tsApproval.setSupId(supId);
				tsApproval.setMon(mon);
				tsApproval.setYear(year);
				tsApproval.setSubDate(subDate);
				tsApproval.setHr(hr);
				tsApproval.setEnteredDate(enteredDate);
				
				tsApprovalPersistence.update(tsApproval);
				
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
			return tsApproval;
		}
}