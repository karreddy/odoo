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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.plng.common.schema.model.suggestion_review;
import com.plng.common.schema.service.suggestion_reviewLocalServiceUtil;
import com.plng.common.schema.service.base.suggestion_reviewLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.suggestion_review",
	service = AopService.class
)
public class suggestion_reviewLocalServiceImpl
	extends suggestion_reviewLocalServiceBaseImpl {
	
	public suggestion_review addSuggestionReview(long suggestionId,String dept,String comment) {
		
		long suggestionReviewId = CounterLocalServiceUtil.increment(suggestion_review.class.getName());
		suggestion_review createsuggestion_review = null;
		 try {
				createsuggestion_review = suggestion_reviewLocalServiceUtil.createsuggestion_review(suggestionReviewId);
				createsuggestion_review.setSid(suggestionId);
				createsuggestion_review.setDepartment(dept);
				createsuggestion_review.setComment(comment);
				createsuggestion_review.setSubmitted_at(new Date());
				createsuggestion_review = suggestion_reviewLocalServiceUtil.addsuggestion_review(createsuggestion_review);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return createsuggestion_review;
	}
	
	public suggestion_review  getDetailsBySuggestionId(long suggestionId) {
		suggestion_review fetchBysuggestionId = null;
		
			try {
				fetchBysuggestionId = suggestion_reviewPersistence.fetchBysuggestionId(suggestionId);
			} catch (Exception e) {
			}
			  return fetchBysuggestionId;
		}
	
}