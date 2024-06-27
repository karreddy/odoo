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
import com.plng.common.schema.model.CafeInventory;
import com.plng.common.schema.model.CafeSystemEntry;
import com.plng.common.schema.service.base.CafeInventoryLocalServiceBaseImpl;

import java.util.Calendar;
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
	property = "model.class.name=com.plng.common.schema.model.CafeInventory",
	service = AopService.class
)
public class CafeInventoryLocalServiceImpl
	extends CafeInventoryLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CafeInventoryLocalServiceImpl.class);

	@Override
	public CafeInventory addInventoryDetails(Date date,String inventory,String item,long quantity,long currentStockquantity1,float currentStockquantity2,float itemUnitPrice,Boolean OneItemB) {
		_log.info("CafeInventoryLocalServiceImpl.addInventoryDetails()...");
		
		List<CafeInventory> cafeInventoryList =null;
		 CafeInventory cafeInventory = null;
		try {
				cafeInventoryList = getCafeInventoryByItem(item,inventory,date);

			    if (!cafeInventoryList.isEmpty()) {
			    	 _log.info("Entry found for today, updating >>>");

			        cafeInventory = cafeInventoryList.get(0);
			        cafeInventory.setDate(date);
			        cafeInventory.setInventory(inventory);
			        cafeInventory.setQuantity(cafeInventory.getQuantity() + quantity);
			        cafeInventory.setCurrentStockquantity1(cafeInventory.getCurrentStockquantity1() + quantity);
			        cafeInventory.setCurrentStockquantity2(currentStockquantity2);
			        cafeInventory.setItemUnitPrice(itemUnitPrice);
			        cafeInventory.setOneItemB(OneItemB);

			        cafeInventoryPersistence.update(cafeInventory);
			    } else {
			    	 _log.info("No entry found for today, creating a new entry. >>>");

			        long inventoryId = counterLocalService.increment(CafeInventory.class.getName());
			        cafeInventory = cafeInventoryPersistence.create(inventoryId);
			        cafeInventory.setDate(date);
			        cafeInventory.setInventory(inventory);
			        cafeInventory.setItem(item);
			        cafeInventory.setQuantity(quantity);
			        cafeInventory.setCurrentStockquantity1(currentStockquantity1 + quantity);
			        cafeInventory.setCurrentStockquantity2(currentStockquantity2);
			        cafeInventory.setItemUnitPrice(itemUnitPrice);
			        cafeInventory.setOneItemB(OneItemB);

			        cafeInventoryPersistence.update(cafeInventory); 
			    }
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		    return cafeInventory;
	}
	
	public List<CafeInventory> getCafeInventoryByItem(String item, String inventory,Date date) {
	    try {
	        return cafeInventoryPersistence.findByItem(item, inventory, date);
	    } catch (Exception e) {
	        _log.debug("Error while fetching CafeInventory by item name: " + item, e);
	        return Collections.emptyList();
	    }
	}  
	
	public JSONObject getCafeInventoriesByDate(Date date, int start, int end,boolean isClubbed) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		try {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CafeInventory.class, CafeInventoryLocalServiceImpl.class.getClassLoader());
		    dynamicQuery.add(PropertyFactoryUtil.forName("date").eq(date));
		    
		    List<CafeInventory> cafeteriaInventory = cafeInventoryPersistence.findWithDynamicQuery(dynamicQuery, start, end);
		    jsonObject.put("Cafeteria", cafeteriaInventory.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Cafeteria")).sorted(Comparator.comparing(CafeInventory::getInventoryId).reversed()).collect(Collectors.toList()));
		//    jsonObject.put("MDorPantry", cafeteriaInventory.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Pantry") || itr.getInventory().trim().equalsIgnoreCase("MD Office")).sorted(Comparator.comparing(CafeInventory::getInventoryId).reversed()).collect(Collectors.toList()));
		    if(isClubbed) {
	        	jsonObject.put("Pantry", cafeteriaInventory.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Pantry")).sorted(Comparator.comparing(CafeInventory::getInventoryId).reversed()).collect(Collectors.toList()));
	        	jsonObject.put("MD", cafeteriaInventory.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("MD Office")).sorted(Comparator.comparing(CafeInventory::getInventoryId).reversed()).collect(Collectors.toList()));
	        }else {
	        	jsonObject.put("MDorPantry", cafeteriaInventory.stream().filter(itr -> itr.getInventory().trim().equalsIgnoreCase("Pantry") || itr.getInventory().trim().equalsIgnoreCase("MD Office")).sorted(Comparator.comparing(CafeInventory::getInventoryId).reversed()).collect(Collectors.toList()));
	        }
		} catch (Exception e) {
			_log.debug("Exception while fetching the getCafeInventoriesByDate data ::::::::: "+e.getMessage(), e);
		}
	    
	    return jsonObject;
	}
	
	@Override
	public List<CafeInventory> getCafeInventoryDetails(int start, int end) {
		try {
			return super.getCafeInventories(start, end);
		} catch (Exception e) {
			 _log.debug("Error while fetching data: " + e.getMessage());
		        return Collections.emptyList();
		}
	}
	
	
	public void updateCoupon(String item,Date date,long quantity)  {
		CafeInventory cafeInventoryByItem = null;
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 calendar.set(Calendar.HOUR_OF_DAY, 0);
		 calendar.set(Calendar.MINUTE, 0);
		 calendar.set(Calendar.SECOND, 0);
		 calendar.set(Calendar.MILLISECOND, 0);
		 Date startOfDay = calendar.getTime();
		try {
			cafeInventoryByItem = cafeInventoryPersistence.findByItemAndDate(item, startOfDay);
	        if (cafeInventoryByItem != null) {
	        	long curQuantity = cafeInventoryByItem.getQuantity();
	        	cafeInventoryByItem.setQuantity(curQuantity+quantity);
	        	cafeInventoryPersistence.update(cafeInventoryByItem);
	        } else {
	            _log.error(" Data not found >>>");
	        }
	    } catch (Exception e) {
	        _log.debug("Exception fetching data >>>" + e.getMessage());
	    }
	}
}