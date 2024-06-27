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
import com.plng.common.schema.model.Upgrade;
import com.plng.common.schema.service.base.UpgradeLocalServiceBaseImpl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.Upgrade",
	service = AopService.class
)
public class UpgradeLocalServiceImpl extends UpgradeLocalServiceBaseImpl {
	
private static final Log _log=LogFactoryUtil.getLog(UpgradeLocalServiceImpl.class);
	
	public Upgrade saveUpgradeDetails(String typeOfrequest,int employeeNo,String employeeName,String basicQualification,String nameOfCourse,String nameOfUniversity 
			,String isUniversity,String durationOfCourse,String mode,String tutionFees,long headOfDepartment,String marksObtained,String additionalQualification,
			InputStream inputStream, long groupId, long userId, String fileName,int status,String emaployeeName,String prof_type,
			InputStream marksheet,InputStream degree,String marksheetFileName,String degreeFileName,long assignedTo,long assignedBy,long uniqueId,String dateOfIssue) {		
		_log.info("Inside saveUpgradeDetails::::::::::");
		Upgrade upgrade=null;
		if(Validator.isNotNull(uniqueId)) {
			upgrade = upgradePersistence.fetchByPrimaryKey(uniqueId);
		}else {
			upgrade = upgradePersistence.create(counterLocalService.increment(Upgrade.class.getName()));
		}
		upgrade.setEmp_no(employeeNo);
		upgrade.setBase_q(basicQualification);
		upgrade.setCourse_name(nameOfCourse);
		upgrade.setUniv_addr(nameOfUniversity);
		upgrade.setAicte(isUniversity);
		upgrade.setCourse_duration(durationOfCourse);
		upgrade.setTuition(tutionFees);
		upgrade.setForm_type(typeOfrequest);
		upgrade.setHod(headOfDepartment);
		upgrade.setMarks(marksObtained);
		upgrade.setDegree_date(getFormattedDate(dateOfIssue));
		upgrade.setMode(mode);
		upgrade.setSubmitted(new Date());
		upgrade.setUserId(userId);
		upgrade.setStatus(status);
		upgrade.setCreatedBy(userId);
		upgrade.setEmp_name(emaployeeName);
		upgrade.setProf_type(prof_type);
		upgrade.setSubmit(new Date());
		upgrade.setAssignedTo(assignedTo);
		upgrade.setAssignedBy(assignedBy);
		
		if(Validator.isNotNull(inputStream) && Validator.isNotNull(fileName)) {
			String fileEntryId = getFileEntryId(inputStream,System.currentTimeMillis()+"-"+fileName, userId, groupId);
			//upgrade.setFile(fileEntryId);
			upgrade.setFileId(fileEntryId);
			
		}
		
		if(Validator.isNotNull(marksheet) && Validator.isNotNull(marksheetFileName)) {
			String fileEntryId = getFileEntryId(marksheet,System.currentTimeMillis()+"-"+marksheetFileName, userId, groupId);
			upgrade.setMarksheet(fileEntryId);
		}
		if(Validator.isNotNull(degree) && Validator.isNotNull(degreeFileName)) {
			String fileEntryId = getFileEntryId(degree,System.currentTimeMillis()+"-"+degreeFileName, userId, groupId);
			upgrade.setDegree(fileEntryId);
		}
		
		return upgradePersistence.update(upgrade);
		
	}
	
	public String getFileEntryId(InputStream inputStream, String fileName, long userId, long groupId) {
		String mimeType = MimeTypesUtil.getContentType(inputStream, fileName);
		_log.info("inputStream:::::::::::::;"+inputStream);
		int size;
		String entryId="";
		try {
			size = inputStream.available();
			_log.info("Size:::::::::::::;"+size);
			User user1 = UserLocalServiceUtil.getUser(userId);
			PermissionChecker checker = PermissionCheckerFactoryUtil.create(user1);
			PermissionThreadLocal.setPermissionChecker(checker);
			FileEntry fileEntry = dlAppService.addFileEntry(StringPool.BLANK, groupId, getFolderId(groupId, userId), fileName, 
					mimeType, fileName, fileName, fileName+" Description", "changeLog", 
					inputStream, size, null, null, new ServiceContext());
			addGuestViewPermission(DLFileEntry.class.getName());
			addHrViewPermission(DLFileEntry.class.getName());
			addSeniorHrViewPermission(DLFileEntry.class.getName());
			addSrOfficerViewPermission(DLFileEntry.class.getName());
			addPllAdminViewPermission(DLFileEntry.class.getName());
			addHodViewPermission(DLFileEntry.class.getName());
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
		DLFolder dlFolder = dlFolderLocalService.fetchFolder(groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Career Progression");
		long folderId = 0;
		if(Validator.isNull(dlFolder)) {
			try {
				dlFolder = dlFolderLocalService.addFolder(null, userId, groupId, groupId, false, 
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Career Progression", "Documents for Carrer Progression", false, new ServiceContext());
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
	
	public static void addHrViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId,"HR");
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
	
	public static void addSeniorHrViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId,"Senior HR");
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
	
	public static void addSrOfficerViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId,"Sr officer");
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
	
	public List<Upgrade> getUpgradeDetailsByUserId(long userId){
		return upgradePersistence.findByUserId(userId);
	}
	
	public List<Upgrade> getUpgradeDetailsByUserIdAndStatus(long userId,int status){
		return upgradePersistence.findByStatus(userId, status);
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
	
	public Upgrade updateUpgradestatus(long id, long assignedTo,long assignedBy,int status,String comment) {
		Upgrade upgrade = null;
		try {
			upgrade = upgradePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		if(Validator.isNotNull(upgrade)) {
			upgrade.setStatus(status);
			upgrade.setAssignedTo(assignedTo);
			upgrade.setAssignedBy(assignedBy);
			upgrade.setComment(comment);
			upgrade = upgradePersistence.update(upgrade);
		}
		return upgrade;
	}
	public Upgrade updateUpgradestatusSrHr(long id, long assignedTo,long assignedBy,int status,long hrHod) {
		Upgrade upgrade = null;
		try {
			upgrade = upgradePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		if(Validator.isNotNull(upgrade)) {
			upgrade.setStatus(status);
			upgrade.setAssignedTo(assignedTo);
			upgrade.setAssignedBy(assignedBy);
			upgrade.setHrhod(hrHod);
			upgrade = upgradePersistence.update(upgrade);
		}
		return upgrade;
	}
	public Upgrade updateUpgradestatusHr(long id, long assignedTo,long assignedBy,int status,long hrApprovedBy) {
		Upgrade upgrade = null;
		try {
			upgrade = upgradePersistence.fetchByPrimaryKey(id);
		} catch (Exception e) {
			_log.error(e.getMessage());
		}
		if(Validator.isNotNull(upgrade)) {
			upgrade.setStatus(status);
			upgrade.setAssignedTo(assignedTo);
			upgrade.setAssignedBy(assignedBy);
			upgrade.setHr_approved_by(hrApprovedBy);
			upgrade = upgradePersistence.update(upgrade);
		}
		return upgrade;
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
	public static void addHodViewPermission(String resourceClassName) {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			Role roleUser = RoleLocalServiceUtil.getRole(companyId,"HOD");
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
	public List<Upgrade> getUpgradeDetailsByassignedTo(long assingnedTo){
		return upgradePersistence.findByAssignedTo(assingnedTo);
	}
	@Reference
	private DLAppService dlAppService;
	
	@Reference
	private DLFolderLocalService dlFolderLocalService;
}