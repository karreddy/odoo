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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.plng.common.schema.exception.NoSuchBillInitiationException;
import com.plng.common.schema.exception.NoSuchFileMovementBWSException;
import com.plng.common.schema.model.BillInitiation;
import com.plng.common.schema.model.FileMovementBWS;
import com.plng.common.schema.service.base.BillInitiationLocalServiceBaseImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.BillInitiation",
	service = AopService.class
)
public class BillInitiationLocalServiceImpl
	extends BillInitiationLocalServiceBaseImpl {
	public BillInitiation addBill(BillInitiation billInitiation) {
		

		billInitiation = billInitiationPersistence.update(billInitiation);

		return billInitiation;

	}

	public BillInitiation updateBillDetails(BillInitiation billInitiation) {
			

		billInitiation = billInitiationPersistence.update(billInitiation);

		return billInitiation;

	}

	private String getUniqueBillId(long BillId) {
		SimpleDateFormat smt = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = smt.format(new Date());

		return formattedDate + BillId;
	}

	public BillInitiation getBillInitiationDetailsByUniqueId(String billInitiationUniqueId) {
		BillInitiation billInitiation = null;
		try {
			billInitiation = billInitiationPersistence.findByBillInitiationUniqueId(billInitiationUniqueId);
		} catch (NoSuchBillInitiationException e) {
			e.printStackTrace();
		}
		return billInitiation;
	}
	/**
	 * 
	 * @param userId
	 * @param statusFlag
	 * @return
	 * @throws NoSuchFileMovementBWSException
	 */
	public int countByUserIdAndStatusFlag(long forwardToUserId, int statusFlag)
			throws NoSuchFileMovementBWSException {
		
		return billInitiationPersistence.countByUserIdAndStatusFlag(statusFlag, forwardToUserId);
	}
	/**
	 * 
	 * @param userId
	 * @param statusFlag
	 * @param orderByComparator 
	 * @return
	 * @throws NoSuchFileMovementBWSException
	 */
	
	public List<BillInitiation> findByUserIdAndStatusFlag(long forwardToUserId, int statusFlag, OrderByComparator<BillInitiation> orderByComparator)
			throws NoSuchFileMovementBWSException {
	
		return billInitiationPersistence.findByUserIdAndStatusFlag(statusFlag, forwardToUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, orderByComparator);
	}
	public List<BillInitiation> findByUserIdAndStatusFlag(long forwardToUserId, int statusFlag)
			throws NoSuchFileMovementBWSException {
		
		return billInitiationPersistence.findByUserIdAndStatusFlag(statusFlag, forwardToUserId);
	}
	public List<BillInitiation> getAllBillinitiations(OrderByComparator<BillInitiation> orderByComparator)
			throws NoSuchFileMovementBWSException {
	
		return billInitiationPersistence.findAll(-1, -1, orderByComparator);
	}
	public List<BillInitiation> getAllSentBills(long sentUserId) {
		try {
			return billInitiationFinder.getAllSentBill(sentUserId);
		}catch (Exception e) {
		}
		return null;
	}
	/**
	 * 
	 * @param currentlyWith
	 * @return
	 * @throws NoSuchFileMovementBWSException
	 */
	public int countAllPendingBills(long currentlyWith)
			throws NoSuchFileMovementBWSException {
		
		return billInitiationFinder.countAllPendingBills(currentlyWith);
	}
	/**
	 * 
	 * @param assignedUser
	 * @return
	 * @throws NoSuchFileMovementBWSException
	 */
	public int countAllAcceptBills(long assignedUser)
			throws NoSuchFileMovementBWSException {
		
		return billInitiationFinder.countAllAcceptBills(assignedUser);
	}
	public List<BillInitiation> getAllAcceptBill(long assignedUser) {
		try {
			return billInitiationFinder.getAllAcceptBill(assignedUser);
		}catch (Exception e) {
		}
		return null;
	}
	/**
	 * 
	 */
	public BillInitiation getFinancePending(long assignedUser, String billInitiationUniqueId) {
		try {
			return billInitiationFinder.getFinancePending(assignedUser, billInitiationUniqueId);
		}catch (Exception e) {
		}
		return null;
	}
	public List<BillInitiation> getAllClosedBills(String currentDate) {
		try {
			return billInitiationFinder.getAllClosedBill(currentDate);
		}catch (Exception e) {
		}
		return null;
	}
	private static final Log _log = LogFactoryUtil.getLog(BillInitiationLocalServiceImpl.class);

}