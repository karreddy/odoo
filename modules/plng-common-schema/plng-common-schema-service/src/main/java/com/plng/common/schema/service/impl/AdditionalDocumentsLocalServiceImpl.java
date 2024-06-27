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
import com.plng.common.schema.model.AdditionalDocuments;
import com.plng.common.schema.service.base.AdditionalDocumentsLocalServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(property = "model.class.name=com.plng.common.schema.model.AdditionalDocuments", service = AopService.class)
public class AdditionalDocumentsLocalServiceImpl extends AdditionalDocumentsLocalServiceBaseImpl {

	public AdditionalDocuments getAdditionalDocumentsByApplicationNo(long applicationNumber) {
		try {
			return additionalDocumentsPersistence.findByapplicationNo(applicationNumber);
		} catch (Exception e) {
			return null;
		}

	}

	public AdditionalDocuments getAdditionDocumentsByJobCode(String jobCode) {
		try {
			return additionalDocumentsPersistence.findByjobCode(jobCode);
		} catch (Exception e) {
			return null;
		}

	}
	public AdditionalDocuments getAdditionDocumentsByJobCodeAndAppNoAndUserId(String jobCode, long applicationNo, long userId) {
		try {
			return additionalDocumentsPersistence.findByjobCodeAndUserIdAndAppNo(jobCode, userId, applicationNo);
		} catch (Exception e) {
			return null;
		}

	}
	public AdditionalDocuments getAdditionDocumentsByJobCodeAndAppNo(String jobCode, long applicationNo) {
		try {
			return additionalDocumentsPersistence.findByjobCodeAndAppNo(jobCode, applicationNo);
		} catch (Exception e) {
			return null;
		}
		
	}
}