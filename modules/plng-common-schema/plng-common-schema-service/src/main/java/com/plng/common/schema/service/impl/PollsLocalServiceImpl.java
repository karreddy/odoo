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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.plng.common.schema.exception.NoSuchPollsException;
import com.plng.common.schema.model.Polls;
import com.plng.common.schema.service.base.PollsLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Polls",
	service = AopService.class
)
public class PollsLocalServiceImpl extends PollsLocalServiceBaseImpl {
	
	public List<Polls> findByStatus(String status){
		return this.pollsPersistence.findByStatus(status);
	}
	public List<Polls> getAllPolls(){
		return this.pollsPersistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	public List<Polls> getPollsByUserId(long userId){
		return this.pollsPersistence.findByUserId(userId);
	}
	@Override
	public Polls getByQid(long qid) {
		Polls polls=null;
		try {
			polls=pollsPersistence.findByQid(qid);
		} catch (NoSuchPollsException e) {
		}
		// TODO Auto-generated method stub
		return polls;
	}
	
}