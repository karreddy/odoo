package com.cafeteria.portlet;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.constants.CafeteriaWebConstantKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author USER
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=PLNG",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CafeteriaReport",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.view-template=/mon_consumption_report.jsp",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe_report.js",
		"javax.portlet.name=" + CafeteriaPortletKeys.CAFETERIA_REPORT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaReportPortlet extends MVCPortlet {
	
	private static final Log _log = LogFactoryUtil.getLog(CafeteriaReportPortlet.class);
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		 ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	        long userId = themeDisplay.getUserId();
		try {
	        boolean isGrade5 = RoleLocalServiceUtil.hasUserRole(userId, themeDisplay.getCompanyId(), CafeteriaWebConstantKeys.ROLE_GRADE_5, false);
	        renderRequest.setAttribute(CafeteriaWebConstantKeys.IS_ROLE, isGrade5);
	        
	    } catch (PortalException e) {
	        _log.debug("Error checking user role: " + e.getMessage());
	    }
		
		super.render(renderRequest, renderResponse);
	}
	

	
}