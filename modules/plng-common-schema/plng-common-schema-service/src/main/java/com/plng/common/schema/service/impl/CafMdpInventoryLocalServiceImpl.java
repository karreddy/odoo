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
import com.plng.common.schema.model.CafMdpInventory;
import com.plng.common.schema.service.base.CafMdpInventoryLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafMdpInventory",
	service = AopService.class
)
public class CafMdpInventoryLocalServiceImpl
	extends CafMdpInventoryLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CafMdpInventoryLocalServiceImpl.class);

	@Override
	public CafMdpInventory saveMdpInventory(Date date,long ifor,long item_id,float quantity,float unit_price) {
		_log.info("CafMdpInventoryLocalServiceImpl.saveVendorpurchase()...");
		
		CafMdpInventory cafMdpInventory=null;
		try {
			long mdpInventoryId = counterLocalService.increment(CafMdpInventory.class.getName());
			cafMdpInventory = cafMdpInventoryPersistence.create(mdpInventoryId);
			cafMdpInventory.setDate(date);
			cafMdpInventory.setIfor(ifor);
			cafMdpInventory.setItem_id(item_id);
			cafMdpInventory.setQuantity(quantity);
			cafMdpInventory.setUnit_price(unit_price);
			cafMdpInventoryPersistence.update(cafMdpInventory);

		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return cafMdpInventory;
	}
	
	public List<CafMdpInventory> getTodaysStock(long iFor,Date date, int start, int end) {
		List<CafMdpInventory> stockToday =null;
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

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CafMdpInventory.class, CafMdpInventoryLocalServiceImpl.class.getClassLoader());
		  if(iFor==1) {
			  dynamicQuery.add(RestrictionsFactoryUtil.between("date", startDate, endDate));
		  }else {
			  dynamicQuery.add(RestrictionsFactoryUtil.between("date", startDate, endDate));
			    dynamicQuery.add(RestrictionsFactoryUtil.eq("ifor",iFor )); 
		  }
		    stockToday = cafStoreInventoryPersistence.findWithDynamicQuery(dynamicQuery, start, end);
		    
		} catch (Exception e) {
			_log.debug("Exception while fetching the getCafeInventoriesByDate data ::::::::: "+e.getMessage(), e);
		}
	    return stockToday;
	}
}