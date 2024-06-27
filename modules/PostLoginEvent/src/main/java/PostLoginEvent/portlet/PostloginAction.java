package PostLoginEvent.portlet;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, property = { "key=login.events.post" }, service = LifecycleAction.class)
public class PostloginAction implements	LifecycleAction{

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent) throws ActionException {
        HttpServletRequest request = lifecycleEvent.getRequest();
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(WebKeys.USER);
        String redirect = "/home";
        try {
			boolean odooAdminRole = RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "Administrator", false);
			boolean odooUserRole = RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "odoo_user", false);
			boolean odooHrAdminRole = RoleLocalServiceUtil.hasUserRole(user.getUserId(), user.getCompanyId(), "hr_admin", false);
			
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
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        try {
        	lifecycleEvent.getResponse().sendRedirect(redirect);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
