package com.mhcyber.grievance.mvc.resource.commands;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.mhcyber.common.schema.model.GrievanceDetails;
import com.mhcyber.grievance.common.util.GrievanceCommonUtil;
import com.mhcyber.grievance.constants.MhcyberGrievanceWebPortletKeys;

import java.io.File;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = { "javax.portlet.name=" + MhcyberGrievanceWebPortletKeys.MHCYBERGRIEVANCEWEB,
		"mvc.command.name="
				+ MhcyberGrievanceWebPortletKeys.SAVE_GRIEVANCE_RESOURCE_COMMAND }, service = MVCResourceCommand.class)

public class SaveGrievanceMVCResourceCommand implements MVCResourceCommand {
	private static final Log _log = LogFactoryUtil.getLog(SaveGrievanceMVCResourceCommand.class);
	@Reference
	GrievanceCommonUtil grievanceCommonUtil;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			GrievanceDetails grievanceDetails = grievanceCommonUtil.saveGrievanceDetails(resourceRequest, themeDisplay);
			if(Validator.isNotNull(grievanceDetails)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				jsonObject.put("grievanceSuccess", true);
				jsonObject.put("grievanceUniqueId", grievanceDetails.getGrievanceUniqueId());
				resourceResponse.getWriter().write(jsonObject.toString());
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return false;
	}

}
