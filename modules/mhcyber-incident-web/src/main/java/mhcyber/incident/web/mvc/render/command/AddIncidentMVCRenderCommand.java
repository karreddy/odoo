package mhcyber.incident.web.mvc.render.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import mhcyber.incident.web.constants.MhcyberIncidentWebConstants;
import mhcyber.incident.web.constants.MhcyberIncidentWebPortletKeys;

@Component(immediate = true, property = { "javax.portlet.name=" + MhcyberIncidentWebPortletKeys.MHCYBERINCIDENTWEB,
		"mvc.command.name=/" }, service = MVCRenderCommand.class)

public class AddIncidentMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		return MhcyberIncidentWebConstants.ADD_INCIDENT_JSP;
	}

}
