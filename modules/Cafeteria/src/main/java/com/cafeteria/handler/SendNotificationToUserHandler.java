package com.cafeteria.handler;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;

@Component(immediate = true, service = UserNotificationHandler.class)
public class SendNotificationToUserHandler extends BaseUserNotificationHandler{
	private static final Log _log = LogFactoryUtil.getLog(SendNotificationToUserHandler.class);

	public static final String PORTLET_ID="com_cafeteria_CafeteriaPortlet";

	    public SendNotificationToUserHandler() {
	        setPortletId(PORTLET_ID);
	    }

	    
	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
	        throws Exception {
	    JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());
	    String notificationText = jsonObject.getString("notificationText");
	    String title = jsonObject.getString("title");
	    String senderName = jsonObject.getString("senderName");
	    String link = jsonObject.getString("link");
	    String body = StringUtil.replace(getBodyTemplate(),
	            new String[]{"[$TITLE$]", "[$SENDERNAME$]", "[$BODY_TEXT$]", "[$LINKHTML$]"},
	            new String[]{title, senderName, notificationText, link});
	    return body;
	}

	@Override
	protected String getBodyTemplate() throws Exception {
	    StringBundler sb = new StringBundler(5);
	  
	    sb.append("<div class=\"notification-element mt-1\">[$BODY_TEXT$] By [$SENDERNAME$]</br></div>");
	   
	    return sb.toString();
	}
	 @Override
	    protected String getLink(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext) {
	    	 JSONObject payloadJSONObject = null;
				try {
					payloadJSONObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());
				} catch (JSONException e) {
					e.printStackTrace();
					return "/dashboard";
				}
		        return payloadJSONObject.getString("link");
	    }
	
}
