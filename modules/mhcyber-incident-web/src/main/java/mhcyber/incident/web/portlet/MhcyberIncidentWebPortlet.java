package mhcyber.incident.web.portlet;

import mhcyber.incident.web.constants.MhcyberIncidentWebPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author USER
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=Incident Management",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=MhcyberIncidentWeb",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/",
		"javax.portlet.name=" + MhcyberIncidentWebPortletKeys.MHCYBERINCIDENTWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class MhcyberIncidentWebPortlet extends MVCPortlet {
}