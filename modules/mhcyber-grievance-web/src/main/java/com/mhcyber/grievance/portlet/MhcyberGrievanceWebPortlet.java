package com.mhcyber.grievance.portlet;

import com.mhcyber.grievance.constants.MhcyberGrievanceWebPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author DELL
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=MhcyberGrievanceWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.name=" + MhcyberGrievanceWebPortletKeys.MHCYBERGRIEVANCEWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class MhcyberGrievanceWebPortlet extends MVCPortlet {
}