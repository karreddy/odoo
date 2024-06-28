package mhcyber.incident.web.mvc.resource.command;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.mhcyber.common.schema.model.IncidentDetails;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import mhcyber.incident.web.constants.MhcyberIncidentWebPortletKeys;

@Component(immediate = true, property = { "javax.portlet.name=" + MhcyberIncidentWebPortletKeys.MHCYBERINCIDENTWEB,
		"mvc.command.name="
				+ MhcyberIncidentWebPortletKeys.SAVE_INCIDENT_RESOURCE_COMMAND }, service = MVCResourceCommand.class)

public class SaveIncidentMVCResourceCommand implements MVCResourceCommand {
	private static final Log _log = LogFactoryUtil.getLog(SaveIncidentMVCResourceCommand.class);
	

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
			IncidentDetails incidentDetails = null;
			if(Validator.isNotNull(incidentDetails)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				jsonObject.put("incidentSuccess", true);
				jsonObject.put("incidentNumber", incidentDetails.getIncidentNumber());
				resourceResponse.getWriter().write(jsonObject.toString());
			}
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return false;
	}


}
