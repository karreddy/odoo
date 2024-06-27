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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.plng.common.schema.model.ReimbursementForm;
import com.plng.common.schema.service.ReimbursementFormLocalServiceUtil;
import com.plng.common.schema.service.base.ReimbursementFormLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.ReimbursementForm",
	service = AopService.class
)
public class ReimbursementFormLocalServiceImpl
	extends ReimbursementFormLocalServiceBaseImpl {
	  Log _log = LogFactoryUtil.getLog(ReimbursementFormLocalServiceImpl.class);
		
		
		public ReimbursementForm addReimbursementForm(ServiceContext serviceContext,long userid,String ename,long eid,String makeandmodel,String imeino,String invoiceno,Date invoicedate,String vendorname,String vendorgstn,String Amount,String action,long fileentryid, Date enddate ) {
			long reimbursementid = counterLocalService.increment(ReimbursementForm.class.getName());
			ReimbursementForm createreimbursement=null;
			try {
				User user = userLocalService.getUser(serviceContext.getUserId());
				
				createreimbursement=ReimbursementFormLocalServiceUtil.createReimbursementForm(reimbursementid);
				createreimbursement.setUserId(userid);
				createreimbursement.setCreateDate(new Date());
		        createreimbursement.setEmpName(ename);
		        createreimbursement.setEmpId(eid);
		        createreimbursement.setMakeandModel(makeandmodel);
		        createreimbursement.setImeiNo(imeino);
		        createreimbursement.setInvoiceNo(invoiceno);
		        createreimbursement.setInvoiceDate(invoicedate);
		        createreimbursement.setVendorName(vendorname);
		        createreimbursement.setVendorGstn(vendorgstn);
		        createreimbursement.setAmount(Amount);
		        createreimbursement.setFileEntryId(fileentryid);
		        
		        createreimbursement.setUserName(user.getFullName());
		        createreimbursement.setCompanyId(serviceContext.getCompanyId());
		        createreimbursement.setGroupId(serviceContext.getScopeGroupId());
		        createreimbursement.setAction(WorkflowConstants.STATUS_PENDING);
		        createreimbursement.setStartDate(new Date());
		        createreimbursement.setEndDate(enddate);
		        createreimbursement.setActionbyuserid(userid);
		        createreimbursement.setActionbyusername(user.getFullName());
		        createreimbursement.setActiondate(new Date());
		       
		        createreimbursement=ReimbursementFormLocalServiceUtil.addReimbursementForm(createreimbursement);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return createreimbursement;
		}
	
		@Override
		public ReimbursementForm RejectForm(long reimbursementid,String makeandmodel,String imeino,String invoiceno,Date invoicedate,String vendorname,String vendorgstn,String amount,String action,long fileEntryId,long userid) {
			ReimbursementForm createreimbursement=null;
			
			try {
			User user = UserLocalServiceUtil.getUser(userid);
				
			createreimbursement = ReimbursementFormLocalServiceUtil.getReimbursementForm(reimbursementid);
	    	
	        createreimbursement.setMakeandModel(makeandmodel);
	        createreimbursement.setImeiNo(imeino);
	        createreimbursement.setInvoiceNo(invoiceno);
	        createreimbursement.setInvoiceDate(invoicedate);
	        createreimbursement.setVendorName(vendorname);
	        createreimbursement.setVendorGstn(vendorgstn);
	        createreimbursement.setAmount(amount);
	        createreimbursement.setFileEntryId(fileEntryId);
	        createreimbursement.setAction(1);
	        createreimbursement.setApprovedamount(null);
	        createreimbursement.setActionbyuserid(userid);
	        createreimbursement.setActionbyusername(user.getFullName());
	        createreimbursement.setFinancebyuserid(0);
	        createreimbursement.setFinancebyusername("");
	        createreimbursement.setAssetTag("");
	        createreimbursement.setDateOfCapitalization(null);
	        createreimbursement.setStartDate(new Date());
	        createreimbursement.setEndDate(null);
	        
	        createreimbursement=ReimbursementFormLocalServiceUtil.updateReimbursementForm(createreimbursement);
	        
			}catch(Exception e) {
				e.printStackTrace();
			}
			return createreimbursement;
		}
		
		
		@Override
		public ReimbursementForm updateAdmin(long reimbursementid, long userid,String amount,int action,String fullname) {
			ReimbursementForm getreimbursementworkflow=null;
			
			try {
				getreimbursementworkflow = ReimbursementFormLocalServiceUtil.getReimbursementForm(reimbursementid);
				getreimbursementworkflow.setAction(action);
				getreimbursementworkflow.setActionbyuserid(userid);
				getreimbursementworkflow.setActionbyusername(fullname);
				getreimbursementworkflow.setApprovedamount(amount);
				getreimbursementworkflow=ReimbursementFormLocalServiceUtil.updateReimbursementForm(getreimbursementworkflow);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return getreimbursementworkflow;
		}
		
		@Override
		public ReimbursementForm updateFinance(long reimbursementid, long userid,String assettag,Date dateofcapitilization,int action,String fullname) {
			ReimbursementForm getreimbursementworkflow=null;
			
			try {
				
				getreimbursementworkflow = ReimbursementFormLocalServiceUtil.getReimbursementForm(reimbursementid);
				getreimbursementworkflow.setAssetTag(assettag);
				getreimbursementworkflow.setDateOfCapitalization(dateofcapitilization);
				getreimbursementworkflow.setAction(action);
				getreimbursementworkflow.setFinancebyuserid(userid);
				getreimbursementworkflow.setFinancebyusername(fullname);
				getreimbursementworkflow.setFinanceactiondate(new Date());
				getreimbursementworkflow=ReimbursementFormLocalServiceUtil.updateReimbursementForm(getreimbursementworkflow);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return getreimbursementworkflow;
		}
		
		
		
		public ReimbursementForm addReimbursementForm(ServiceContext serviceContext,long userid,String ename,long eid,String makeandmodel,String imeino,String invoiceno,Date invoicedate,String vendorname,String vendorgstn,String Amount,String action,long fileentryid, Date enddate,String mobileorlaptop ) {
			long reimbursementid = counterLocalService.increment(ReimbursementForm.class.getName());
			ReimbursementForm createreimbursement=null;
			try {
				User user = userLocalService.getUser(serviceContext.getUserId());
				
				createreimbursement=ReimbursementFormLocalServiceUtil.createReimbursementForm(reimbursementid);
				createreimbursement.setMobileOrLaptop(mobileorlaptop);
				createreimbursement.setUserId(userid);
				createreimbursement.setCreateDate(new Date());
		        createreimbursement.setEmpName(ename);
		        createreimbursement.setEmpId(eid);
		        createreimbursement.setMakeandModel(makeandmodel);
		        createreimbursement.setImeiNo(imeino);
		        createreimbursement.setInvoiceNo(invoiceno);
		        createreimbursement.setInvoiceDate(invoicedate);
		        createreimbursement.setVendorName(vendorname);
		        createreimbursement.setVendorGstn(vendorgstn);
		        createreimbursement.setAmount(Amount);
		        createreimbursement.setFileEntryId(fileentryid);
		        createreimbursement.setUserName(user.getFullName());
		        createreimbursement.setCompanyId(serviceContext.getCompanyId());
		        createreimbursement.setGroupId(serviceContext.getScopeGroupId());
		        createreimbursement.setAction(WorkflowConstants.STATUS_PENDING);
		        createreimbursement.setStartDate(new Date());
		        createreimbursement.setEndDate(enddate);
		        createreimbursement=ReimbursementFormLocalServiceUtil.addReimbursementForm(createreimbursement);
		        
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return createreimbursement;
		}
}