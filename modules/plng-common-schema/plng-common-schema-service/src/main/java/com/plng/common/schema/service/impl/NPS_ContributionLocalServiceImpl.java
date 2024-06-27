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
import com.plng.common.schema.exception.NoSuchNPS_ContributionException;
import com.plng.common.schema.model.NPS_Contribution;
import com.plng.common.schema.service.base.NPS_ContributionLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.NPS_Contribution",
	service = AopService.class
)
public class NPS_ContributionLocalServiceImpl
	extends NPS_ContributionLocalServiceBaseImpl {
	
	public NPS_Contribution getNPSContributionByEmpId(int employeeId) {

		NPS_Contribution nps = null;
		try {
			nps = nps_ContributionPersistence.findByemployeeId(employeeId);
		} catch ( NoSuchNPS_ContributionException e) {
			return null;
		}
		return nps;
	}
	
	public List<NPS_Contribution> getNPSContributionByForwardYear(String fy) {

		List<NPS_Contribution> nps = null;
		try {
			nps = nps_ContributionPersistence.findByforwardYear(fy);
		} catch (Exception e) {
			return null;
		}
		return nps;
	}
	public NPS_Contribution getNPSContributionByPran(String pran) {

		NPS_Contribution nps = null;
		try {
			nps = nps_ContributionPersistence.findBypran(pran);
		} catch (NoSuchNPS_ContributionException e) {
			return null;
		}
		return nps;
	}
}