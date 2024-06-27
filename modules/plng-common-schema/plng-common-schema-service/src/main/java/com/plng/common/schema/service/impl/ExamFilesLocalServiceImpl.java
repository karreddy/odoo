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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.plng.common.schema.model.ExamFiles;
import com.plng.common.schema.service.base.ExamFilesLocalServiceBaseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.ExamFiles",
	service = AopService.class
)
public class ExamFilesLocalServiceImpl extends ExamFilesLocalServiceBaseImpl {
	
private static final Log _log=LogFactoryUtil.getLog(ExamFilesLocalServiceImpl.class);
	
	public ExamFiles saveExamAwardFiles(JSONArray jsonArray,long userId,long groupId,long companyId,long examId,String[] selectionFileName,InputStream[] selectionIs,String[] photoName,InputStream[] photoIs) {
		ExamFiles exam=null;
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			if(Validator.isNotNull(jsonObject.getString("childName"))) {
			    exam = examFilesPersistence.create(counterLocalService.increment(ExamFiles.class.getName()));
			    exam.setChildName(jsonObject.getString("childName"));
			    exam.setSeletedServices(jsonObject.getString("service"));
			    exam.setSelectionFor(jsonObject.getString("selectionFor"));
				exam.setDateOfselection(getFormattedDate(jsonObject.getString("dateOfSelection")));
				exam.setUserId(userId);
				exam.setGroupId(groupId);
				exam.setCompanyId(companyId);
				exam.setCreateBy(userId);
				exam.setCreateDate(new Date());
				exam.setModifiedDate(new Date());
				exam.setModifiedBy(userId);
				exam.setExamId(examId);
				exam.setStatus("Initiated");
				if(Validator.isNotNull(selectionFileName) && Validator.isNotNull(selectionIs)) {
					String fileEntryId = getFileEntryId(selectionIs[i], System.currentTimeMillis()+"-"+selectionFileName[i], userId, groupId);
				exam.setSelectionLetter(Long.parseLong(fileEntryId));
				}
				if(Validator.isNotNull(photoName) && Validator.isNotNull(photoIs)) {
					String fileEntryId = getFileEntryId(photoIs[i], System.currentTimeMillis()+"-"+photoName[i], userId, groupId);
				exam.setPhoto(Long.parseLong(fileEntryId));
				}
				
				 exam=examFilesPersistence.update(exam);
			}
			
		}
		return exam;
		
	}
	
	private Date getFormattedDate(String date) {
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");
		if(date != null && !date.isEmpty()) {
			try {
				return smt.parse(date);
			} catch (ParseException e) {
				_log.error("Exception on parsing date : "+e.getMessage());
			}
		}
		return null;
	}
	
	public String getFileEntryId(InputStream inputStream, String fileName, long userId, long groupId) {
		String mimeType = MimeTypesUtil.getContentType(inputStream, fileName);
		_log.info("Mime Type of file:::::::::"+mimeType);
		_log.info("File name:::::::::::::"+fileName);
		_log.info("Input stream::::::::::::"+inputStream);
		int size=0;
		String entryId="";
		try {
//			size = IOUtils.toByteArray(inputStream).length;
//			_log.info("Size of the files:::::::::::::;"+IOUtils.toByteArray(inputStream).length);
			User user1 = UserLocalServiceUtil.getUser(userId);
			PermissionChecker checker = PermissionCheckerFactoryUtil.create(user1);
			PermissionThreadLocal.setPermissionChecker(checker);
			
			FileEntry fileEntry = dlAppService.addFileEntry(StringPool.BLANK, groupId, getFolderId(groupId, userId), fileName, 
					mimeType, fileName, fileName, fileName+" Description", "changeLog", 
					inputStream,inputStream.available(), null, null, new ServiceContext());
			addGuestViewPermission(DLFileEntry.class.getName());
			addAdminViewPermission(DLFileEntry.class.getName());
			addPllAdminViewPermission(DLFileEntry.class.getName());
			long fileEntryId = fileEntry.getFileEntryId();
			entryId = Long.toString(fileEntryId);
		} catch (IOException e) {
			_log.error(e.getMessage());
		} catch (PortalException e) {
			_log.error(e.getMessage());
		}
		return entryId;	
	}
	
	private long getFolderId(long groupId, long userId) {
		DLFolder dlFolder = dlFolderLocalService.fetchFolder(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Meritorious Award");
		long folderId = 0;
		if(Validator.isNull(dlFolder)) {
			try {
				dlFolder = dlFolderLocalService.addFolder(null, userId, groupId, groupId, false, 
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Meritorious Award", "Meritorious Award", false, new ServiceContext());
				folderId = dlFolder.getFolderId();
			} catch (PortalException e) {
				_log.error("Exception on adding folder : "+e.getMessage());
			}
		}else {
			folderId = dlFolder.getFolderId();
		}
		addPermission(new String[] {ActionKeys.ADD_DOCUMENT, ActionKeys.VIEW});
		return folderId;
	}
	public static void addGuestViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId, RoleConstants.GUEST);
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId,
					resourceClassName,
					ResourceConstants.SCOPE_COMPANY,
					""+companyId,
					roleUser.getRoleId(),
					new String[] {ActionKeys.VIEW});
		} catch (PortalException e) {
			_log.error("Exception on Setting permission : "+e.getMessage());
		}
	}
	
	public static void addPermission(String[] actions) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId, RoleConstants.USER);
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId,
					DLFolder.class.getName(),
					ResourceConstants.SCOPE_COMPANY,
					""+companyId,
					roleUser.getRoleId(),
					actions);
		} catch (PortalException e) {
			_log.error("Exception on Setting permission : "+e.getMessage());
		}
	}
	
	public List<ExamFiles> getExamFilesById(long examId){
		return examFilesPersistence.findByExamId(examId);
	}
	
	public ExamFiles updateExamFilesByExamId(long uniqueId,long amount,String status) {
		ExamFiles examFile=null;
		try {
			examFile = examFilesPersistence.fetchByPrimaryKey(uniqueId);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		if(Validator.isNotNull(examFile)) {
			examFile.setStatus(status);
			examFile.setAmount(amount);
			examFile = examFilesPersistence.update(examFile);
		}
		return examFile;
	}
	public static void addAdminViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId,RoleConstants.ADMINISTRATOR);
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId,
					resourceClassName,
					ResourceConstants.SCOPE_COMPANY,
					""+companyId,
					roleUser.getRoleId(),
					new String[] {ActionKeys.VIEW,ActionKeys.DOWNLOAD});
		} catch (PortalException e) {
			_log.error("Exception on Setting permission : "+e.getMessage());
		}
	}
	public static void addPllAdminViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId,"Pll Admin");
			ResourcePermissionLocalServiceUtil.setResourcePermissions(
					companyId,
					resourceClassName,
					ResourceConstants.SCOPE_COMPANY,
					""+companyId,
					roleUser.getRoleId(),
					new String[] {ActionKeys.VIEW,ActionKeys.DOWNLOAD});
		} catch (PortalException e) {
			_log.error("Exception on Setting permission : "+e.getMessage());
		}
	}
	@Reference
	private DLAppService dlAppService;
	
	@Reference
	private DLFolderLocalService dlFolderLocalService;
}