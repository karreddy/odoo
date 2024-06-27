/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.plng.common.schema.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.Validator;
import com.plng.common.schema.model.SalaryDetails;
import com.plng.common.schema.service.base.SalaryDetailsLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.SalaryDetails",
	service = AopService.class
)
public class SalaryDetailsLocalServiceImpl
	extends SalaryDetailsLocalServiceBaseImpl {
	
	  public SalaryDetails getSalaryDetailsByApplicationNo(long  applicationNumber) { 
		  try {
			  return salaryDetailsPersistence.findByapplicationNumber(applicationNumber);
		  }catch (Exception e) {
			  return null; 
		}
		  
	  }
	  public SalaryDetails addUpdateSalaryDetails(SalaryDetails salaryDetails) { 
		  try {
			  if(Validator.isNotNull(salaryDetails)) {
				  SalaryDetails salaryDetails2=  salaryDetailsLocalService.addSalaryDetails(salaryDetails);
				  return salaryDetails2; 
			  }
			 
		  }catch (Exception e) {
			  return null;
		}
		return null;
	  }

	  public SalaryDetails getSalaryDetailsByAppNoAndUserIdAndJobCode(long  applicationNumber, long userId, String jobCode) { 
		  try {
			  return salaryDetailsPersistence.findByjobCodeAndUserIdAndAppNo(jobCode, userId, applicationNumber);
		  }catch (Exception e) {
			  return null; 
		}
		  
	  }
	 
}