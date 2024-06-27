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
import com.plng.common.schema.model.CanteenIssue;
import com.plng.common.schema.service.base.CanteenIssueLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CanteenIssue",
	service = AopService.class
)
public class CanteenIssueLocalServiceImpl
	extends CanteenIssueLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CanteenIssueLocalServiceImpl.class);

	@Override
	public CanteenIssue saveCanteenIssue(String coupon_id,long empId,String req_for,long item_id,float requested_quant ,float value_quant,Date created) {
		CanteenIssue canteenIssue=null;
		try {
			long canteenIssueId = counterLocalService.increment(CanteenIssue.class.getName());
			canteenIssue = canteenIssuePersistence.create(canteenIssueId);
			canteenIssue.setCoupon_id(coupon_id);
			canteenIssue.setEmpid(empId);
			canteenIssue.setReq_for(req_for);
			canteenIssue.setItem_id(item_id);
			canteenIssue.setRequested_quant(requested_quant);
			canteenIssue.setValue_quant(value_quant);
			canteenIssue.setCreated(created);
			canteenIssue.setIssued(new Date());
			
			canteenIssuePersistence.update(canteenIssue);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return canteenIssue;
	}
}