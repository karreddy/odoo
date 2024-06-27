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
import com.plng.common.schema.model.StationaryRequest;
import com.plng.common.schema.service.MaintenanceRequestLocalServiceUtil;
import com.plng.common.schema.service.base.MaintenanceRequestLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;



/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.MaintenanceRequest",
	service = AopService.class
)
public class MaintenanceRequestLocalServiceImpl
	extends MaintenanceRequestLocalServiceBaseImpl {
	

	 Log _log=LogFactoryUtil.getLog(MaintenanceRequestLocalServiceImpl.class);

public	List<MaintenanceRequest> findByMaintenanceRequestId(long maintenanceReqId){
	return this.maintenanceRequestPersistence.findByMaintenanceRequestId(maintenanceReqId);
		
}
public	List<MaintenanceRequest> findByStatus(String status){
	return this.maintenanceRequestPersistence.findBystatusLabel(status);		
}
public	List<MaintenanceRequest> findByRequestBy(long userId){
	return this.maintenanceRequestPersistence.findByRequestBy(userId);		
}
public MaintenanceRequest addmaintenaceRquest(ServiceContext serviceContext,ThemeDisplay themeDisplay, String location, String empName,String category, String details,
		int status ,String statusLabel ){
		 
		
		 User user=themeDisplay.getUser();
		  long userId=user.getUserId();
		  MaintenanceRequest createMaintenance_Request =null;        
		  try {
			 createMaintenance_Request = MaintenanceRequestLocalServiceUtil.createMaintenanceRequest(CounterLocalServiceUtil.increment(MaintenanceRequest.class.getName()));
			 createMaintenance_Request.setLocation(location);
			 createMaintenance_Request.setEmployeeName(empName);
			 createMaintenance_Request.setCategory(category);
			 createMaintenance_Request.setRequestDetails(details);
			 createMaintenance_Request.setRequestBy(userId);
			 createMaintenance_Request.setGroupId(user.getGroupId());
			 createMaintenance_Request.setCompanyId(user.getCompanyId());
			 createMaintenance_Request.setStatusByUserName(user.getFullName());
			 createMaintenance_Request.setStatus(status);
			 createMaintenance_Request.setStatusLabel(statusLabel);
		     createMaintenance_Request.setStatusByUserId(user.getUserId());
			 
			 MaintenanceRequestLocalServiceUtil.addMaintenanceRequest(createMaintenance_Request);
			  
//			           
//			  String Title="Maintenance Request By    "+user.getFullName();
//			        
//			        
//			       AssetEntry assetEntry = assetEntryLocalService.updateEntry( user.getUserId(), serviceContext.getScopeGroupId(), new Date(),
//			            new Date(), MaintenanceRequest.class.getName(),createMaintenance_Request.getMaintenanceRequestId(),"", 0, null, null, true, false, new Date(), null,
//			            new Date(), null, ContentTypes.TEXT_HTML, Title,Title , null, null, null, 0, 0, null);
//		
//			       
//			       Indexer<MaintenanceRequest> indexer = IndexerRegistryUtil.nullSafeGetIndexer(MaintenanceRequest.class);
//			       indexer.reindex(createMaintenance_Request);
//			 
//			       WorkflowHandlerRegistryUtil.startWorkflowInstance(createMaintenance_Request.getCompanyId(), createMaintenance_Request.getGroupId(), createMaintenance_Request.getRequestBy(), MaintenanceRequest.class.getName(),
//			    		   createMaintenance_Request.getPrimaryKey(), createMaintenance_Request, serviceContext);   
			    } catch (Exception e) {
			      
			     }
			       return createMaintenance_Request;
			 }
	 
	 public void  updateStatus(long userId,long maintenanceReqId,int status,String statusLable,String remark,String amount){
		 
		 MaintenanceRequest updateMaintenanceRequest=maintenanceRequestPersistence.fetchByPrimaryKey(maintenanceReqId);
		 
		 
		 updateMaintenanceRequest.setStatus(status);
		 updateMaintenanceRequest.setStatusLabel(statusLable);
		 if ("In Process".equals(statusLable)) {
		 updateMaintenanceRequest.setRequestProcessorRemark(remark);
		 }
		 updateMaintenanceRequest.setAmount(amount);
		 updateMaintenanceRequest.setStatusByUserId(userId);
		 updateMaintenanceRequest.setStatusDate(new Date());
		 
		 User user = null;
		 try {
		      user = userLocalService.getUser(userId);
		      updateMaintenanceRequest.setStatusByUserName(user.getFullName());
		      updateMaintenanceRequest.setStatusByUserUuid(user.getUserUuid());
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		 updateMaintenanceRequest=maintenanceRequestPersistence.update(updateMaintenanceRequest);
		
	
		 }
	
	
}