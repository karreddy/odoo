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
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.CafeteriaCouponDetails;
import com.plng.common.schema.service.base.CafeteriaCouponDetailsLocalServiceBaseImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeteriaCouponDetails",
	service = AopService.class
)
public class CafeteriaCouponDetailsLocalServiceImpl
	extends CafeteriaCouponDetailsLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CafeteriaCouponDetailsLocalServiceImpl.class);

	@Override
	public CafeteriaCouponDetails addCafeteriaCouponDetails(long empId ,long companyId,long groupId,String date ,String eName,String requestFor,String item ,long availQty,long reqQty,float itemVal ) {
		CafeteriaCouponDetails cafeteriaCouponDetails=null;
		try {
			long cafId = counterLocalService.increment(CafeteriaCouponDetails.class.getName());
			cafeteriaCouponDetails = cafeteriaCouponDetailsPersistence.create(cafId);
			cafeteriaCouponDetails.setCafId(cafId);
			cafeteriaCouponDetails.setGroupId(groupId);
			cafeteriaCouponDetails.setCompanyId(companyId);
			cafeteriaCouponDetails.setUserId(empId);
			cafeteriaCouponDetails.setCreateDate(new Date());
			cafeteriaCouponDetails.setDate_(date);
			cafeteriaCouponDetails.setEmplooyeeName(eName);
			cafeteriaCouponDetails.setReqFor(requestFor);
			cafeteriaCouponDetails.setItem(item);
			cafeteriaCouponDetails.setItemQuantity(availQty);
			cafeteriaCouponDetails.setItemValue(itemVal);
			
			cafeteriaCouponDetailsPersistence.update(cafeteriaCouponDetails);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return cafeteriaCouponDetails;
	}
	
	@Override
	public List<CafeteriaCouponDetails> getCafeteriaDetailsByItem(String item ){
		try {
	        return cafeteriaCouponDetailsPersistence.findByItemAndInv(item);
	    } catch (Exception e) {
	        _log.debug("Error while fetching data : " +  e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	@Override
	public List<CafeteriaCouponDetails> getCafeteriaCouponDetailses(int start, int end) {
		try {
			return super.getCafeteriaCouponDetailses(start, end);
		} catch (Exception e) {
			 _log.debug("Error while fetching data : " +  e.getMessage());
		        return Collections.emptyList();
		}
	}
	
	@Override
	public List<CafeteriaCouponDetails> getByUserId(long userId) {
		try {
			return cafeteriaCouponDetailsPersistence.findByUserId(userId);
		} catch (Exception e) {
			 _log.debug("Error while fetching data : " +  e.getMessage());
		        return Collections.emptyList();
		}
	}
	@Override
	public List<CafeteriaCouponDetails> getByUserIdAndDate(long userId, int month, int year) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.MONTH, month - 1); 
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.DAY_OF_MONTH, 1); 

	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    Date startDate = calendar.getTime();

	    calendar.add(Calendar.MONTH, 1); 

	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    Date endDate = calendar.getTime();

	    DynamicQuery dynamicQuery = dynamicQuery();

	    dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", userId));
	    dynamicQuery.add(RestrictionsFactoryUtil.ge("createDate", startDate));
	    dynamicQuery.add(RestrictionsFactoryUtil.lt("createDate", endDate));

	    try {
			return cafeteriaCouponDetailsLocalService.dynamicQuery(dynamicQuery);
		} catch (Exception e) {
			 _log.debug("Error while fetching data : " +  e.getMessage());
		        return Collections.emptyList();
		}
	}

	public List<CafeteriaCouponDetails> getAmountByUserIdAndCurrentMonth(long userId) {
	    LocalDate currentDate = LocalDate.now();
	    int currentMonth = currentDate.getMonthValue();
	    int currentYear = currentDate.getYear();

	    LocalDate startOfMonth = LocalDate.of(currentYear, currentMonth, 1);
	    LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());
	    List<CafeteriaCouponDetails> filteredList = null;
	    List<CafeteriaCouponDetails> amountByUserId = null;
		try {
			amountByUserId = cafeteriaCouponDetailsPersistence.findByUserId(userId);
			filteredList = new ArrayList<>();
			
			for (CafeteriaCouponDetails cafeteriaCouponDetails : amountByUserId) {
			    Date createDate = cafeteriaCouponDetails.getCreateDate();
			    LocalDateTime createLocalDateTime = LocalDateTime.ofInstant(createDate.toInstant(), ZoneOffset.UTC);
			    LocalDate createLocalDate = createLocalDateTime.toLocalDate();
			    if (createLocalDate.isEqual(startOfMonth) || (createLocalDate.isAfter(startOfMonth) && createLocalDate.isBefore(endOfMonth))) {
			        filteredList.add(cafeteriaCouponDetails);
			    }
			}
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}

	    return filteredList;
	}
	
	public List<CafeteriaCouponDetails> getItemQtyByUser(long userId,String date,String item) {
		List<CafeteriaCouponDetails> byUserIdAndItemAndDate =null;
		try {
			byUserIdAndItemAndDate = cafeteriaCouponDetailsPersistence.findByUserIdItemAndDate(userId, date, item);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return byUserIdAndItemAndDate;
	}
	
	public void updateCouponDetail(long userId,String date_,String item,long itemQuantity)  {
		CafeteriaCouponDetails couponDetails =null;
		try {
	       couponDetails = cafeteriaCouponDetailsPersistence.findByUserIdItemAndQty(userId, date_, item, itemQuantity);
	       
	        if (couponDetails != null) {
	        	couponDetails.setItemQuantity(0);
	        	couponDetails.setItemValue(0);
	        	cafeteriaCouponDetailsPersistence.update(couponDetails);
	        } else {
	            _log.error("Record Not Found.");
	        }
	    } catch (Exception e) {
	        _log.debug("Error while fetching Data "+ e.getMessage());
	    }
	}
}