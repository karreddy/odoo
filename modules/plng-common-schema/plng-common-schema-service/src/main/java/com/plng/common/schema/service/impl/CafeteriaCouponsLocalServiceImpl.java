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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.plng.common.schema.exception.NoSuchCafeteriaCouponsException;
import com.plng.common.schema.model.CafeteriaCoupons;
import com.plng.common.schema.service.CafeInventoryLocalService;
import com.plng.common.schema.service.CafeteriaCouponDetailsLocalService;
import com.plng.common.schema.service.base.CafeteriaCouponsLocalServiceBaseImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeteriaCoupons",
	service = AopService.class
)
public class CafeteriaCouponsLocalServiceImpl extends CafeteriaCouponsLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CafeteriaCouponsLocalServiceImpl.class);

	@Override
	public CafeteriaCoupons generateCoupon(long userId ,String userName,String date,String reqFor,String itemName,long itemQuantity,float itemValue,String status ) {
		_log.info("...CafeteriaCouponsLocalServiceImpl...");
		CafeteriaCoupons cafeteriaCoupons =null;
		try {
			long couponId = counterLocalService.increment(CafeteriaCoupons.class.getName());
			cafeteriaCoupons = cafeteriaCouponsPersistence.create(couponId);
			 
			cafeteriaCoupons.setCouponId(couponId);
			cafeteriaCoupons.setCreateDate(new Date());
			cafeteriaCoupons.setUserId(userId);
			cafeteriaCoupons.setUserName(userName);
			cafeteriaCoupons.setDate(date);
			cafeteriaCoupons.setReqFor(reqFor);
			cafeteriaCoupons.setItemName(itemName);
			cafeteriaCoupons.setItemQuantity(itemQuantity);
			cafeteriaCoupons.setItemValue(itemValue);
			 String couponNo = generateCouponNumber(userId, reqFor, date, couponId);
			cafeteriaCoupons.setCouponNo(couponNo);
			cafeteriaCoupons.setStatus(status);
			cafeteriaCouponsPersistence.update(cafeteriaCoupons);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return cafeteriaCoupons;
	}
	public List<CafeteriaCoupons> getCouponDetailByUser(long userId,String date,String status) {
		 List<CafeteriaCoupons> couponsByUser =null;
			try {
				couponsByUser = cafeteriaCouponsPersistence.findByUserIdAndDateStatus(userId, date, status);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return couponsByUser;
		}
	
	public List<CafeteriaCoupons> getCouponsByUser(long userId,String date) {
		 List<CafeteriaCoupons> couponsByUser =null;
			try {
				couponsByUser = cafeteriaCouponsPersistence.findByUserIdAndDate(userId, date);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return couponsByUser;
		}
	
	public List<CafeteriaCoupons> getAllCoupons(String date,String status) {
		List<CafeteriaCoupons> allCoupons = null;
			try {
				 allCoupons = cafeteriaCouponsPersistence.findByDateAndStatus(date, status);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return allCoupons;
		}
	
	public void updateCoupon(String couponNo)  {
		CafeteriaCoupons coupon =null;
		try {
	        coupon = cafeteriaCouponsPersistence.findByCouponNoAndStatus(couponNo, "Active");
	        if (coupon != null) {
	        	coupon.setStatus("Issued");
	            cafeteriaCouponsPersistence.update(coupon);
	            _log.info("Coupon " + couponNo + " found and has been deleted.");
	        } else {
	            _log.info("Coupon " + couponNo + " not found.");
	        }
	    } catch (NoSuchCafeteriaCouponsException e) {
	        _log.debug("Error while deleting coupon with coupon number " + couponNo + ": " + e.getMessage());
	    }
		}
	
	private String generateCouponNumber(long userId, String reqFor, String date, long couponId) {
		//coupon id format 837SB1069 
		//-->837-userID   S-reqFor B-Month 1069-Cid
	    String[] dateParts = date.split("-");
	    int monthIndex = Integer.parseInt(dateParts[1]) - 1;
	    String[] months = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
	    String month = months[monthIndex];
	    reqFor = reqFor.equalsIgnoreCase("self") ? "S" : "G";
	    
	    String couponNo = userId + reqFor + month + couponId;

	    return couponNo;
	}
	
	public void couponsExpiration() {
		List<CafeteriaCoupons> couponsToDelete =null;
	    try {
	    	 LocalDateTime currentTime = LocalDateTime.now();
	    	 couponsToDelete =cafeteriaCouponsPersistence.findByStatus("Active");
	    	 JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
	            long userId = 0 ;
	            String jsonString=null;
	        for (CafeteriaCoupons coupon : couponsToDelete) {
	            LocalDateTime couponCreationTime = coupon.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	            long minutesDifference = ChronoUnit.MINUTES.between(couponCreationTime, currentTime);
	           
	            if (minutesDifference > 30) {
	            	 userId = coupon.getUserId();
	            	String itemName = coupon.getItemName();
	            	long itemQuantity = coupon.getItemQuantity();
	            	 String date = coupon.getDate();
	            	 Date createDate = coupon.getCreateDate();
	            	 String couponNo = coupon.getCouponNo();
	            	 
	            	coupon.setStatus("Expired");
	            	
	        		String message="Your order with coupon code : "+couponNo+" For "+itemQuantity+" "+itemName+" has been expired.The Coupon is Auto-deleted";
	        		messageJSONObject.put("userId", userId);
	        		messageJSONObject.put("notificationText", message);
	        		messageJSONObject.put("title", "Canteen Coupon Issuance.");
	        		messageJSONObject.put("senderName", "System");
	        		messageJSONObject.put("link","/cafeteria");
	        		jsonString= messageJSONObject.toString();
	              
	                cafeteriaCouponsPersistence.update(coupon);
	                _cafeteriaCouponDetailsLocalService.updateCouponDetail(userId, date, itemName, itemQuantity);
	                _cafeInventoryLocalService.updateCoupon(itemName, createDate, itemQuantity);
	                _log.info("Coupon Expired : " + coupon.getCouponId());
	            }
	        }
	        UserNotificationEventLocalServiceUtil.addUserNotificationEvent(userId,"com_cafeteria_CafeteriaPortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,0, jsonString, false,new ServiceContext());
	    } catch (Exception e) {
	    	 _log.debug(e.getMessage());
	    }
	}
	
	public CafeteriaCoupons getCouponDetails(String couponNo) {
		CafeteriaCoupons couponDetails =null;
			try {
			 couponDetails = cafeteriaCouponsPersistence.findByCouponNo(couponNo);
			} catch (Exception e) {
				_log.debug("Exception While Fetching data >>>"+e.getMessage());
			}
			  return couponDetails;
		}
	@Reference private CafeteriaCouponDetailsLocalService _cafeteriaCouponDetailsLocalService;
	@Reference private CafeInventoryLocalService _cafeInventoryLocalService;
}