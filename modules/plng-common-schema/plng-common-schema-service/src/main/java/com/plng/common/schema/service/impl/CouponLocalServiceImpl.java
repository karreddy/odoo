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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.CafeteriaCouponDetails;
import com.plng.common.schema.model.Coupon;
import com.plng.common.schema.model.CouponStatus;
import com.plng.common.schema.service.base.CouponLocalServiceBaseImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Coupon",
	service = AopService.class
)
public class CouponLocalServiceImpl extends CouponLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CouponLocalServiceImpl.class);

	@Override
	public Coupon saveCoupon(long empId,long cId,String req_for,long item_id,float requested_quant ,float value_quant, String month,String couponNo) {
		Coupon coupon=null;
		try {
			long couponId = counterLocalService.increment(Coupon.class.getName());
			coupon = couponPersistence.create(couponId);
			coupon.setCid(cId);
			coupon.setEmpid(empId);
			coupon.setReq_for(req_for);
			coupon.setItem_id(item_id);
			coupon.setRequested_quant(requested_quant);
			coupon.setValue_quant(value_quant);
			coupon.setMonth(month);
			coupon.setCreated(new Date());
			coupon.setCoupon_id(couponNo);
			 
			couponPersistence.update(coupon);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return coupon;
	}
	
	public void couponDeletion(String coupon_id)  {
		try {
			  couponPersistence.removeByCouponNO(coupon_id);
	    } catch (Exception e) {
	        _log.debug("Exception fetching data >>>" + e.getMessage());
	    }
	}
	
	@Override
	public List<Coupon> getAllTodaysCoupons(int start, int end) {
		List<Coupon> couponStatusToday =null;
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
			
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Coupon.class, CouponLocalServiceImpl.class.getClassLoader());
		    dynamicQuery.add(RestrictionsFactoryUtil.between("created", startDate, endDate));

		     couponStatusToday = couponStatusPersistence.findWithDynamicQuery(dynamicQuery, start, end);
		    
		} catch (Exception e) {
			_log.debug("Exception while fetching the coupons data ::::::::: "+e.getMessage(), e);
		}
	    return couponStatusToday;
	}

}