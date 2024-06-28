package mhcyber.common.api.service;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import mhcyber.common.api.api.MhcyberCommonApi;

@Component(immediate = true, service = MhcyberCommonApi.class)

public class MhcyberCommonService implements MhcyberCommonApi {
	
	private static final Log LOGGER = LogFactoryUtil.getLog(MhcyberCommonService.class);
	
	@Activate
	public void refreshTokenCode() {
		Timer timer = new Timer();
		timer.schedule(new RefreshLiferayAuthToken(), 0, 300000); // for 5 minutes
	}
	
	@Override
	public Map<String, String> getHttpHeaderInfoAndBasicAuth() {
		Map<String, String> headers = new HashMap<>();
		String token = RefreshLiferayAuthToken.getToken();
		LOGGER.debug("token in getHttpHeaderInfoAndBasicAuth" + token);
		headers.put("Authorization", "Bearer " + token);
		headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
		return headers;
	}
}
