package com.mhcyber.grievance.common.util;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.mhcyber.common.schema.model.GrievanceDetails;
import com.mhcyber.common.schema.service.GrievanceDetailsLocalServiceUtil;
import com.mhcyber.common.schema.service.persistence.GrievanceDetailsPersistence;
import com.mhcyber.grievance.constants.GrievanceConstant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.portlet.ResourceRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = GrievanceCommonUtil.class)

public class GrievanceCommonUtil {
	@Reference
	private CounterLocalService counterLocalService;
	@Reference
	private DLAppLocalService dlAppLocalService;
	@Reference
	private GrievanceDetailsPersistence grievanceDetailsPersistence;

	private static final Log _log = LogFactoryUtil.getLog(GrievanceCommonUtil.class);

	public long uploadFileEntity(ServiceContext serviceContext, UploadPortletRequest request,
			ThemeDisplay themeDisplay, String uploadFile) throws PortalException, SystemException {

		String filename = "";
		String description = "File description";
		FileEntry fileEntry = null;
		long fileEntryId = 0L;
		try {

			long repositoryId = DLFolderConstants.getDataRepositoryId(serviceContext.getScopeGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			long folderId = 0;
			try {
				Folder folder = dlAppLocalService.getFolder(repositoryId, 0L, "Grievance");
				folderId = folder.getFolderId();
			} catch (Exception e) {
				_log.error("No folder exist with Grievance");
			}

			File file = request.getFile(uploadFile);
			_log.debug("file>>>>>>>>>>>>> " + file);
			filename = request.getFileName(uploadFile);
			if (Validator.isNotNull(filename)) {
				filename = generateRandomDigits(8) + "_" + filename;
				_log.debug("filename::::::::::::::" + filename);
				String mimeType = MimeTypesUtil.getContentType(file);

				fileEntry = dlAppLocalService.addFileEntry(serviceContext.getUserId(), repositoryId, folderId, filename,
						mimeType, filename, description, "", file, serviceContext);
				_log.debug("fileEntry>>>>>>>>>>>>> " + fileEntry);
				if (Validator.isNotNull(fileEntry)) {
					fileEntryId = fileEntry.getFileEntryId();
				}
			}

		} catch (PortalException e) {
			_log.error("An exception occured uploading file: " + e.getMessage(), e);
		} catch (SystemException e) {
			_log.error("An exception occured uploading file: " + e.getMessage(), e);
		}
		return fileEntryId;
	}
	public static String generateRandomDigits(int length) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(random.nextInt(10)); // Generate random digits (0-9)
		}
		return sb.toString();
	}
	public GrievanceDetails setGrievanceDetails(ResourceRequest resourceRequest, ThemeDisplay themeDisplay, GrievanceDetails grievanceDetails) {
		try {
			grievanceDetails.setGrievanceUniqueId(getUniqueGrievId(grievanceDetails.getPrimaryKey()));
		String grievanceType = ParamUtil.getString(resourceRequest, "grievanceType");
		String description = ParamUtil.getString(resourceRequest, "description");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(MVCPortlet.class.getName(),
				uploadRequest);
		String photoFile = "photoFile";
		String audioFile = "audioFile";
		String videoFile = "videoFile";
			long photoFileEntityId =uploadFileEntity(serviceContext, uploadRequest, themeDisplay,photoFile);
			long audioFileEntityId =uploadFileEntity(serviceContext, uploadRequest, themeDisplay,audioFile);
			long videoFileEntityId =uploadFileEntity(serviceContext, uploadRequest, themeDisplay,videoFile);
			_log.info("grievanceType: " + grievanceType + "description:" + description+"photoFile:"+photoFileEntityId
					+"audioFile:"+audioFileEntityId+"videoFile:"+videoFileEntityId);
			grievanceDetails.setGrievanceType(grievanceType);
			grievanceDetails.setDescription(description);
			grievanceDetails.setPhotoEntryId(photoFileEntityId);
			grievanceDetails.setAudioEntryId(audioFileEntityId);
			grievanceDetails.setVideoEntryId(videoFileEntityId);
			grievanceDetails.setStatus(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grievanceDetails;
	}
	
	public GrievanceDetails saveGrievanceDetails(ResourceRequest resourceRequest, ThemeDisplay themeDisplay) {
		try {
		GrievanceDetails grievanceDetails = grievanceDetailsPersistence.create(counterLocalService.increment(GrievanceDetails.class.getName()));
		grievanceDetails = setGrievanceDetails(resourceRequest, themeDisplay, grievanceDetails);
		if(Validator.isNotNull(grievanceDetails)) {
			return GrievanceDetailsLocalServiceUtil.addGrievanceDetails(grievanceDetails);
		}
		}catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return null;
	}
	private String getUniqueGrievId(long grievPK) {
		SimpleDateFormat smt = new SimpleDateFormat("ddMMyy");
		String formattedDate = smt.format(new Date());
		String uniqueId = "MCG" + formattedDate + grievPK;
		_log.info("unique id : "+uniqueId);
		return uniqueId;
	}

}
