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
import com.plng.common.schema.exception.NoSuchFileMovementBWSException;
import com.plng.common.schema.exception.NoSuchFileMovementException;
import com.plng.common.schema.model.FileMovement;
import com.plng.common.schema.model.FileMovementBWS;
import com.plng.common.schema.service.base.FileMovementLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;



/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.FileMovement",
	service = AopService.class
)
public class FileMovementLocalServiceImpl
	extends FileMovementLocalServiceBaseImpl {
	
	@Override
	public List<FileMovement> getfileByUserId(long userId) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findByUserId(userId);
	}

	@Override
	public List<FileMovement> getAknowledByUser(long name) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentTo(name);
	}

	@Override
	public List<FileMovement> getActionPendingSentto(long userId) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentTo(userId);
	}

	

	@Override
	public List<FileMovement> getBybgUniqueIdAndSentby(String bgid, long sentby) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findByUniqueIdSentBy(bgid, sentby);
	}

	@Override
	public List<FileMovement> getBybgUniqueId(String bgid) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findByBgInitiationUniqueId(bgid);
	}

	@Override
	public List<FileMovement> getBybgUniqueIdAndSentto(String bgid, long sentto) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findByUniqueIdSentTo(bgid, sentto);
	}
	
	@Override
	public List<FileMovement> getByMovementIdAndbgUniqueIdAndSentto( long movmentId,String bgInitiationUniqueId, long sentTo) {
		return fileMovementPersistence.findByMovIdAndBgInitUniqIdAndSentTo(movmentId, bgInitiationUniqueId, sentTo);
	}

	@Override
	public List<FileMovement> getAknowledByUserId(long userId) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentTo(userId);
	}

	@Override
	public List<FileMovement> getActionPendingSentTo(long sentTo) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentTo(sentTo);
	}

    @Override
	public List<FileMovement> getBgsInCustody(long userId) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentTo(userId);
	}
    @Override
    public List<FileMovement> getBgsSent(long userId) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentBy(userId);
	}
	@Override
	public List<FileMovement> getActionpendingBySentByAndSentto(long sentby, long sentto) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findBySentByAndsentTo(sentby, sentto);
	}
	@Override
	public List<FileMovement> getCommentByBgInitUniqIdAndSentByAndSentto(String bgInitiationUniqueId,long sentby, long sentto) {
		// TODO Auto-generated method stub
		return fileMovementPersistence.findByBgInitUniqIdAndSentByAndSentto(bgInitiationUniqueId, sentby, sentto);
	}
	
	public FileMovement getFileMovementDetailsByBgInitiationUniqueIdAndUserId(String bgInitiationUniqueId, long userId)
			throws NoSuchFileMovementException{

		return fileMovementPersistence.findByBgUniqueIdAndUserId(bgInitiationUniqueId, userId);
	}
	public FileMovement getFileMovementDetailsByBgInitiationUniqueIdAndSenttoAndUserId(String bgInitiationUniqueId,long sentto, long userId)
			throws NoSuchFileMovementException{

		return fileMovementPersistence.findByBgUniqueIdAndSentoAndUserId(bgInitiationUniqueId, sentto, userId);

	
}
	
}