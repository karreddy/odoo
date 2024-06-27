package LoginModule.portlet;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManagerUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import LoginModule.constants.LoginModulePortletKeys;

@Component(immediate = true, property = {

		"javax.portlet.name=" + LoginModulePortletKeys.LOGINMODULE,
		"mvc.command.name=action/LoginWithPassword",

}, service = MVCActionCommand.class)
public class LoginWithPasswordAction extends BaseMVCActionCommand {
	Log _log=LogFactoryUtil.getLog(LoginWithPasswordAction.class);

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		HttpServletRequest request=PortalUtil.getHttpServletRequest(actionRequest);
		HttpServletResponse response=PortalUtil.getHttpServletResponse(actionResponse);
		
		String email = ParamUtil.getString(actionRequest, "userId");
		String password = ParamUtil.getString(actionRequest, "password");
		_log.info("email--->"+email);
		_log.info("password--->"+password);
		ThemeDisplay theme = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long companyId=PortalUtil.getCompanyId(actionRequest);
		String authType=CompanyConstants.AUTH_TYPE_EA;
		try {
			boolean rememberMe=false;
			User user = UserLocalServiceUtil.getUserByEmailAddress(companyId, email);
			_log.info("isLockout ::::::::::::::::: "+user.isLockout());
			//List<User> userList = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			
			//int authenticate = UserLocalServiceUtil.authenticateByEmailAddress(theme.getCompanyId(), email, password, null, null, null);
   
			AuthenticatedSessionManagerUtil.login(request, response, email, password, rememberMe, authType);
 
			   long userId=PortalUtil.getUserId(actionRequest);
			
		} catch (SystemException e) {
			_log.error(e);
		}
		
		
		 sendRedirect(actionRequest,actionResponse,"/");
		 	
	}

}

