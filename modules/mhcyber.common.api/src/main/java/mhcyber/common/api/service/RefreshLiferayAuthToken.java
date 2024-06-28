package mhcyber.common.api.service;

import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.ws.rs.core.MediaType;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.util.tracker.ServiceTracker;

import mhcyber.http.connector.api.api.MhcyberHttpConnectorApi;
import mhcyber.liferay.configurations.web.api.LiferayConfiguration;


@Component(immediate = true, service = RefreshLiferayAuthToken.class)
public class RefreshLiferayAuthToken extends TimerTask{
	public static String newToken;
	
	@Override
	public void run() {
		if (isStart()) {
			RefreshLiferayAuthToken.newToken = getLiferayAuthorizationToken();
			logger.debug("token at scheduled time ?? "+ scheduledExecutionTime() + " and token is ?? " + newToken );
			
		}
	}
	
	public static String getToken() {
		logger.debug("new Token ?? " + newToken);
		return newToken;
	}
	
	@Override
	public long scheduledExecutionTime() {
		return super.scheduledExecutionTime();
	}
	
	private boolean isStart() {
		boolean start = false;
		LiferayConfiguration liferayConfiguration = null;
		try {
			liferayConfiguration = ConfigurationProviderUtil.getSystemConfiguration(LiferayConfiguration.class);
		} catch (ConfigurationException e) {
			logger.error(e.getMessage());
		}
		if (Validator.isNotNull(liferayConfiguration)) {
			start = liferayConfiguration.stopScheduleExecution();
		}
		return start;
	}
	
	public String getLiferayAuthorizationToken() {
		
		Bundle bundle = FrameworkUtil.getBundle(RefreshLiferayAuthToken.class);
		BundleContext bundleContext = bundle.getBundleContext();
		ServiceTracker<MhcyberHttpConnectorApi, MhcyberHttpConnectorApi> serviceTracker =  new ServiceTracker(bundleContext, MhcyberHttpConnectorApi.class, null);
		serviceTracker.open();
		MhcyberHttpConnectorApi HttpConnector = serviceTracker.getService();
		
		logger.debug("getToken >>>>> method started>>>>>>");
		JSONObject jsonResponse = null;
		try {
			LiferayConfiguration liferayConfiguration = ConfigurationProviderUtil.getSystemConfiguration(LiferayConfiguration.class);
			
			String tokenEndpoint = liferayConfiguration.getTokenEndPoint();
			String clientId = liferayConfiguration.getClientId();
			String clientSecret = liferayConfiguration.getClientSecret();
			logger.debug("tokenEndpoint >>>>  " + tokenEndpoint + "clientId >>>>  " + clientId + "clientSecret >>>>  "
					+ clientSecret);

			String payload = null;
			payload = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s", clientId,
					clientSecret);

			Map<String, String> headers = new HashMap<>();
			headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
			String tokenResponse = HttpConnector.tokenExecutePost(tokenEndpoint, payload, headers);
			logger.debug("tokenResponse>>>>>>>> " + tokenResponse);

			jsonResponse = JSONFactoryUtil.createJSONObject(tokenResponse);
		} catch (JSONException | ConfigurationException e) {
			logger.error(e.getMessage());
		}
		logger.debug("jsonResponse>>>>>>>>>> " + jsonResponse);
		String accessToken = jsonResponse.getString("access_token");
		logger.debug("accessToken>>>>>>>>> " + accessToken);
		logger.debug("getToken >>>>> method ended>>>>>>");
		return accessToken;
	}

	private static final Log logger = LogFactoryUtil.getLog(RefreshLiferayAuthToken.class);
}
