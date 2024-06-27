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
import com.plng.common.schema.model.CafeVendorMaster;
import com.plng.common.schema.service.base.CafeVendorMasterLocalServiceBaseImpl;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeVendorMaster",
	service = AopService.class
)
public class CafeVendorMasterLocalServiceImpl
	extends CafeVendorMasterLocalServiceBaseImpl {
	
	private static final Log _log = LogFactoryUtil.getLog(CafeVendorMasterLocalServiceImpl.class);

	@Override
	public CafeVendorMaster addCafeteriaVendorMaster(String vendorName,String vendorAddress,String vendorSapId) {
		CafeVendorMaster cafeVendorMaster=null;
		try {
			long vendorId = counterLocalService.increment(CafeVendorMaster.class.getName());
			cafeVendorMaster = cafeVendorMasterPersistence.create(vendorId);
			cafeVendorMaster.setVendorId(vendorId);;
			cafeVendorMaster.setVendorName(vendorName);
			cafeVendorMaster.setVendorAddress(vendorAddress);
			cafeVendorMaster.setVendorSapId(vendorSapId);
			cafeVendorMasterPersistence.update(cafeVendorMaster);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return cafeVendorMaster;
	}
	
	@Override
	public List<CafeVendorMaster> getCafeVendorMasterDetails(int start, int end) {
			try {
				return super.getCafeVendorMasters(start, end);
			} catch (Exception e) {
				 _log.debug("Error while fetching data: " + e.getMessage());
			     return Collections.emptyList();
			}
		}
}