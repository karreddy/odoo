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
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.service.base.CafeItemMasterLocalServiceBaseImpl;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.CafeItemMaster",
	service = AopService.class
)
public class CafeItemMasterLocalServiceImpl
	extends CafeItemMasterLocalServiceBaseImpl {
	private static final Log _log = LogFactoryUtil.getLog(CafeItemMasterLocalServiceImpl.class);

	@Override
	public CafeItemMaster addCafeItemMaster(String itemDesc,String itemUnits) {
		_log.info("CafeItemMasterLocalServiceImpl.addCafeItemMaster()...");
		CafeItemMaster cafeItemMaster=null;
		try {
			long itemId = counterLocalService.increment(CafeItemMaster.class.getName());
			cafeItemMaster = cafeItemMasterPersistence.create(itemId);
			cafeItemMaster.setItemId(itemId);
			cafeItemMaster.setItemDesc(itemDesc);
			cafeItemMaster.setItemUnits(itemUnits);
			cafeItemMaster.setStatus(1);
			cafeItemMasterPersistence.update(cafeItemMaster);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		return cafeItemMaster;
	}
	
	@Override
	public List<CafeItemMaster> getCafeVendorMasterDetails(int start, int end) {
			try {
				return super.getCafeItemMasters(start, end);
			} catch (Exception e) {
				_log.debug(e.getMessage());
				 return Collections.emptyList();
			}
		}
}