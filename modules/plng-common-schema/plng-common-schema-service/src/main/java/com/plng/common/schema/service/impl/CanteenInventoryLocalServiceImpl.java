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
import com.plng.common.schema.model.CafeteriaCoupons;
import com.plng.common.schema.model.CanteenInventory;
import com.plng.common.schema.service.base.CanteenInventoryLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CanteenInventory",
	service = AopService.class
)
public class CanteenInventoryLocalServiceImpl
	extends CanteenInventoryLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CanteenInventoryLocalServiceImpl.class);
	@Override
	public CanteenInventory saveMdpInventory(Date date,long item_id,float quantity,float unit_price,Date time,int status,boolean res_guest,boolean hamper) {
		_log.info("CanteenInventoryLocalServiceImpl.saveVendorpurchase()...");
		
		CanteenInventory canteenInventory=null;
		try {
			long inventoryId = counterLocalService.increment(CanteenInventory.class.getName());
			canteenInventory = canteenInventoryPersistence.create(inventoryId);
			canteenInventory.setDate(date);
			canteenInventory.setItem_id(item_id);
			canteenInventory.setQuantity(quantity);
			canteenInventory.setUnit_price(unit_price);
			canteenInventory.setTime(time);
			canteenInventory.setStatus(status);
			canteenInventory.setRes_guest(res_guest);
			canteenInventory.setHamper(hamper);
			canteenInventoryPersistence.update(canteenInventory);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return canteenInventory;
	}
	
	public List<CanteenInventory> getByItemId(long item_id) {
		List<CanteenInventory> canteenInventory =null;
			try {
			    canteenInventory = canteenInventoryPersistence.findByItem(item_id);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return canteenInventory;
		}
	
	public List<CanteenInventory> getTodaysStock(Date date, int start, int end) {
		List<CanteenInventory> stockToday =null;
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0); 
			cal.set(Calendar.MINUTE, 0); 
			cal.set(Calendar.SECOND, 0);
			// this is the start time.
			Date startDate = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY,23); 
			cal.set(Calendar.MINUTE, 59); 
			cal.set(Calendar.SECOND, 59);
			Date endDate=cal.getTime();
			_log.info("start date >>>"+startDate + " endDate >>>>"+endDate);

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CanteenInventory.class, CanteenInventoryLocalServiceImpl.class.getClassLoader());
		    dynamicQuery.add(RestrictionsFactoryUtil.between("date", startDate, endDate));

		    stockToday = canteenInventoryPersistence.findWithDynamicQuery(dynamicQuery, start, end);
		    
		} catch (Exception e) {
			_log.debug("Exception while fetching the getCafeInventoriesByDate data ::::::::: "+e.getMessage(), e);
		}
	    return stockToday;
	}
}