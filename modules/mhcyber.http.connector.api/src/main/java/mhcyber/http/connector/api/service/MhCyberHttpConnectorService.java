package mhcyber.http.connector.api.service;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import mhcyber.http.connector.api.api.MhcyberHttpConnectorApi;
import mhcyber.liferay.configurations.web.api.LiferayConfiguration;

@Component(immediate = true, service = MhcyberHttpConnectorApi.class)
public class MhCyberHttpConnectorService implements MhcyberHttpConnectorApi{
	
	@Reference
	private Http http;
	
	@Reference(unbind = "-")
	private ConfigurationProvider provider;
	
	private static final Log _log = LogFactoryUtil.getLog(MhCyberHttpConnectorService.class);
	
	
	@Override
	public String getAuthToken(String url, String payload) {
		_log.debug("getDataFlowAuthToken invoking ::: ");
		try {
			Http.Options options = new Http.Options();
			options.setLocation(url);
			options.setBody(payload, StringPool.BLANK, StringPool.BLANK);
			options.setPost(true);
			//Http.Response response = options.getResponse();
			_log.debug("getDataFlowAuthToken invocation successfuly ::: ");
			return http.URLtoString(options);
		} catch (IOException e) {
			_log.error("An Error occurred executing the get DataFlow Auth Token ::: " + e);
		}
		_log.debug("getDataFlowAuthToken invocation Failed ::: ");
		return null;
	}

	@Override
	public String executeGet(String url, String payload, Map<String, String> headers) {
		_log.debug("executeGet invoking ::: ");
		try {
			_log.debug("inside try block >>>=========");
			Http.Options options = new Http.Options();
			options.setLocation(url);
			options.setHeaders(headers);
			options.setBody(payload, StringPool.BLANK, StringPool.BLANK);
			options.setPost(false);
			//Http.Response response = options.getResponse();
			return http.URLtoString(options);
		} catch (Exception e) {
			_log.error("An Error occurred in the executeGet()  ::: " + e);
		}
		_log.debug("executeGet invocation successful ::: ");
		return null;
	}
	
	

	@Override
	public String tokenExecutePost(String url, String payload, Map<String, String> headers) {

		_log.debug("Invoking the executPost method");
		try {
			StringBuilder urlBuilder = new StringBuilder(url);
			urlBuilder.append("?");
			urlBuilder.append(payload);
			String completeUrl = urlBuilder.toString();
			_log.debug("completeUrl is  >>>=========" + completeUrl);
			Http.Options options = new Http.Options();
			options.setLocation(completeUrl);
			if (!headers.isEmpty()) {
				options.setHeaders(headers);
			}
			options.setPost(true);
			Http.Response response = options.getResponse();
			_log.debug("response ??  ::: " + response.getResponseCode());
			_log.debug("executePost invocation successfuly");
			_log.debug("response>>>>>>>>>> " + http.URLtoString(options));
			return http.URLtoString(options);
		} catch (IOException e) {
			_log.error("An IO error occurred: " + e.getMessage());
			_log.debug("An IO error occurred: " + e);
		}
		return null;
	}

	@Override
	public String executePost(String url, String payload, Map<String, String> headers) {

		_log.debug("Invoking the executPost method");
		try {
			Http.Options options = new Http.Options();
			options.setLocation(url);
			if (!headers.isEmpty()) {
				options.setHeaders(headers);
			}
			options.setBody(payload, StringPool.BLANK, "UTF-8");
			options.setPost(true);
			_log.debug("executePost invocation successfuly");
			return http.URLtoString(options);
		} catch (IOException e) {
			_log.error("An IO error occurred: " + e.getMessage(), e);
		}
		return "saved";
	}

	@Override
	public String executePut(String url, String payload, Map<String, String> headers) {
		_log.debug("Invoking the executePut method ");

		try {
			Http.Options options = new Http.Options();
			options.setLocation(url);
			
			if (!headers.isEmpty()) {
				options.setHeaders(headers);
			}
			
			options.setBody(payload, StringPool.BLANK, "UTF-8");
			options.setPut(true);

			_log.debug("executePut invocation successfuly");
			return http.URLtoString(options);
		} catch (IOException e) {
			_log.debug("An IO error occurred: " + e.getMessage(), e);
		}
		return "updated successfully";
	}

	@Override
	public String executeDelete(String url, Map<String, String> headers) {
		_log.debug("Invoking the executeDelete method ");
		try {
			Http.Options options = new Http.Options();
			options.setLocation(url);
			options.setHeaders(headers);
			options.setDelete(true);
			_log.debug("executeDelete invocation successfuly");
			return http.URLtoString(options);
		} catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
		return "deleted successfully";
	}
	@Override
	public String getLiferayAuthorizationToken() {
		_log.debug("getToken >>>>> method started>>>>>>");
		JSONObject jsonResponse = null;
		try {
			LiferayConfiguration liferayConfiguration = provider.getSystemConfiguration(LiferayConfiguration.class);
			String tokenEndpoint = liferayConfiguration.getTokenEndPoint();
			String clientId = liferayConfiguration.getClientId();
			String clientSecret = liferayConfiguration.getClientSecret();
			_log.debug("tokenEndpoint >>>>  " + tokenEndpoint + "clientId >>>>  " + clientId + "clientSecret >>>>  "
					+ clientSecret);

			String payload = null;
			payload = String.format("grant_type=client_credentials&client_id=%s&client_secret=%s", clientId,
					clientSecret);

			Map<String, String> headers = new HashMap<>();
			headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
			String tokenResponse = tokenExecutePost(tokenEndpoint, payload, headers);
			_log.debug("tokenResponse>>>>>>>> " + tokenResponse);

			jsonResponse = JSONFactoryUtil.createJSONObject(tokenResponse);
		} catch (JSONException | ConfigurationException e) {
			_log.error(e.getMessage());
		}
		_log.debug("jsonResponse>>>>>>>>>> " + jsonResponse);
		String accessToken = jsonResponse.getString("access_token");
		_log.debug("accessToken>>>>>>>>> " + accessToken);
		_log.debug("getToken >>>>> method ended>>>>>>");
		return accessToken;
	}

	
}
