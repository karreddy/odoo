package mhcyber.common.api.api;

import java.util.Map;

/**
 * @author himanshu.sharma
 */
public interface MhcyberCommonApi {
	
	/**
	  * @author Himanshu.Sharma
	  * @return The below method used to get HTTP Header for authentications and it will return String.
	  */
	Map<String, String> getHttpHeaderInfoAndBasicAuth();
}