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
import com.plng.common.schema.model.DashboardArticles;
import com.plng.common.schema.service.base.DashboardArticlesLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.DashboardArticles",
	service = AopService.class
)
public class DashboardArticlesLocalServiceImpl
	extends DashboardArticlesLocalServiceBaseImpl {
	
	public List<DashboardArticles> getarticleBystatus(String status) {
		// TODO Auto-generated method stub
		return  dashboardArticlesPersistence.findByStatusByLabel(status);
	}
	
	public List<DashboardArticles>getarticlesListByUserId(long userId){
		return dashboardArticlesPersistence.findBysubmittedBy(userId);	
	}
}