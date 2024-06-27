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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.plng.common.schema.exception.NoSuchFileMovementBWSException;
import com.plng.common.schema.model.FileMovementBWS;
import com.plng.common.schema.service.base.FileMovementBWSLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.FileMovementBWS",
	service = AopService.class
)
public class FileMovementBWSLocalServiceImpl
	extends FileMovementBWSLocalServiceBaseImpl {
	OrderByComparator<FileMovementBWS> filePersistenceOrderByComparator = OrderByComparatorFactoryUtil
			.create("FileMovementBWS", "createDate", false);
	public List<FileMovementBWS> getFileMovementDetailsByUserId(long userId) {

		return fileMovementBWSPersistence.findByUserId(userId);
	}

	public List<FileMovementBWS> getFileMovementDetailsByBillInitiationUniqueId(String billInitiationUniqueId)
			throws NoSuchFileMovementBWSException {
		
		return fileMovementBWSPersistence.findByBillInitiationUniqueId(billInitiationUniqueId);
	}
	
	public FileMovementBWS getFileMovementDetailsByBillInitiationUniqueIdAndUserId(String billInitiationUniqueId, long userId)
			throws NoSuchFileMovementBWSException {

		return fileMovementBWSPersistence.findByBillInitiationUniqueIdAndUserId(billInitiationUniqueId, userId);
	}
	public List<FileMovementBWS> getFileMovementDetailsBybillInitiationUniqueId(String billInitiationUniqueId) {
		
		return fileMovementBWSPersistence.findBygetFileMovementBWSDetails(billInitiationUniqueId,  QueryUtil.ALL_POS, QueryUtil.ALL_POS, filePersistenceOrderByComparator);
	}
	public FileMovementBWS getFileMovementDetailsByBillInitiationUniqueIdAndSentUserId(String billInitiationUniqueId, long sentUserId)
			throws NoSuchFileMovementBWSException {

		return fileMovementBWSPersistence.findByBillInitiationUniqueIdAndSentUserId(billInitiationUniqueId, sentUserId);
	}
	public FileMovementBWS getFileMovementDetailsByBillInitiationUniqueIdAndSentUserIdAndUserId(String billInitiationUniqueId, long sentUserId, long userId)
			throws NoSuchFileMovementBWSException {
		
		return fileMovementBWSPersistence.findByBillInitiationUniqueIdAndSentUserIdAndUserId(billInitiationUniqueId, userId, sentUserId);
	}
	public List<FileMovementBWS> getFileMovementDetailsBybillInitiationUniqueIdAndUserId(String billInitiationUniqueId, long userId) {
		
		return fileMovementBWSPersistence.findByAllBillInitiationUniqueIdAndUserId(billInitiationUniqueId, userId);
	}
	public int getFileMovementDetailsBySendByCount(String sentBy) {
		
		return fileMovementBWSPersistence.countBySentBy(sentBy);
	}
	public List<FileMovementBWS> getFileMovementDetailsBySendBy(String sentBy) {
		
		return fileMovementBWSPersistence.findBySentBy(sentBy, QueryUtil.ALL_POS, QueryUtil.ALL_POS, filePersistenceOrderByComparator);
	}
	public long getFileMovementDetailsByBillInitiationUniqueIdCount(String billInitiationUniqueId)
			throws NoSuchFileMovementBWSException {
		
		return fileMovementBWSPersistence.countByBillInitiationUniqueId(billInitiationUniqueId);
	}
	public int countByUserId(long userId) {

		return fileMovementBWSPersistence.countByUserId(userId);
	}
	public List<FileMovementBWS> getFileMovementDetailsBySendByAndBillId(String sentBy, String billInitiationId) {
		
		return fileMovementBWSPersistence.findBySentByAndBillId(sentBy, billInitiationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, filePersistenceOrderByComparator);
				
	}
	public int getFileMovementDetailsBySendByAndBillIdCount(String sentBy, String billInitiationUniqueId) {
		
		return fileMovementBWSPersistence.countBySentByAndBillId(sentBy, billInitiationUniqueId);
	}
	public int countByAckOnDateAndSentToEmpIdAndPrefix(Date ackOnDateStart, Date ackOnDateEnd,
			String billInitiationUniqueIdPrefix) {
		try {
		return fileMovementBWSFinder.countByAckOnDateAndSentToEmpIdAndPrefix(ackOnDateStart, ackOnDateEnd, billInitiationUniqueIdPrefix);
		}catch (Exception e) {
		}
		return 0;
	}
	public int getFinalStatus(String billInitiationUniqueId) {
		try {
			return fileMovementBWSFinder.getFinalStatus(billInitiationUniqueId);
		}catch (Exception e) {
		}
		return 0;
	}
	public List<FileMovementBWS> getAllFinanceBill(Date startDate, Date endDate, String prefix) {
		try {
			return fileMovementBWSFinder.getAllFinanceBill(startDate, endDate, prefix);
		}catch (Exception e) {
		}
		return null;
	}
	public List<FileMovementBWS> findByUserId(long userId) {

		return fileMovementBWSPersistence.findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, filePersistenceOrderByComparator);
	}
	
	
}