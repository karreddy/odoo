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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.plng.common.schema.exception.NoSuchEmployeeDirectoryException;
import com.plng.common.schema.exception.NoSuchSamaritanCertificatesException;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.model.SamaritanCertificates;
import com.plng.common.schema.service.SamaritanCertificatesLocalServiceUtil;
import com.plng.common.schema.service.base.SamaritanCertificatesLocalServiceBaseImpl;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;



/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.SamaritanCertificates",
	service = AopService.class
)
public class SamaritanCertificatesLocalServiceImpl
	extends SamaritanCertificatesLocalServiceBaseImpl {
	
	
Log logger=LogFactoryUtil.getLog(SamaritanCertificatesLocalServiceImpl.class);
	
	public List<SamaritanCertificates>getCertificaesByempId(String empId){
		return samaritanCertificatesPersistence.findByempId(empId);
		
	}

	public SamaritanCertificates getCertificateByPaymentRefNo(String PaymentRefNo) {

		SamaritanCertificates SamaritanCertificate = null;
		try {
			SamaritanCertificate = samaritanCertificatesPersistence.findBypaymentReferenceNo(PaymentRefNo);
		} catch (NoSuchSamaritanCertificatesException e) {
		}
		return SamaritanCertificate;
	}
	
	public JSONArray importExcel(File file) {
		
		
		
	      JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
	        try (
	        		
	        		
	        	      Workbook workbook = new XSSFWorkbook(file)) {

	            Sheet sheet = workbook.getSheetAt(0); 

	             

	               	for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
	               	    Row row = sheet.getRow(rowIndex);
	               	    if (row != null) {
	               	
	               	
	               	
	                 double SlNo =row.getCell(0).getNumericCellValue();
	                 String name =row.getCell(1).getStringCellValue();
	                 double empId=row.getCell(2).getNumericCellValue();
	                 double amount = row.getCell(3).getNumericCellValue();
	                 String id =row.getCell(4).getStringCellValue();
	                 java.util.Date date1 = row.getCell(5).getDateCellValue();
	                 java.util.Date date2 = row.getCell(6).getDateCellValue();
	                 
	               long amount1=(long)amount;
	               long slno=(long)SlNo;
	               long empId1=(long)empId;
	               DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	               String formattedDate1 = dateFormat.format(date1);
	               String formattedDate2 = dateFormat.format(date2);
	               
	               JSONObject jsonObject =JSONFactoryUtil.createJSONObject();
	               jsonObject.put("SlNo", slno);
	               jsonObject.put("Name", name);
	               jsonObject.put("Amount", amount1);
	               jsonObject.put("ID", id);
	               jsonObject.put("Date1", formattedDate1);
	               jsonObject.put("Date2", formattedDate2);
	            
	               
	               jsonArray.put(jsonObject);
	               
	               if (!certificateExists(id)) {
	               SamaritanCertificates AddSamaritanCertificate = SamaritanCertificatesLocalServiceUtil.createSamaritanCertificates(CounterLocalServiceUtil.increment(SamaritanCertificates.class.getName()));
	              AddSamaritanCertificate.setPaymentReferenceNo(id);
	               AddSamaritanCertificate.setEmployeeName(name);
	               AddSamaritanCertificate.setAmount(amount1);
	               AddSamaritanCertificate.setContributionDate(date1);
	               AddSamaritanCertificate.setDataEntered(date2);
	               AddSamaritanCertificate.setEmpId(String.valueOf(empId1));
	               SamaritanCertificatesLocalServiceUtil.addSamaritanCertificates(AddSamaritanCertificate);
	               logger.info("Data added successfully for ID: " + id);
	               } else {
	                   logger.info("Certificate with ID " + id + " already exists. Skipping...");
	               }
	               
	                   logger.info(SlNo);
	                   logger.info(name);
	                   logger.info(empId);
	                   logger.info(amount);
	                   logger.info(amount1);
	                   logger.info(id);
	                   logger.info(date1); 
	                   logger.info(date2);
	                   logger.info(formattedDate1); 
	                   logger.info(formattedDate2);
	                  
	                   
	                 
	                 
	                   }}

	               logger.info("Data imported successfully!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	               workbook.close();
	           } catch (Exception e) {
	               logger.error("Error importing data from Excel", e);
	           }

	       	
	  

			
	    
	       
	        logger.info(jsonArray);
			return jsonArray;
	   
		}
private boolean certificateExists(String certificateId) {
    try {
        List<SamaritanCertificates> samaritanCertificatesList =
                SamaritanCertificatesLocalServiceUtil.getSamaritanCertificateses(-1, -1);

        for (SamaritanCertificates certificates : samaritanCertificatesList) {
            if (certificateId.equals(certificates.getPaymentReferenceNo())) {
                return true; 
            }
        }
        return false;
    } catch (Exception e) {
    	 logger.error("Error checking certificate existence", e);
        return false;
    }
}
}