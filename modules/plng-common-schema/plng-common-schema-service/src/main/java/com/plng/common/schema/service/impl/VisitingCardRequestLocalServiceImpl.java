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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.plng.common.schema.model.MaintenanceRequest;
import com.plng.common.schema.model.VisitingCardRequest;
import com.plng.common.schema.service.VisitingCardRequestLocalServiceUtil;
import com.plng.common.schema.service.base.VisitingCardRequestLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;


/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.VisitingCardRequest",
	service = AopService.class
)
public class VisitingCardRequestLocalServiceImpl
	extends VisitingCardRequestLocalServiceBaseImpl {
	
	
	 Log _log=LogFactoryUtil.getLog(VisitingCardRequestLocalServiceImpl.class);
		
		public	List<VisitingCardRequest> findByVisitingCardRequestId(long visitingCardReqId){
			return this.visitingCardRequestPersistence.findByVisCardRequestId(visitingCardReqId);
				
		}
		public	List<VisitingCardRequest> findByStatus(String status){
			return this.visitingCardRequestPersistence.findBystatusLabel(status);		
		}
		
		public	List<VisitingCardRequest> findByRequestBy(long userId){
			return this.visitingCardRequestPersistence.findByRequestBy(userId);		
		}
			
		 public VisitingCardRequest addVisitingCardRquest(ServiceContext serviceContext,ThemeDisplay themeDisplay,
					boolean printAdrssbackSide,
					String Quantity,
					String rmk,
					String faxNumber,
					String directNumber,
					String extensionNumber,
					String mobileNumber,
					String email,
					String designation,
					String nameToBePrinted,
					String employeeName,
					String location,int status ,String statusLabel
				 ){
			 
	
			
			 User user=themeDisplay.getUser();
			  long userId=user.getUserId();
			  VisitingCardRequest createVisitingCard_Request=null;
			      
			  try {
				   createVisitingCard_Request = VisitingCardRequestLocalServiceUtil.createVisitingCardRequest(CounterLocalServiceUtil.increment(VisitingCardRequest.class.getName()));
				 
				  createVisitingCard_Request.setRequestBy(userId);
				  createVisitingCard_Request.setEmployeeName(employeeName);
				  createVisitingCard_Request.setGroupId(user.getGroupId());
				  createVisitingCard_Request.setStatusByUserName(user.getFullName());
				  createVisitingCard_Request.setStatus(status);
				  createVisitingCard_Request.setStatusLabel(statusLabel);
				  createVisitingCard_Request.setStatusByUserId(user.getUserId());
				  createVisitingCard_Request.setBackSideAddress(printAdrssbackSide);
				  createVisitingCard_Request.setQuantity(Quantity);
				  createVisitingCard_Request.setRemarks(rmk);
				  createVisitingCard_Request.setFaxNumber(faxNumber);
				  createVisitingCard_Request.setDirectNumber(directNumber);
				  createVisitingCard_Request.setExtnNumber(extensionNumber);
				  createVisitingCard_Request.setMobileNumber(mobileNumber);
				  createVisitingCard_Request.setEmailAddress(email);
				  createVisitingCard_Request.setDesignation(designation);
				  createVisitingCard_Request.setNameTobePrint(nameToBePrinted);
				  createVisitingCard_Request.setLocation(location);
		        
		        
				  VisitingCardRequestLocalServiceUtil.addVisitingCardRequest(createVisitingCard_Request);
		      
				        
//				           
//				  String Title="Visiting Card Request By    "+user.getFullName();
//				        
//				        
//				       AssetEntry assetEntry = assetEntryLocalService.updateEntry( user.getUserId(), serviceContext.getScopeGroupId(), new Date(),
//				            new Date(), VisitingCardRequest.class.getName(),createVisitingCard_Request.getVisCardRequestId(),"", 0, null, null, true, false, new Date(), null,
//				            new Date(), null, ContentTypes.TEXT_HTML, Title,Title , null, null, null, 0, 0, null);
//			
//				       
//				       Indexer<VisitingCardRequest> indexer = IndexerRegistryUtil.nullSafeGetIndexer(VisitingCardRequest.class);
//				       indexer.reindex(createVisitingCard_Request);
//				 
//				       WorkflowHandlerRegistryUtil.startWorkflowInstance(createVisitingCard_Request.getCompanyId(), createVisitingCard_Request.getGroupId(), createVisitingCard_Request.getRequestBy(), VisitingCardRequest.class.getName(),
//				    		   createVisitingCard_Request.getPrimaryKey(), createVisitingCard_Request, serviceContext);   
				    } catch (Exception e) {
				      
				     }
				       return createVisitingCard_Request;
				 }
		 public  void updateStatus(long userId,long visitingCrdReqId,int status,String statusLable,String remark,String amount){
			 VisitingCardRequest updateVisitingCardRequest=visitingCardRequestPersistence.fetchByPrimaryKey(visitingCrdReqId);
			 
			
			 updateVisitingCardRequest.setStatus(status);
			 updateVisitingCardRequest.setStatusLabel(statusLable);
			 if ("In Process".equals(statusLable)) {
			 updateVisitingCardRequest.setRequestProcessorRemark(remark);
			 }
			 updateVisitingCardRequest.setAmount(amount);
			 updateVisitingCardRequest.setStatusByUserId(userId);
			 updateVisitingCardRequest.setStatusDate(new Date());
			 User user = null;
			 try {
			      user = userLocalService.getUser(userId);
			      updateVisitingCardRequest.setStatusByUserName(user.getFullName());
			      updateVisitingCardRequest.setStatusByUserUuid(user.getUserUuid());
			 } catch (Exception e) {
			     e.printStackTrace();
			 }
			 updateVisitingCardRequest=visitingCardRequestPersistence.update(updateVisitingCardRequest);
			
			 }
	
}