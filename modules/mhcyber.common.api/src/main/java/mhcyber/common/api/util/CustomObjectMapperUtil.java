package mhcyber.common.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.DateFormat;
/**
 * @author himanshu.sharma
 */
public class CustomObjectMapperUtil {
	private CustomObjectMapperUtil() {}
	/**
	 * @author himanshu.sharma
	 * @return this method return the instance of the object mapper.
	 */
	private static final ObjectMapper _objectMapper;
	
	/**
	 * @author himanshu.sharma
	 * @return this method return the instance of the object mapper.
	 */
	static {
		_objectMapper = new ObjectMapper();
		_objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	}
	
	/**
	 * @author himanshu.sharma
	 * @return this method return the instance of the object mapper.
	 */
	public static final ObjectMapper getObjectMapper() {
		return _objectMapper;
	}
	
	/**
	 * @author himanshu.sharma
	 * @return this method read the response and set the objects value and returns the DTO.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T readValue(String response, Class<?> clazz){
		try {
			_log.debug("inside the readValue");
			return (T) _objectMapper.readValue(response, clazz);
		} catch (JsonProcessingException e) {
			_log.error("Exception while processing the Json ::: " + e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T writeValueAsString(Object object, DateFormat dateFormat){
		try {
			if (Validator.isNotNull(dateFormat)) {
				_objectMapper.setDateFormat(dateFormat);
			}
			_log.debug("inside the readValue");
			return (T) _objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			_log.error("Exception while processing the Json ::: " + e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readValue(String response, Class<?> clazz, DateFormat dateFormat){
		try {
			if (Validator.isNotNull(dateFormat)) {
				_objectMapper.setDateFormat(dateFormat);
			}
			_log.debug("inside the readValue");
			return (T) _objectMapper.readValue(response, clazz);
		} catch (Exception e) {
			_log.error("Exception while processing the Json ::: " + e);
		}
		return null;
	}
	
	private static final Log _log = LogFactoryUtil.getLog(CustomObjectMapperUtil.class);
}