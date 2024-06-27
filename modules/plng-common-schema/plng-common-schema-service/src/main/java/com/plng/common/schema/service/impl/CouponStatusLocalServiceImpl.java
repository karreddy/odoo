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
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;
import com.plng.common.schema.model.CouponStatus;
import com.plng.common.schema.service.CouponLocalService;
import com.plng.common.schema.service.InventoryQtyLocalService;
import com.plng.common.schema.service.base.CouponStatusLocalServiceBaseImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CouponStatus",
	service = AopService.class
)
public class CouponStatusLocalServiceImpl
	extends CouponStatusLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CouponStatusLocalServiceImpl.class);

	@Override
	public CouponStatus savecouponStatus(long empId,long cId,String req_for,long item_id,float requested_quant ,float value_quant,String month,String couponNo) {
		CouponStatus couponStatus=null;
		try {
			long couponStatusId = counterLocalService.increment(CouponStatus.class.getName());
			couponStatus = couponStatusPersistence.create(couponStatusId);
			couponStatus.setCid(cId);
			couponStatus.setEmpid(empId);
			couponStatus.setMonth(month);
			couponStatus.setCreated(new Date());
			couponStatus.setStatus("New");
			couponStatus.setReq_for(req_for);
			couponStatus.setRequested_quant(requested_quant);
			couponStatus.setValue_quant(value_quant);
			couponStatus.setItem_id(item_id);
			couponStatus.setCoupon_id(couponNo);
			
			couponStatusPersistence.update(couponStatus);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return couponStatus;
	}
	
	public List<CouponStatus> getCouponsByUser(long empId,Date date) {
		List<CouponStatus> CouponStatusListByUser  =null;
			try {
				  CouponStatusListByUser = couponStatusPersistence.findByEmpIdAndDate(empId, date);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return CouponStatusListByUser;
		}
	
	public void updateCoupon(String coupon_id)  {
		CouponStatus couponStatus = null;
		
		try {
			  couponStatus = couponStatusPersistence.findByCouponId(coupon_id);
			  couponStatus.setStatus("Issued");
			  couponStatusPersistence.update(couponStatus);
	    } catch (Exception e) {
	        _log.debug("Exception fetching data >>>" + e.getMessage());
	    }
	}
	
	
	public List<CouponStatus> getCouponsByUser(long empId,Date date, int start, int end) {
		List<CouponStatus> couponStatusToday =null;
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
			
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CouponStatus.class, CouponStatusLocalServiceImpl.class.getClassLoader());
		    dynamicQuery.add(RestrictionsFactoryUtil.between("created", startDate, endDate));
		    dynamicQuery.add(PropertyFactoryUtil.forName("empid").eq(empId));
		    
		    

		     couponStatusToday = couponStatusPersistence.findWithDynamicQuery(dynamicQuery, start, end);
		    
		} catch (Exception e) {
			_log.debug("Exception while fetching the getCafeInventoriesByDate data ::::::::: "+e.getMessage(), e);
		}
	    return couponStatusToday;
	}
	
	public void couponsExpiration() {
		List<CouponStatus> couponsToDelete =null;
	    try {
	    	 LocalDateTime currentTime = LocalDateTime.now();
	    	   couponsToDelete = couponStatusPersistence.findByStatus("New");
	    	 
	    	 JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
	            long empId = 0 ;
	            String jsonString=null;
	        for (CouponStatus coupon : couponsToDelete) {
	            LocalDateTime couponCreationTime = coupon.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	            long minutesDifference = ChronoUnit.MINUTES.between(couponCreationTime, currentTime);
	           
	            if (minutesDifference > 30) {
	            	empId = coupon.getEmpid();
	            	String coupon_id = coupon.getCoupon_id();
	            	long item_id = coupon.getItem_id();
	            	float qty = coupon.getRequested_quant();
	            	coupon.setStatus("Expired");
	            	
	        		String message="Your order with coupon code : "+coupon_id+" has been expired.The Coupon is Auto-deleted";
	        		messageJSONObject.put("userId", empId);
	        		messageJSONObject.put("notificationText", message);
	        		messageJSONObject.put("title", "Canteen Coupon Issuance.");
	        		messageJSONObject.put("senderName", "System");
	        		messageJSONObject.put("link","/cafeteria");
	        		jsonString= messageJSONObject.toString();
	              
	        		couponStatusPersistence.update(coupon);
	        		_inventoryQtyLocalService.rollBackInventoryQty(item_id, qty);
	        		_couponLocalService.couponDeletion(coupon_id);
	                _log.info("Coupon Expired : " +  coupon_id);
	            }
	        }
	        UserNotificationEventLocalServiceUtil.addUserNotificationEvent(empId,"com_cafeteria_CafeteriaPortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,0, jsonString, false,new ServiceContext());
	    } catch (Exception e) {
	    	 _log.debug(e.getMessage());
	    }
	}
	
	public List<CouponStatus> getMonthlyDetails(long empId, int month, int year) {
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

	    dynamicQuery.add(RestrictionsFactoryUtil.eq("empid", empId));
	    dynamicQuery.add(RestrictionsFactoryUtil.ge("created", startDate));
	    dynamicQuery.add(RestrictionsFactoryUtil.lt("created", endDate));
 
	    try {
			return couponStatusLocalService.dynamicQuery(dynamicQuery);
		} catch (Exception e) {
			 _log.debug("Error while fetching data : " +  e.getMessage());
		        return Collections.emptyList();
		}
	}
	
	public List<CouponStatus> getTotalAmount(long empid) {
	    float totalAmount = 0.0f;
	    List<CouponStatus> totalAmountList =null;
	    try {
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.DAY_OF_MONTH, 1); 
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        Date startDate = cal.getTime();
	        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); 
	        cal.set(Calendar.HOUR_OF_DAY, 23);
	        cal.set(Calendar.MINUTE, 59);
	        cal.set(Calendar.SECOND, 59);
	        Date endDate = cal.getTime();
	        DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CouponStatus.class, CouponStatusLocalServiceImpl.class.getClassLoader());
	        dynamicQuery.add(RestrictionsFactoryUtil.between("created", startDate, endDate));
	        dynamicQuery.add(RestrictionsFactoryUtil.eq("empid", empid));

	       totalAmountList = couponStatusLocalService.dynamicQuery(dynamicQuery);

	    } catch (Exception e) {
	        _log.error("Error occurred while fetching data from database: " + e.getMessage(), e);
	    }
	    return totalAmountList;
	}



	public JSONObject getTotalQuantityByItem(long empid, Date created, int start, int end) {
	    JSONObject totalQuantityByItemJSON = JSONFactoryUtil.createJSONObject();
	    try {
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        Date startDate = cal.getTime();
	        cal.set(Calendar.HOUR_OF_DAY, 23);
	        cal.set(Calendar.MINUTE, 59);
	        cal.set(Calendar.SECOND, 59);
	        Date endDate = cal.getTime();

	        DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CouponStatus.class, CouponStatusLocalServiceImpl.class.getClassLoader());
	        dynamicQuery.add(RestrictionsFactoryUtil.between("created", startDate, endDate));
	        dynamicQuery.add(RestrictionsFactoryUtil.eq("empid", empid));

	        List<CouponStatus> qtyByEmpAndDate = couponStatusLocalService.dynamicQuery(dynamicQuery);
	        
	        for (CouponStatus couponStatus : qtyByEmpAndDate) {
	            long itemId = couponStatus.getItem_id();
	            float requestedQuant = couponStatus.getRequested_quant();
	            totalQuantityByItemJSON.put(String.valueOf(itemId), requestedQuant);
	        }
	    } catch (Exception e) {
	        _log.error("Error occurred while fetching data from database: " + e.getMessage(), e);
	    }
	    return totalQuantityByItemJSON;
	}
	
	
	public List<CouponStatus> getCouponsByUser(long item_id,String status) {
		List<CouponStatus> byItemIdStatus =null;
			try {
				byItemIdStatus = couponStatusPersistence.findByItemIdStatus(item_id, status);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return byItemIdStatus;
		}
	
	public List<CouponStatus> getAllTodaysCoupons() {
	    List<CouponStatus> couponStatusToday = null;
	    try {
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.HOUR_OF_DAY, 0); 
	        cal.set(Calendar.MINUTE, 0); 
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        Date startDate = cal.getTime();

	        cal.set(Calendar.HOUR_OF_DAY, 23); 
	        cal.set(Calendar.MINUTE, 59); 
	        cal.set(Calendar.SECOND, 59);
	        cal.set(Calendar.MILLISECOND, 999);
	        Date endDate = cal.getTime();

	        DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CouponStatus.class, CouponStatusLocalServiceImpl.class.getClassLoader());
	        dynamicQuery.add(RestrictionsFactoryUtil.between("created", startDate, endDate));
	        dynamicQuery.add(RestrictionsFactoryUtil.eq("status", "Issued"));

	        couponStatusToday = couponStatusPersistence.findWithDynamicQuery(dynamicQuery);
	    } catch (Exception e) {
	        _log.debug("Exe while fetching Today's Coupons >>>" + e.getMessage());
	    }
	    return couponStatusToday;
	}

	
	@Reference private CouponLocalService _couponLocalService;
	@Reference private InventoryQtyLocalService _inventoryQtyLocalService;
}