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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.model.Suggestion;
import com.plng.common.schema.service.SuggestionLocalServiceUtil;
import com.plng.common.schema.service.base.SuggestionLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Suggestion",
	service = AopService.class
)
public class SuggestionLocalServiceImpl extends SuggestionLocalServiceBaseImpl {
Log _log=LogFactoryUtil.getLog(SuggestionLocalServiceImpl.class);
		
    public Suggestion addSuggestion(long groupId, long companyid, long userid, String ename,long eid,String department,String location,String suggestion,String generatedno,long filenetryid) {
    	long suggestionId = counterLocalService.increment(Suggestion.class.getName());
    	Suggestion createsuggestion = null;
		try {
			createsuggestion = SuggestionLocalServiceUtil.createSuggestion(suggestionId);
			createsuggestion.setGroupId(companyid);
			createsuggestion.setCompanyId(companyid);
			createsuggestion.setUserId(userid);
			createsuggestion.setUserName(ename);
			createsuggestion.setCreateDate(new Date());
			createsuggestion.setModifiedDate(new Date());
			createsuggestion.setEmployeeName(ename);
			createsuggestion.setEmployeeId(eid);
			createsuggestion.setDepartment(department);
			createsuggestion.setLocation(location);
			createsuggestion.setSuggestion(suggestion);
			createsuggestion.setGeneratedNo(generatedno);
			createsuggestion.setFileEntryId(filenetryid);
			createsuggestion.setStartDate(new Date());
			createsuggestion.setStatus(1);
			createsuggestion.setStatusByUserId(userid);
			createsuggestion.setStatusByUserName(ename);
			
			createsuggestion = SuggestionLocalServiceUtil.addSuggestion(createsuggestion);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		return createsuggestion;
    }

}