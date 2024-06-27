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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.CafeInvConsumption;
import com.plng.common.schema.service.base.CafeInvConsumptionLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeInvConsumption",
	service = AopService.class
)
public class CafeInvConsumptionLocalServiceImpl
	extends CafeInvConsumptionLocalServiceBaseImpl {
	
private static final Log _log = LogFactoryUtil.getLog(CafeInvConsumptionLocalServiceImpl.class);
	
	public CafeInvConsumption addInventoryConsumption(Date date,long itemId,long invDisplay,float quantity,String reason) {
		CafeInvConsumption cafeInvConsumption=null;
		try {
			long consumId = counterLocalService.increment(CafeInvConsumption.class.getName());
			 
			  cafeInvConsumption = cafeInvConsumptionPersistence.create(consumId);
			cafeInvConsumption.setConsumId(consumId);
			cafeInvConsumption.setCreateDate(new Date());
			cafeInvConsumption.setModifiedDate(new Date());
			cafeInvConsumption.setDate(date);
			cafeInvConsumption.setItem(itemId);
			cafeInvConsumption.setInvDisplay(invDisplay);
			cafeInvConsumption.setQuantity(quantity);
			cafeInvConsumption.setReason(reason);

			cafeInvConsumptionPersistence.update(cafeInvConsumption);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return cafeInvConsumption;
	}

	@Override
	public List<CafeInvConsumption> getInventoryConsumtionDetails(int start, int end) {
		try {
			return super.getCafeInvConsumptions(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		} catch (Exception e) {
			 _log.debug("Error while fetching CafeInventory by item name: " + e.getMessage());
		        return Collections.emptyList();
		}
	}
}