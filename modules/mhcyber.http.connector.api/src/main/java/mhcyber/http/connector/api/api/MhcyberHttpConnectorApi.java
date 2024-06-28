package mhcyber.http.connector.api.api;

import java.util.Map;
/**
 * @author Himanshu.Sharma
 */
public interface MhcyberHttpConnectorApi {
	/**
	 * @author Himanshu.Sharma
	 * @param url
	 * @param payload
	 * @return The below method will return the Authentication Token as a part of the response body.
	 * The token will be utilized to make subsequent calls to other API's.
	 */
	 String getAuthToken(String url, String payload);
	 
	 /**
	  * @author Himanshu.Sharma
	  * @param url
	  * @param parts
	  * @param headers
	  * @return The below method is used to get the headless webservice data and return complete json response of object.
	  */
	 String executeGet(String url, String payload,  Map<String, String> headers);
	 
	 /**
	  * @author Himanshu.Sharma
	  * @param url
	  * @param parts
	  * @return  The below method is used to save the data using headless api and it will return the String.
	  */
	 String executePost(String url, String payload, Map<String, String> headers);
	 
	 /**
	  * @author Himanshu.Sharma
	  * @param url
	  * @param parts
	  * @return The below method used to update the data using headless api and it will return String.
	  */
	 String executePut(String url, String payload, Map<String, String> headers);
	 
	 /**
	  * @author Himanshu.Sharma
	  * @param url
	  * @return The below method used to delete the data using headless api and it will return String.
	  */
	String executeDelete(String url, Map<String, String> headers);

	/**
	  * @author Himanshu.Sharma
	  * @param url
	  * @return The below method used to add the data using headless api and it will return String.
	  */
	String tokenExecutePost(String url, String payload, Map<String, String> headers);

	/**
	  * @author Himanshu.Sharma
	  * @return The below method used to get liferay authorize token and it will return String.
	  */

	String getLiferayAuthorizationToken();
	
	
}