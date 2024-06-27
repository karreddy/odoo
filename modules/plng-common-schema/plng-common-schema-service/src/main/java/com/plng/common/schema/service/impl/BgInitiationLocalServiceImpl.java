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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.plng.common.schema.exception.NoSuchBgInitiationException;
import com.plng.common.schema.model.BgInitiation;
import com.plng.common.schema.service.base.BgInitiationLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.BgInitiation",
	service = AopService.class
)
public class BgInitiationLocalServiceImpl
	extends BgInitiationLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(BgInitiationLocalServiceImpl.class);
	
	@Override
	public List<BgInitiation> getAknowledByUsername(String name) {
		
		return bgInitiationPersistence.findByName(name);
	}

	

	@Override
	public List<BgInitiation> getbgInitiatedByUserId(long userId) {
		// TODO Auto-generated method stub
		return bgInitiationPersistence.findByuserId(userId);
	}
	@Override
	public List<BgInitiation> getbgByBgUniqueId(String bguniqueId) {
		// TODO Auto-generated method stub
		return bgInitiationPersistence.findByBgInitiationUniqueId(bguniqueId);
	}
	@Override
	public List<BgInitiation> getEicByUerId(long userId) {
		// TODO Auto-generated method stub
		return bgInitiationPersistence.findByuserId(userId);
	}
	@Override
	public List<BgInitiation> getBgByStatus(String  status) {
		// TODO Auto-generated method stub
		return bgInitiationPersistence.findByStatus(status);
}
	



	@Override
	public List<BgInitiation> getBgInCustodys(String bgInCustody ,String location) {
		// TODO Auto-generated method stub
		return bgInitiationPersistence.findByBgInCustody(bgInCustody, location);
	}



	@Override
	public List<BgInitiation> getBgReturn(String bgReturn) {
		// TODO Auto-generated method stub
		return bgInitiationPersistence.findByBgReturn(bgReturn, -1, -1);
	}
	
	@Override
	public BgInitiation getBgDetailsByBgUniqueId(String bgInitiationUniqueId) {
		BgInitiation bgInitation=null;
		try {
			bgInitation= bgInitiationPersistence.findByBgInitiationId(bgInitiationUniqueId);
		} catch (NoSuchBgInitiationException e) {
		
			e.printStackTrace();
		}
		return bgInitation;
	}



	@Override
	public List<BgInitiation> getBgInCustodys(String bgInCustody) {
		_log.info(bgInCustody);
		return bgInitiationPersistence.findByBgsInCustody("yes",-1,-1);
	}
	
	
	
}
	

	
	
	
