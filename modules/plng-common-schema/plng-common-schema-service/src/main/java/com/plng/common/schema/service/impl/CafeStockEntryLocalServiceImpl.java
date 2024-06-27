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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.CafeStockEntry;
import com.plng.common.schema.service.base.CafeStockEntryLocalServiceBaseImpl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeStockEntry",
	service = AopService.class
)
public class CafeStockEntryLocalServiceImpl
	extends CafeStockEntryLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CafeStockEntryLocalServiceImpl.class);

	@Override
	public CafeStockEntry addStockEntryDetails(String vendorName,String itemName,long currentQty,long quantity,float unitPrice,String invoiceNo,Date invoiceDate,float totalAmount) {
	
		CafeStockEntry cafeStockEntry = null;
		List<CafeStockEntry> cafeStocksByItemList =null;
	    try {
	    	cafeStocksByItemList=getCafeStocksByItemAndDate(itemName, invoiceDate);

			if (!cafeStocksByItemList.isEmpty()) {
			    _log.info("Entry found for today, updating >>>");
			    cafeStockEntry = cafeStocksByItemList.get(0);
			    cafeStockEntry.setVendor(vendorName);
			    cafeStockEntry.setItem(itemName);
			    cafeStockEntry.setCurrentStockQty(cafeStockEntry.getCurrentStockQty()+quantity);
			    cafeStockEntry.setQuantity(cafeStockEntry.getQuantity() + quantity);
			    cafeStockEntry.setItemUnitPrice(unitPrice);
			    cafeStockEntry.setInvoiceNo(invoiceNo);
			    cafeStockEntry.setInvoiceDate(invoiceDate);
			    cafeStockEntry.setTotalAmount(totalAmount);

			    cafeStockEntryPersistence.update(cafeStockEntry);
			} else {
			    _log.info("No entry found for today, creating a new entry. >>>");
			    long stockId = counterLocalService.increment(CafeStockEntry.class.getName());
			    cafeStockEntry = cafeStockEntryPersistence.create(stockId);
			    cafeStockEntry.setStockEntryId(stockId);
			    cafeStockEntry.setVendor(vendorName);
			    cafeStockEntry.setItem(itemName);
			    cafeStockEntry.setCurrentStockQty(currentQty);
			    cafeStockEntry.setQuantity(quantity);
			    cafeStockEntry.setItemUnitPrice(unitPrice);
			    cafeStockEntry.setInvoiceNo(invoiceNo);
			    cafeStockEntry.setInvoiceDate(invoiceDate);
			    cafeStockEntry.setCreateDate(new Date()); 
			    cafeStockEntry.setTotalAmount(totalAmount);

			    cafeStockEntryPersistence.update(cafeStockEntry);
			}
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}

	    return cafeStockEntry;
	}
	public List<CafeStockEntry> getCafeStocksByItemAndDate(String item, Date invoiceDate) {
	    try {
	        return cafeStockEntryPersistence.findByItem(item, invoiceDate);
	    } catch (Exception e) {
	    	 _log.error("Error while fetching data " + e.getMessage());
		        return Collections.emptyList();
	    }
	}
	
	public List<CafeStockEntry> getCafeStockEntriesByDate(Date date, int start, int end) {
		   
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CafeStockEntry.class, CafeStockEntryLocalServiceImpl.class.getClassLoader());
	    dynamicQuery.add(PropertyFactoryUtil.forName("invoiceDate").eq(date));

	    try {
			return cafeStockEntryPersistence.findWithDynamicQuery(dynamicQuery, start, end);
		} catch (Exception e) {
			 _log.error("Error while fetching data " + e.getMessage());
		        return Collections.emptyList();
		}
	}
	
	@Override
	public List<CafeStockEntry> getCafeStockEntryDetails(int start, int end) {
		try {
			return super.getCafeStockEntries(start, end);
		} catch (Exception e) {
			 _log.error("Error while fetching data " + e.getMessage());
		        return Collections.emptyList();
		}
	}
	
}