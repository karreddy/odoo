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
import com.plng.common.schema.model.CafStoreInventory;
import com.plng.common.schema.model.InventoryQty;
import com.plng.common.schema.service.base.InventoryQtyLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.InventoryQty",
	service = AopService.class
)
public class InventoryQtyLocalServiceImpl
	extends InventoryQtyLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(InventoryQtyLocalServiceImpl.class);

	@Override
	public InventoryQty saveInventoryQty(long item_id, float qty) {
	    _log.info("InventoryQtyLocalServiceImpl.saveInventoryQty()...");

	    InventoryQty inventoryQty = null;
	    try {
	        InventoryQty existingInventory = inventoryQtyPersistence.fetchByItem(item_id);
	        if (existingInventory != null) {
	            qty += existingInventory.getQty();
	            existingInventory.setQty(qty);
	            existingInventory.setDate(new Date());
	            inventoryQty = inventoryQtyPersistence.update(existingInventory);
	        } else {
	            long invQtyId = counterLocalService.increment(InventoryQty.class.getName());
	            inventoryQty = inventoryQtyPersistence.create(invQtyId);
	            inventoryQty.setItem_id(item_id);
	            inventoryQty.setQty(qty);
	            inventoryQty.setDate(new Date());
	            inventoryQtyPersistence.update(inventoryQty);
	        }
	    } catch (Exception e) {
	        _log.error(e.getMessage());
	    }
	    return inventoryQty;
	}

	public InventoryQty deductInventoryQty(long item_id, float qty) {
	    _log.info("CafStoreInventoryLocalServiceImpl.deductStoreInventory()...");
	    InventoryQty inventoryQty = null;
	    try {
	    	InventoryQty existingInventory = inventoryQtyPersistence.fetchByItem(item_id);
	        if (existingInventory != null) {
	            float remainingQty = existingInventory.getQty() - qty;
	            if (remainingQty >= 0) { 
	                existingInventory.setQty(remainingQty);
	                inventoryQty = inventoryQtyPersistence.update(existingInventory);
	            } else {
	                _log.error("Insufficient quantity for item ID: " + item_id);
	            }
	        } 
	    } catch (Exception e) {
	        _log.error(e.getMessage());
	    }
	    return inventoryQty;
	}

	public InventoryQty rollBackInventoryQty(long item_id, float qty) {
	    _log.info("CafStoreInventoryLocalServiceImpl.rollBackInventoryQty()...");
	    InventoryQty inventoryQty = null;
	    try {
	    	InventoryQty existingInventory = inventoryQtyPersistence.fetchByItem(item_id);
	        if (existingInventory != null) {
	            float updatedQty = existingInventory.getQty() + qty;
	            existingInventory.setQty(updatedQty);
	            inventoryQty= inventoryQtyPersistence.update(existingInventory);
	        } 
	    } catch (Exception e) {
	        _log.error(e.getMessage());
	    }
	    return inventoryQty;
	}
}