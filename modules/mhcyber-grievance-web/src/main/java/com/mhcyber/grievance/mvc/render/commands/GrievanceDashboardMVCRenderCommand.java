package com.mhcyber.grievance.mvc.render.commands;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.mhcyber.grievance.constants.MhcyberGrievanceWebPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
@Component(immediate = true, property = { "javax.portlet.name=" + MhcyberGrievanceWebPortletKeys.MHCYBERGRIEVANCEWEB,
		"mvc.command.name=/"}, service = MVCRenderCommand.class)

public class GrievanceDashboardMVCRenderCommand implements MVCRenderCommand{

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		return MhcyberGrievanceWebPortletKeys.ADD_GRIEVANCE_JSP;
	}

}
