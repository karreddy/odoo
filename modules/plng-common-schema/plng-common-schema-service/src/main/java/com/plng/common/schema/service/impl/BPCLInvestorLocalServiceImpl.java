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
import com.plng.common.schema.model.BPCLInvestor;
import com.plng.common.schema.service.base.BPCLInvestorLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.BPCLInvestor",
	service = AopService.class
)
public class BPCLInvestorLocalServiceImpl
	extends BPCLInvestorLocalServiceBaseImpl {
	public List<BPCLInvestor> getBpclByFolioNo(String folioNo) {
		List<BPCLInvestor> bpcl = new ArrayList<>();
		try {
			bpcl = bpclInvestorPersistence.findByfolioNo(folioNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpcl;
	}
	public List<BPCLInvestor> getBpclByDemartNo(String dematNo) {
		List<BPCLInvestor> bpcl = new ArrayList<>();
		try {
			bpcl = bpclInvestorPersistence.findBydematNo(dematNo);
		} catch (Exception e) {	
			e.printStackTrace();
		}
		return bpcl;
	}
	public List<BPCLInvestor> getBpclByDemartNoAndFolioNo(String dematNo, String folioNo) {
		List<BPCLInvestor> bpcl = new ArrayList<>();
		try {
			bpcl = bpclInvestorPersistence.findBydematNoAndFolioNo(dematNo, folioNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpcl;
	}
	
}