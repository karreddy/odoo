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
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.CafeSystemEntry;
import com.plng.common.schema.service.base.CafeSystemEntryLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeSystemEntry",
	service = AopService.class
)
public class CafeSystemEntryLocalServiceImpl
	extends CafeSystemEntryLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CafeSystemEntryLocalServiceImpl.class);

	public CafeSystemEntry saveLiveInventoryDetails(Date date,String inventory,String item,
			long quantity,long currentStockquantity1,float currentStockquantity2,float itemUnitPrice,Boolean OneItemB) {
		_log.info("CafeSystemEntryLocalServiceImpl.saveLiveInventoryDetails()...");
		
		CafeSystemEntry cafeSystemEntry = null;
		try {
			cafeSystemEntry = null;
			long liveId = counterLocalService.increment(CafeSystemEntry.class.getName());
			cafeSystemEntry = cafeSystemEntryPersistence.create(liveId);
			
			cafeSystemEntry.setCreateDate(new Date());
			cafeSystemEntry.setModifiedDate(new Date());
			cafeSystemEntry.setDate(date);
			cafeSystemEntry.setInventory(inventory);
			cafeSystemEntry.setItem(item);
			cafeSystemEntry.setQuantity(quantity);
			cafeSystemEntry.setCurrentStockquantity1(currentStockquantity1);
			cafeSystemEntry.setCurrentStockquantity2(currentStockquantity2);
			cafeSystemEntry.setItemUnitPrice(itemUnitPrice);
			cafeSystemEntry.setOneItemB(OneItemB);
			
			cafeSystemEntryPersistence.update(cafeSystemEntry);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		 
		return cafeSystemEntry;

	}
	
	public List<CafeSystemEntry> getCafeLiveInventoryByDate(Date date) {
	    try {
	        return cafeSystemEntryPersistence.findByDate(date);
	    } catch (Exception e) {
	        _log.debug("Error while fetching data : " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	public JSONObject getCafeInventoriesByDate(Date date, int start, int end,boolean isClubbed) {
	    JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
	    List<CafeSystemEntry> cafeSystemEntries = null;
	    try {
	        DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CafeSystemEntry.class, getClass().getClassLoader());
	        dynamicQuery.add(PropertyFactoryUtil.forName("date").eq(date));
	        
	        cafeSystemEntries=cafeSystemEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	        jsonObject.put("Cafeteria", cafeSystemEntries.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Cafeteria")).sorted(Comparator.comparing(CafeSystemEntry::getModifiedDate).reversed()).collect(Collectors.toList()));
	        if(isClubbed) {
	        	jsonObject.put("Pantry", cafeSystemEntries.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Pantry")).sorted(Comparator.comparing(CafeSystemEntry::getModifiedDate).reversed()).collect(Collectors.toList()));
	        	jsonObject.put("MD", cafeSystemEntries.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("MD Office")).sorted(Comparator.comparing(CafeSystemEntry::getModifiedDate).reversed()).collect(Collectors.toList()));
	        }else {
	        	jsonObject.put("MDorPantry", cafeSystemEntries.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Pantry") || itr.getInventory().trim().equalsIgnoreCase("MD Office")).sorted(Comparator.comparing(CafeSystemEntry::getModifiedDate).reversed()).collect(Collectors.toList()));
	        }
        
	    } catch (Exception e) {
	        _log.debug("Exception while fetching the getCafeInventoriesByDate data ::::::::: " + e.getMessage());
	    }

	    return jsonObject;
	}

}