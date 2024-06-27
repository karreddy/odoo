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
import com.plng.common.schema.model.StationaryRequest;
import com.plng.common.schema.service.StationaryRequestLocalServiceUtil;
import com.plng.common.schema.service.base.StationaryRequestLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;


/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.StationaryRequest",
	service = AopService.class
)
public class StationaryRequestLocalServiceImpl
	extends StationaryRequestLocalServiceBaseImpl {
	
	
	 Log _log=LogFactoryUtil.getLog(StationaryRequestLocalServiceImpl.class);
		public	List<StationaryRequest> findBystationaryRequestId(long stationaryRequestId){
			return this.stationaryRequestPersistence.findBystationaryRequestId(stationaryRequestId);
				
		}
		public	List<StationaryRequest> findByStatus(String status){
			return this.stationaryRequestPersistence.findBystatusLabel(status);		
		}
		public	List<StationaryRequest> findByRequestBy(long userId){
			return this.stationaryRequestPersistence.findByRequestBy(userId);		
		}
		
		 public StationaryRequest addstationaryRquest(ServiceContext serviceContext,ThemeDisplay themeDisplay, String selectedDataString,int status ,String statusLabel
				 ){
			 
			
			 User user=themeDisplay.getUser();
			  long userId=user.getUserId();
			  StationaryRequest createStationary_Request =null;        
			  try {
			    createStationary_Request = StationaryRequestLocalServiceUtil.createStationaryRequest(CounterLocalServiceUtil.increment(StationaryRequest.class.getName()));
		        createStationary_Request.setRequirement(selectedDataString);
		        createStationary_Request.setRequestBy(userId);
		        createStationary_Request.setEmployeeName(user.getFullName());
		        createStationary_Request.setGroupId(user.getGroupId());
		        createStationary_Request.setStatusByUserName(user.getFullName());
		        createStationary_Request.setStatus(status);
		        createStationary_Request.setStatusLabel(statusLabel);
		        createStationary_Request.setStatusByUserId(user.getUserId());
		        StationaryRequestLocalServiceUtil.addStationaryRequest(createStationary_Request);
		      
				        
				           
//				  String Title="Stationary Request By    "+user.getFullName();
//				        
//				        
//				       AssetEntry assetEntry = assetEntryLocalService.updateEntry( user.getUserId(), serviceContext.getScopeGroupId(), new Date(),
//				            new Date(), StationaryRequest.class.getName(),createStationary_Request.getStationaryRequestId(),"", 0, null, null, true, false, new Date(), null,
//				            new Date(), null, ContentTypes.TEXT_HTML, Title,Title , null, null, null, 0, 0, null);
//			
//				       
//				       Indexer<StationaryRequest> indexer = IndexerRegistryUtil.nullSafeGetIndexer(StationaryRequest.class);
//				       indexer.reindex(createStationary_Request);
//				 
//				       WorkflowHandlerRegistryUtil.startWorkflowInstance(createStationary_Request.getCompanyId(), createStationary_Request.getGroupId(), createStationary_Request.getRequestBy(), StationaryRequest.class.getName(),
//				    		   createStationary_Request.getPrimaryKey(), createStationary_Request, serviceContext);   
				    } catch (Exception e) {
				      
				     }
				       return createStationary_Request;
				 }
		 public void updateStatus(long userId,long stationaryReqId,int status,String statusLable,String remark,String amount){
			 
			 StationaryRequest updateStationaryRequest=stationaryRequestPersistence.fetchByPrimaryKey(stationaryReqId);
			 
			 updateStationaryRequest.setStatus(status);
			 updateStationaryRequest.setStatusLabel(statusLable);
			 updateStationaryRequest.setAmount(amount);
			 if ("In Process".equals(statusLable)) {
			   
			 updateStationaryRequest.setRequestProcessorRemark(remark);
			 }
			 updateStationaryRequest.setStatusByUserId(userId);
			 updateStationaryRequest.setStatusDate(new Date());
			 
			 User user = null;
			 try {
			      user = userLocalService.getUser(userId);
			      updateStationaryRequest.setStatusByUserName(user.getFullName());
			      updateStationaryRequest.setStatusByUserUuid(user.getUserUuid());
			 } catch (Exception e) {
			     e.printStackTrace();
			 }
			 updateStationaryRequest=stationaryRequestPersistence.update(updateStationaryRequest);
			
		
			 }
		
}