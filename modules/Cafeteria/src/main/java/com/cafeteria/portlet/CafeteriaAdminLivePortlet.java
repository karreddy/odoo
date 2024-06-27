package com.cafeteria.portlet;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

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
		"javax.portlet.display-name=cafeteriaAdminLive",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe_admin.js",
		"javax.portlet.init-param.view-template=/cafe-admin-view/view.jsp",
		"javax.portlet.name=" + CafeteriaPortletKeys.CAFETERIA_ADMIN_LIVE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaAdminLivePortlet extends MVCPortlet {
	 
}