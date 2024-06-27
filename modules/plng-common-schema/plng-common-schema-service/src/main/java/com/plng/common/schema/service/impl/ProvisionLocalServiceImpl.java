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
import com.plng.common.schema.model.CanteenInventory;
import com.plng.common.schema.model.Provision;
import com.plng.common.schema.model.SaarthiTimeSheet;
import com.plng.common.schema.service.base.ProvisionLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Provision",
	service = AopService.class
)
public class ProvisionLocalServiceImpl extends ProvisionLocalServiceBaseImpl {
	
private  Log _log = LogFactoryUtil.getLog(getClass());
	
	public  List<Provision> getByEmp(long empId) {
	    try {
	        return provisionPersistence.findByEmpId(empId);
	    } catch (Exception e) {
	        _log.error("Exception while fetching data: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	public  List<Provision> getByApp(long appId) {
	    try {
	        return provisionPersistence.findByAppId(appId);
	    } catch (Exception e) {
	        _log.error("Exception while fetching data: " + e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	public List<Provision> getProvsByDateRange(Date fromDate,Date toDate) {
		List<Provision> provList =null;
		try {
			Calendar cal = Calendar.getInstance();
	        cal.setTime(fromDate);
	        cal.set(Calendar.HOUR_OF_DAY, 0); 
	        cal.set(Calendar.MINUTE, 0); 
	        cal.set(Calendar.SECOND, 0);
	        Date startDate = cal.getTime();
	        
	        cal.setTime(toDate);
	        cal.set(Calendar.HOUR_OF_DAY, 23); 
	        cal.set(Calendar.MINUTE, 59); 
	        cal.set(Calendar.SECOND, 59);
	        Date endDate = cal.getTime();
			
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Provision.class, ProvisionLocalServiceImpl.class.getClassLoader());
		    dynamicQuery.add(RestrictionsFactoryUtil.between("appSubmit", startDate, endDate));

		    provList = provisionPersistence.findWithDynamicQuery(dynamicQuery);
		    
		    
		} catch (Exception e) {
			_log.debug("Exception while fetching the data ::::::::: "+e.getMessage());
		}
	    return provList;
	}
	
}