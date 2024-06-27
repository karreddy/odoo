package LoginModule.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auto.login.AutoLogin;
import com.liferay.portal.kernel.security.auto.login.AutoLoginException;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = AutoLogin.class)
public class VMHRAutoLogin implements AutoLogin{

	@Override
	public String[] login(HttpServletRequest request, HttpServletResponse httpServletResponse)
			throws AutoLoginException {
		_log.info("AutoLogin");
		String emailAddress = request.getParameter("emailAddress");
		String action = request.getParameter("action");	
		_log.info("emailAddress :::::"+emailAddress);
		if(Validator.isNotNull(emailAddress)) {

			if(action.equalsIgnoreCase("ehfbefiehfhefeifefewfefef")) {
				long companyId = PortalUtil.getCompanyId(request);
				User user = null;
					try {
						user = UserLocalServiceUtil.getUserByEmailAddress(companyId, emailAddress);
					} catch (Exception e) {
						_log.error(e.getMessage());
					}
				
				_log.info("User:::::" + user);
				if (Validator.isNotNull(user)) {
	
					return new String[] { String.valueOf(user.getUserId()), user.getPassword(),
							String.valueOf(user.isPasswordEncrypted()) };
					
				} else {
					return null;
				}
			}
	}
		return null;

	}

	private static final Log _log = LogFactoryUtil.getLog(VMHRAutoLogin.class);
}
