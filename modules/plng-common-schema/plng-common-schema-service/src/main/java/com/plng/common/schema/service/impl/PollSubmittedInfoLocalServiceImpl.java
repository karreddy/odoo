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
import com.plng.common.schema.exception.NoSuchPollOptionException;
import com.plng.common.schema.exception.NoSuchPollSubmittedInfoException;
import com.plng.common.schema.model.PollOption;
import com.plng.common.schema.model.PollSubmittedInfo;
import com.plng.common.schema.service.base.PollSubmittedInfoLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;



/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.PollSubmittedInfo",
	service = AopService.class
)
public class PollSubmittedInfoLocalServiceImpl
	extends PollSubmittedInfoLocalServiceBaseImpl {
	
	@Override
	public List<PollSubmittedInfo> findByuserId(long userId) {
		// TODO Auto-generated method stub
		return this.pollSubmittedInfoPersistence.findByuserId(userId);
	}
	@Override
	public List<PollSubmittedInfo> getByUserIdAndQid(long userId ,long qid) {
		// TODO Auto-generated method stub
		return this.pollSubmittedInfoPersistence.findByUserIdAndQid(userId, qid);
	}
	@Override
	public PollSubmittedInfo getByQid(long qid) {
		// TODO Auto-generated method stub
		PollSubmittedInfo  pollSubmittedInfo=null;
		try {
			pollSubmittedInfo=pollSubmittedInfoPersistence.findByQid(qid);
		} catch (NoSuchPollSubmittedInfoException e) {
		}
		// TODO Auto-generated method stub
		return pollSubmittedInfo;
	}
}