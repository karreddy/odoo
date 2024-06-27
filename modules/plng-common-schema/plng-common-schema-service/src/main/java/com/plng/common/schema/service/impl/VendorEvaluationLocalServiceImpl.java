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

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.model.VendorEvaluation;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalServiceUtil;
import com.plng.common.schema.service.VendorEvaluationLocalServiceUtil;
import com.plng.common.schema.service.base.VendorEvaluationLocalServiceBaseImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;



/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.VendorEvaluation",
	service = AopService.class
)
public class VendorEvaluationLocalServiceImpl
	extends VendorEvaluationLocalServiceBaseImpl {
	
	@Override
	public List<VendorEvaluation> getvendorEvaluationjsonObjectByUserId(long userId) {
		// TODO Auto-generated method stub
		return vendorEvaluationPersistence.findBySubmittedBy(userId);
	}
	
Log logger=LogFactoryUtil.getLog(VendorEvaluationLocalServiceImpl.class);
	
	public List<VendorEvaluation> findvendorFormDetailsByUserId(long submittedBy) {
		return vendorEvaluationPersistence.findBySubmittedBy(submittedBy);
	}
	
	 public void addjsonObjectFromJson(JSONObject jsonObject) {
	        try {
	           logger.info(jsonObject);
	           
	 		  EmployeeDirectory employeeDetailsByUserId = empDirectory.getEmployeeDetailsByUserId(jsonObject.getLong("userId"));
			  
			  
	 		 
				long employeeId=0;
				if (employeeDetailsByUserId != null) {
					 logger.info("Employee Found  :::::::::");
					employeeId = employeeDetailsByUserId.getEmployeeId();
				} else {
				    logger.info("Employee directories list is empty.");
				}
				
				
				try {
					  VendorEvaluation createVendorEvaluation = VendorEvaluationLocalServiceUtil.createVendorEvaluation(CounterLocalServiceUtil.increment(VendorEvaluation.class.getName()));
						
						createVendorEvaluation.setUserId(jsonObject.getLong("userId"));
						createVendorEvaluation.setCompanyId(jsonObject.getLong("companyId"));
						createVendorEvaluation.setEmpId(employeeId);
						createVendorEvaluation.setSubmittedBy(jsonObject.getLong("userId"));
						
						
						createVendorEvaluation.setTypeOfProcurement(jsonObject.getString("typeOfProcurement"));
						createVendorEvaluation.setName(jsonObject.getString("contractorName"));
						createVendorEvaluation.setEmployeeName(jsonObject.getString("employeefullName"));
						createVendorEvaluation.setVendorCode(jsonObject.getString("vendorCode"));
						createVendorEvaluation.setVendorSapCode(jsonObject.getString("vendorSapCode"));
						createVendorEvaluation.setJobLocation(jsonObject.getString("jobLocation"));
						createVendorEvaluation.setSapPONumber(jsonObject.getString("sapPONumber"));
						createVendorEvaluation.setSapPODate(getFormattedDate(jsonObject.getString("sapPODate")));
						createVendorEvaluation.setItemDescription(jsonObject.getString("itemDescription"));
						createVendorEvaluation.setDeliveryStartDate(getFormattedDate(jsonObject.getString("deliveryStartDate")));
						createVendorEvaluation.setDeliveryEndDate(getFormattedDate(jsonObject.getString("deliveryEndDate")));
						createVendorEvaluation.setCurrencyType(jsonObject.getString("currencyType"));
						createVendorEvaluation.setFinalContractValue(jsonObject.getString("finalContractValue"));
						createVendorEvaluation.setReasonForDelay(jsonObject.getString("reasonForDelay"));
						createVendorEvaluation.setTotalMarks(jsonObject.getString("totalMarks"));
					    createVendorEvaluation.setActualDeliveryDate(getFormattedDate(jsonObject.getString("actualDeliveryDate")));
						
					    createVendorEvaluation.setDelayCases(jsonObject.getString("delayCases"));
					    createVendorEvaluation.setDelPerfSelect(jsonObject.getString("delPerfSelect"));
					    createVendorEvaluation.setDelPerfRmk(jsonObject.getString("delPerfRmk"));
					    createVendorEvaluation.setDelPerFinalMarks(jsonObject.getString("delPerFinalMarks"));
					    createVendorEvaluation.setQualPerfOneMarks(jsonObject.getString("qualPerf_one_marks"));
					    createVendorEvaluation.setQualPerfOneRmk(jsonObject.getString("qualPerf_one_Rmk"));
					    createVendorEvaluation.setQualPerfTwoSelectValue(jsonObject.getString("qualPerf_two_select_value"));
					    createVendorEvaluation.setQualPerfTwoMarks(jsonObject.getString("qualPerf_two_marks"));
					    createVendorEvaluation.setQualPerfTwoRmk(jsonObject.getString("qualPerf_two_Rmk"));
					    createVendorEvaluation.setQualPerfThreeSelectValue(jsonObject.getString("qualPerf_three_select_value"));
					    createVendorEvaluation.setQualPerfThreeMarks(jsonObject.getString("qualPerf_three_marks"));
					    createVendorEvaluation.setQualPerfThreeRmk(jsonObject.getString("qualPerf_three_Rmk"));
					    createVendorEvaluation.setQualPerfFinalMarks(jsonObject.getString("qualPerfFinalMarks"));
					    createVendorEvaluation.setReliPerfOneMarks(jsonObject.getString("reliPerf_one_marks"));
					    createVendorEvaluation.setReliPerfOneRmk(jsonObject.getString("reliPerf_one_Rmk"));
					    createVendorEvaluation.setReliPerfTwoMarks(jsonObject.getString("reliPerf_two_marks"));
					    createVendorEvaluation.setReliPerfTwoRmk(jsonObject.getString("reliPerf_two_Rmk"));
					    createVendorEvaluation.setReliPerfThreeMarks(jsonObject.getString("reliPerf_three_marks"));
					    createVendorEvaluation.setReliPerfThreeRmk(jsonObject.getString("reliPerf_three_Rmk"));
					    createVendorEvaluation.setReliPerfFourMarks(jsonObject.getString("reliPerf_four_marks"));
					    createVendorEvaluation.setReliPerfFourRmk(jsonObject.getString("reliPerf_four_Rmk"));
					    createVendorEvaluation.setReliPerfFiveMarks(jsonObject.getString("reliPerf_five_marks"));
					    createVendorEvaluation.setReliPerfFiveRmk(jsonObject.getString("reliPerf_five_Rmk"));
					    createVendorEvaluation.setReliPerfFinalMarks(jsonObject.getString("reliPerfFinalMarks"));
					    createVendorEvaluation.setRange(jsonObject.getString("range"));
					    createVendorEvaluation.setFinalRemarks(jsonObject.getString("finalRemarks"));
					    
					    
					    
						VendorEvaluationLocalServiceUtil.addVendorEvaluation(createVendorEvaluation);
					
				} catch (Exception e) {
					logger.info("Error Submitting  the form " +e.getMessage());
				}
						
					
				sendMail(jsonObject.getString("email"), jsonObject.getString("employeefullName"), jsonObject.getString("totalMarks"));
	               
				
	            logger.info("Form data added successfully.");
	        } catch (Exception e) {
	            logger.error("Error adding form data: " + e.getMessage());
	        }
	    }

	
private Date getFormattedDate(String date) {
		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");
		if(date != null && !date.isEmpty()) {
			try {
				return smt.parse(date);
			} catch (Exception e) {
				logger.error("Exception on parsing date : "+e.getMessage());
			}
		}
		return null;
	}
	
private void sendMail(String email,String name,String totalMarks) {	
		
		
List<EmployeeDirectory> employeeDirectories = EmployeeDirectoryLocalServiceUtil.getEmployeeDirectories(-1, -1);
List<String> ccEmails =new ArrayList<>();
		long userId=0;
		if (!employeeDirectories.isEmpty()) {
		    for (EmployeeDirectory employeeDetails : employeeDirectories) {
		    	
		        if (employeeDetails.getDepartment().equalsIgnoreCase("CNP")) {
		        	userId = employeeDetails.getUserId();
		            logger.info("CNP Dept userId: " + userId);
		            User fetchUser = UserLocalServiceUtil.fetchUser(userId);
		            String emailAddress = fetchUser.getEmailAddress();
		            ccEmails.add(emailAddress);
		        }
		    }
		} else {
		    logger.info("Employee directories list is empty.");
		}
		
		
		logger.info(ccEmails);
		
		String fromEmail = "";
       String toEmail =email ;
        List<InternetAddress> ccAddressesList = new ArrayList<>();
        for (String ccEmail : ccEmails) {
            try {
				ccAddressesList.add(new InternetAddress(ccEmail));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        InternetAddress[] ccAddressesArray = ccAddressesList.toArray(new InternetAddress[0]);
logger.info(ccAddressesArray);
	try {
		logger.info("******************mail");
		
	    MailMessage mailMessage = new MailMessage();
	    mailMessage.setFrom(new InternetAddress(fromEmail));
	    mailMessage.setTo(new InternetAddress(toEmail));
	    mailMessage.setSubject("C&P Vendor Evaluation Form Submitted");
	      
	    String vendorName = name;
	    String yourCompany = "Petronet LNG Limited ";

	    String body = "Dear " + vendorName + ",\n\n" +
	                  "I trust this email finds you well. We wanted to extend our sincere appreciation for submitting the Vendor Evaluation Services Form. Your collaboration in providing the necessary information is crucial in helping us enhance our vendor partnerships.\n\n" +
	                  "Final marks: " + totalMarks + "\n\n" +
	                  "Best Regards,\n\n" +
	                  yourCompany;
	    
	    mailMessage.setBody(body);
	    mailMessage.setCC(ccAddressesArray);
	    logger.info(mailMessage);
	    MailServiceUtil.sendEmail(mailMessage);
	    System.out.println("Email sent successfully.");
	  
	} catch (Exception e) {
	    System.out.println("Failed to send the email: " + e.getMessage());
	}
	
	}
@Override
public List<VendorEvaluation> getDetailsBySubmittedByAndTypeOfProcurement(long submittedby, String typeOfProcurement) {
	// TODO Auto-generated method stub
	return vendorEvaluationPersistence.findBySubmittedByTypeOfProcurement(submittedby, typeOfProcurement);
}


@Override
public List<VendorEvaluation> getDetailsByTypeOfProcurement(String typeOfProcurement) {
	// TODO Auto-generated method stub
	return vendorEvaluationPersistence.findByTypeOfProcurement(typeOfProcurement);
}

@Reference
private EmployeeDirectoryLocalService empDirectory;


	
}