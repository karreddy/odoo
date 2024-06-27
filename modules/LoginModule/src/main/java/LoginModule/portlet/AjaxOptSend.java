package LoginModule.portlet;
 
 
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
 
import java.util.Random;
 
import javax.mail.internet.InternetAddress;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
 
import org.osgi.service.component.annotations.Component;

import LoginModule.constants.LoginModulePortletKeys;
 
@Component(immediate= true, 
property= {
		"javax.portlet.name=" + LoginModulePortletKeys.LOGINMODULE,
		"mvc.command.name=/feature/AjaxOptSend" 
		}, 
		service = MVCResourceCommand.class)
public class AjaxOptSend extends BaseMVCResourceCommand {
 
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
//			UserLocalServiceUtil.updatePassword(userId, password1, password2, passwordReset)
			user.getPassword();
//			System.out.println("user:::::::::::"+user);
//			System.out.println("user.getPassword1:::::::::::"+user.getPasswordUnencrypted());
//			System.out.println("user.getPassword2:::::::::::"+user.getPassword());
			res.put("emailValid","true");
		}catch(PortalException e) {
			res.put("emailValid","false");
		}
		try {
		String otp="";
			if(Validator.isNotNull(user)) {
				 otp = generateOTP();
				 res.put("otp",otp);	
				InternetAddress fromAddress = null;
				InternetAddress toAddress = null;
				if(!email.trim().equalsIgnoreCase("")) {
					fromAddress = new InternetAddress("venugopalbathala18@gmail.com");
					toAddress = new InternetAddress(email);
					MailMessage mailMessage = new MailMessage();
		    		mailMessage.setTo(toAddress);
		    		mailMessage.setFrom(fromAddress);
		    		mailMessage.setSubject("Otp For Login");
		    		mailMessage.setHTMLFormat(true);
		    		mailMessage.setBody("<p>Hi, Your otp for login is "+"<b>"+ otp +"</b>"+ "</p>");
		    		MailServiceUtil.sendEmail(mailMessage);
		    		System.out.println("Mail sent successfully");
					res.put("emailSent","true");
 
				}
			}
		}catch(Exception e) {
			res.put("emailSent","false");
 
		}
//		System.out.println("user:::::::::::"+user);


		resourceResponse.getWriter().print(res);
	}
    public static String generateOTP() {
        // Length of the OTP
        int otpLength = 6;
 
        // Possible characters in the OTP
        String characters = "0123456789";
 
        // StringBuilder to store the OTP
        StringBuilder otp = new StringBuilder(otpLength);
 
        // Create a random object
        Random random = new Random();
 
        // Generate OTP by appending random characters from the set of possible characters
        for (int i = 0; i < otpLength; i++) {
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }
 
        // Convert StringBuilder to String and return the OTP
        return otp.toString();
    }
}