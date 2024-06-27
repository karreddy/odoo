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
import com.plng.common.schema.model.CafeQuantityMaster;
import com.plng.common.schema.service.base.CafeQuantityMasterLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeQuantityMaster",
	service = AopService.class
)
public class CafeQuantityMasterLocalServiceImpl
	extends CafeQuantityMasterLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CafeQuantityMasterLocalServiceImpl.class);

	@Override
	public CafeQuantityMaster addCafeQtyMaster(String itemDesc,long itemQty) {
		_log.info("CafeQuantityMasterLocalServiceImpl.addCafeQtyMaster()  >>>>>>>>> ");
	
		CafeQuantityMaster cafeQuantityMaster=null;
		
	     List<CafeQuantityMaster> itemList = getCafeQtyBYItem(itemDesc);
	    _log.info("itemList..." + itemList);

	    if (!itemList.isEmpty()) {
	        _log.info("Entry found , updating >>>");
	        cafeQuantityMaster = itemList.get(0);
	        cafeQuantityMaster.setModifiedDate(new Date());
			cafeQuantityMaster.setItemQuantity(cafeQuantityMaster.getItemQuantity()+itemQty);

	        cafeQuantityMasterPersistence.update(cafeQuantityMaster);
	    } else {
	        _log.info("No entry found for today, creating a new entry. >>>");

	        long itemId = counterLocalService.increment(CafeQuantityMaster.class.getName());
			cafeQuantityMaster = cafeQuantityMasterPersistence.create(itemId);
			cafeQuantityMaster.setItemId(itemId);
			cafeQuantityMaster.setCreateDate(new Date());
			cafeQuantityMaster.setModifiedDate(new Date());
			cafeQuantityMaster.setItemDesc(itemDesc);
			cafeQuantityMaster.setItemQuantity(itemQty);
			cafeQuantityMasterPersistence.update(cafeQuantityMaster);
			
	    }

	    return cafeQuantityMaster;
	}
	public List<CafeQuantityMaster> getCafeQtyBYItem(String itemDesc){
		try {
		return cafeQuantityMasterPersistence.findByItem(itemDesc.trim());
	    } catch (Exception e) {
	        _log.error("Error while fetching CafeInventory by item name and date: " + itemDesc, e);
	        return Collections.emptyList();
	    }
	}
	
	@Override
	public List<CafeQuantityMaster> getCafeQtyMasterDetails(int start, int end) {
		return super.getCafeQuantityMasters(start, end);
		}
}
