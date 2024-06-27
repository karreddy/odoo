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
import com.plng.common.schema.model.CafeVendorPurchase;
import com.plng.common.schema.service.base.CafeVendorPurchaseLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeVendorPurchase",
	service = AopService.class
)
public class CafeVendorPurchaseLocalServiceImpl
	extends CafeVendorPurchaseLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CafeVendorPurchaseLocalServiceImpl.class);

	@Override
	public CafeVendorPurchase saveVendorpurchase(Date invDate,long vendor_id,long item_id,String invoice_no,long qty,float unit_price,float amount) {
		_log.info("CafeVendorPurchaseLocalServiceImpl.saveVendorpurchase()...");
		
		CafeVendorPurchase cafeVendorPurchase=null;
		try {
			long stockEntryId = counterLocalService.increment(CafeVendorPurchase.class.getName());
			cafeVendorPurchase = cafeVendorPurchasePersistence.create(stockEntryId);
			cafeVendorPurchase.setCreateDate(new Date());
			cafeVendorPurchase.setDate(invDate);
			cafeVendorPurchase.setVendor_id(vendor_id);
			cafeVendorPurchase.setItem_id(item_id);
			cafeVendorPurchase.setInvoice_no(invoice_no);
			cafeVendorPurchase.setQty(qty);
			cafeVendorPurchase.setUnit_price(unit_price);
			cafeVendorPurchase.setAmount(amount);

			cafeVendorPurchasePersistence.update(cafeVendorPurchase);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		return cafeVendorPurchase;
	}
	
	public List<CafeVendorPurchase> getTodaysStock(Date date, int start, int end) {
		List<CafeVendorPurchase> stockToday =null;
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

			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CafeVendorPurchase.class, CafeVendorPurchaseLocalServiceImpl.class.getClassLoader());
		    dynamicQuery.add(RestrictionsFactoryUtil.between("createDate", startDate, endDate));

		    stockToday = cafeVendorPurchasePersistence.findWithDynamicQuery(dynamicQuery, start, end);
		    
		} catch (Exception e) {
			_log.debug("Exception while fetching the getCafeInventoriesByDate data ::::::::: "+e.getMessage(), e);
		}
	    return stockToday;
	}
}