package mhcyber.liferay.configurations.web.api;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition.Scope;

import aQute.bnd.annotation.metatype.Meta.AD;
import aQute.bnd.annotation.metatype.Meta.OCD;
/**
 * @author himanshu.sharma
 */
@ExtendedObjectClassDefinition(category = "mhcyber-configuration", scope = Scope.COMPANY)

@OCD(id = "mhcyber.liferay.configurations.web.api.LiferayConfiguration", localization = "content/language", name = "Liferay")

public interface LiferayConfiguration {

		/**
		 * 
		 * @return This method returns the client id.
		 */
		@AD(deflt = "", name = "Client Id", description = "This is for Client Id", required = false)
		String getClientId();

		/**
		 * 
		 * @return This method returns the client secret.
		 */
		@AD(deflt = "", name = "Client Secret", description = "This is for Client Secret", required = false)
		String getClientSecret();
		/**
		 * 
		 * @return This method returns the token end point.
		 */
		@AD(deflt = "", name = "Token End Point", description = "This is for Token End Point", required = false)
		String getTokenEndPoint();
		/**
		 * 
		 * @return This method returns the boolean for start or stop the schedule task.
		 */
		@AD(deflt = "false", name = "Start Scheduled Task", description = "Set this true to Start the Schduled task and Set this false to Stop the schedule task for token", required = false)
		boolean stopScheduleExecution();	
		
}