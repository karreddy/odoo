package LoginModule.portlet;
 
 
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.TicketConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Date;

import javax.mail.internet.InternetAddress;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import LoginModule.constants.LoginModulePortletKeys;
 
@Component(immediate= true, 
property= {
		"javax.portlet.name=" + LoginModulePortletKeys.LOGINMODULE,
		"mvc.command.name=/feature/forgotpwd" 
		}, 
		service = MVCResourceCommand.class)
public class Forgotpwd extends BaseMVCResourceCommand {
 
	final Log _log = LogFactoryUtil.getLog(Forgotpwd.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		String email=ParamUtil.getString(resourceRequest, "email", "");
//		System.out.println("email for login "+email);
		JSONObject res= JSONFactoryUtil.createJSONObject();
		User user=null;
		Long company_id=PortalUtil.getDefaultCompanyId();

		try {
			user=UserLocalServiceUtil.getUserByEmailAddress(company_id, email);
			
			String forgotPasswordResetLink = getForgotPasswordResetLink(user.getCompanyId(), user.getUserId(),resourceRequest.getServerName(),resourceRequest.getServerPort());
//			System.out.println("forgotPasswordResetLink >>>>>>>>>>>>>>>>>> "+forgotPasswordResetLink);
			
			
			String changePasswordLink = forgotPasswordResetLink;
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;
			fromAddress = new InternetAddress("venugopalbathala18@gmail.com");
			toAddress = new InternetAddress(email);
			MailMessage mailMessage = new MailMessage();
    		mailMessage.setTo(toAddress);
    		mailMessage.setFrom(fromAddress);
    		mailMessage.setSubject("Password reset request for "+ email);
    		mailMessage.setHTMLFormat(true);
    		mailMessage.setBody("<b>To reset/change your password, please click below link: </b><br><br><a href='"+changePasswordLink+"'>Change Password</a>");
    		MailServiceUtil.sendEmail(mailMessage);
    		System.out.println("Mail sent successfully");
    		res.put("emailValid","true");
		}catch(PortalException e) {
			res.put("emailValid","false");
		}
		resourceResponse.getWriter().print(res);
	}
	
	public String getForgotPasswordResetLink(long companyId, long userId, String ServerName, long ServerPort) {
		System.out.println(ServerName);
		System.out.println(ServerPort);
		String passwordResetURL = StringPool.BLANK;
		User user = null;
		long plid = 0;
		try {
			user = _userLocalService.getUser(userId);
		} catch (PortalException e) {
			_log.error("Exception in getting :::" + e.getMessage());
		}
		if (Validator.isNotNull(user)) {
			PasswordPolicy passwordPolicy = null;
			Date expirationDate = null;
			try {
				passwordPolicy = user.getPasswordPolicy();
			} catch (PortalException e1) {
				_log.error("Exception in getting :::" + e1.getMessage());
			}
			if (Validator.isNotNull(passwordPolicy)) {
				if ((passwordPolicy != null) && (passwordPolicy.getResetTicketMaxAge() > 0)) {
//				System.out.println("passwordPolicy.getResetTicketMaxAge() :::::"+passwordPolicy.getResetTicketMaxAge());
					expirationDate = new Date(
							System.currentTimeMillis() + (passwordPolicy.getResetTicketMaxAge() * 1000));
				}
			}
			Group group = null;
			Layout layout = null;
			group = _groupLocalService.fetchFriendlyURLGroup(companyId, "/guest");
			if (Validator.isNotNull(group)) {
				layout = _layoutLocalService.fetchLayoutByFriendlyURL(group.getGroupId(), false, "/login");
			}

			if (Validator.isNotNull(layout)) {
				plid = layout.getPlid();
			}
			Ticket ticket = _ticketLocalService.addDistinctTicket(user.getCompanyId(), User.class.getName(),
					user.getUserId(), TicketConstants.TYPE_PASSWORD, null, expirationDate, null);

			try {
				Company company = null;
				company = _companyLocalService.getCompany(companyId);
				int portalServerPort = PortalUtil.getPortalServerPort(true);
				System.out.println("portalServerPort"+portalServerPort);
				
				String portalURL = PortalUtil.getPortalURL(ServerName+":"+ServerPort, portalServerPort, false);
				StringBundler sb = new StringBundler(6);
				sb.append(portalURL);
				sb.append("/c");
				sb.append("/portal/update_password?p_l_id=");
				sb.append(plid);
				sb.append("&ticketKey=");
				sb.append(ticket.getKey());
				passwordResetURL = sb.toString();
			} catch (Exception e) {
				_log.error("Exception in getting :::" + e.getMessage());
			}
			if (user.isLockout()) {
				try {
					_userLocalService.updateLockoutByEmailAddress(user.getCompanyId(), user.getEmailAddress(), false);
				} catch (PortalException e) {
					_log.error(e.getMessage());
				}
			}
		}
//		_log.info("passwordResetURL>>>>" + passwordResetURL);
		return passwordResetURL;
	}
	
	@Reference private UserLocalService _userLocalService;
	@Reference private GroupLocalService _groupLocalService;
	@Reference private LayoutLocalService _layoutLocalService;
	@Reference private CompanyLocalService _companyLocalService;
	@Reference private TicketLocalService _ticketLocalService;
	
	
}

