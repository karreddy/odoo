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
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.CafStoreInventory;
import com.plng.common.schema.service.base.CafStoreInventoryLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafStoreInventory",
	service = AopService.class
)
public class CafStoreInventoryLocalServiceImpl
	extends CafStoreInventoryLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CafStoreInventoryLocalServiceImpl.class);

	@Override
	public CafStoreInventory saveStoreInventory (long item_id,float qty) {
		_log.info("CafStoreInventoryLocalServiceImpl.saveVendorpurchase()...");
		CafStoreInventory cafStoreInventory = null;
	    try {
	        CafStoreInventory existingInventory = cafStoreInventoryPersistence.fetchByItem(item_id);
	        if (existingInventory != null) {
	            existingInventory.setQty(existingInventory.getQty()+qty);
	            existingInventory.setDate(new Date());
	            cafStoreInventory = cafStoreInventoryPersistence.update(existingInventory);
	        } else {
	            long storeId = counterLocalService.increment(CafStoreInventory.class.getName());
	            cafStoreInventory = cafStoreInventoryPersistence.create(storeId);
	            cafStoreInventory.setDate(new Date());
	            cafStoreInventory.setItem_id(item_id);
	            cafStoreInventory.setQty(qty);
	            cafStoreInventoryPersistence.update(cafStoreInventory);
	        }
	    } catch (Exception e) {
	        _log.error(e.getMessage());
	    }
	    return cafStoreInventory;
	}	
	
	@Override
	public CafStoreInventory deductStoreInventory(long item_id, float qty) {
	    _log.info("CafStoreInventoryLocalServiceImpl.deductStoreInventory()...");
	    CafStoreInventory cafStoreInventory = null;
	    try {
	        CafStoreInventory existingInventory = cafStoreInventoryPersistence.fetchByItem(item_id);
	        if (existingInventory != null) {
	            float remainingQty = existingInventory.getQty() - qty;
	            if (remainingQty >= 0) { 
	                existingInventory.setQty(remainingQty);
	                cafStoreInventory = cafStoreInventoryPersistence.update(existingInventory);
	            } else {
	                _log.error("Insufficient quantity for item ID: " + item_id);
	            }
	        } 
	    } catch (Exception e) {
	        _log.error(e.getMessage());
	    }
	    return cafStoreInventory;
	}
	
}