package LoginModule.portlet;

import LoginModule.constants.LoginModulePortletKeys;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
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
 * @author akeel
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=MyOdooProject",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=LoginModule",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/redirection.jsp",
		"javax.portlet.name=" + LoginModulePortletKeys.LOGINMODULE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"com.liferay.portlet.requires-namespaced-parameters=false"
	},
	service = Portlet.class
)
public class LoginModulePortlet extends MVCPortlet {
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		String redirect="/view.jsp";
		ThemeDisplay themedisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		if(themedisplay.isSignedIn()) {
			
			boolean odooAdminRole=false;
			boolean odooUserRole=false;
			boolean odooHrAdminRole=false;
			try {
			odooAdminRole = RoleLocalServiceUtil.hasUserRole(themedisplay.getUserId(), themedisplay.getCompanyId(), "Administrator", false);
			odooUserRole = RoleLocalServiceUtil.hasUserRole(themedisplay.getUserId(), themedisplay.getCompanyId(), "odoo_user", false);
			odooHrAdminRole = RoleLocalServiceUtil.hasUserRole(themedisplay.getUserId(), themedisplay.getCompanyId(), "hr_admin", false);
			}catch(Exception e) {
				_log.info("error fetching Roles");
			}
			System.out.println("odooAdminRole"+odooAdminRole);
			System.out.println("odooUserRole"+odooUserRole);
			System.out.println("odooHrAdminRole"+odooHrAdminRole);
			
			
			if(odooAdminRole) {
				redirect = "/admin-dashboard";
			}else if(odooUserRole) {
				redirect = "/employee-dashboard";
			}
			else if(odooHrAdminRole) {
				redirect = "/admin-dashboard";
			}
			
			
			
			
			
			
		}
		renderRequest.setAttribute("isLoggedIn", themedisplay.isSignedIn());
		renderRequest.setAttribute("redirect", redirect);
		
		super.render(renderRequest, renderResponse);
	}
	private static final Log _log = LogFactoryUtil.getLog(LoginModulePortlet.class);
}