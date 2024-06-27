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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.plng.common.schema.model.LocalVehicleRequest;
import com.plng.common.schema.service.LocalVehicleRequestLocalServiceUtil;
import com.plng.common.schema.service.base.LocalVehicleRequestLocalServiceBaseImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.plng.common.schema.model.LocalVehicleRequest",
	service = AopService.class
)
public class LocalVehicleRequestLocalServiceImpl
	extends LocalVehicleRequestLocalServiceBaseImpl {
	
	 Log _log=LogFactoryUtil.getLog(LocalVehicleRequestLocalServiceImpl.class);
		public	List<LocalVehicleRequest> findBylocalVehicalRequestId(long locVehicleRequestId){
			return this.localVehicleRequestPersistence.findBylocVehicleRequestId(locVehicleRequestId);
				
		}
		
		public	List<LocalVehicleRequest> findByStatus(String status){
			return this.localVehicleRequestPersistence.findBystatusLabel(status);		
		}
		
		public	List<LocalVehicleRequest> findByRequestBy(long userId){
			return this.localVehicleRequestPersistence.findByRequestBy(userId);		
		}
		public LocalVehicleRequest addLocalVehicleRequest(ServiceContext serviceContext, ThemeDisplay themeDisplay, String empName,
                String location, String travelDate, String reportingTime,
                String tripStartFrom, String placesToBeVisited,
                String purposeOfTravel, String remarks, 
                boolean approvalRequired, String oneWayOrReturn ,int status ,String statusLabel) {
			 
			
			 User user=themeDisplay.getUser();
			  long userId=user.getUserId();
			  LocalVehicleRequest createRequest=null;
			         
			  try {
				  createRequest=LocalVehicleRequestLocalServiceUtil.createLocalVehicleRequest(CounterLocalServiceUtil.increment(LocalVehicleRequest.class.getName()));
						  createRequest.setEmployeeName(empName);
				  
				
				  createRequest.setRequestBy(userId);
				  createRequest.setEmployeeName(user.getFullName());
				  createRequest.setGroupId(user.getGroupId());
				  createRequest.setStatusByUserName(user.getFullName());
				  createRequest.setStatus(status);
				  createRequest.setStatusLabel(statusLabel);
				  createRequest.setStatusByUserId(user.getUserId());
				  
			        createRequest.setLocation(location);
			        createRequest.setTravelDtae(getFormattedDate(travelDate));
			        createRequest.setReportingTime(reportingTime);
			        createRequest.setReportingPlace(tripStartFrom);
			        createRequest.setPlacesToBeVisited(placesToBeVisited);
			        createRequest.setPurposeOfTravel(purposeOfTravel);
			        createRequest.setRemarks(remarks);
			        createRequest.setApprovalReq(approvalRequired);
			        createRequest.setOneWayOrReturn(oneWayOrReturn);
				  
				  LocalVehicleRequestLocalServiceUtil.addLocalVehicleRequest(createRequest);
		      
				        
				           
//				  String Title="Local Vehicle Request By    "+user.getFullName();
				        
				        
//				       AssetEntry assetEntry = assetEntryLocalService.updateEntry( user.getUserId(), serviceContext.getScopeGroupId(), new Date(),
//				            new Date(), LocalVehicleRequest.class.getName(),createRequest.getLocVehicleRequestId(),"", 0, null, null, true, false, new Date(), null,
//				            new Date(), null, ContentTypes.TEXT_HTML, Title,Title , null, null, null, 0, 0, null);
//			
//				       
//				       Indexer<LocalVehicleRequest> indexer = IndexerRegistryUtil.nullSafeGetIndexer(LocalVehicleRequest.class);
//				       indexer.reindex(createRequest);
//				 
//				       WorkflowHandlerRegistryUtil.startWorkflowInstance(createRequest.getCompanyId(), createRequest.getGroupId(), createRequest.getRequestBy(), LocalVehicleRequest.class.getName(),
//				    		   createRequest.getPrimaryKey(), createRequest, serviceContext);   
				  
				    } catch (Exception e) {
				      
				     }
				       return createRequest;
				 }
		 public void updateStatus(long userId,long localVehicleReqId,int status,String statusLable,String remark,String amount,String vehilceDetails){
			 
			 LocalVehicleRequest updateLocalVehicleRequest=localVehicleRequestPersistence.fetchByPrimaryKey(localVehicleReqId);
			 
			 updateLocalVehicleRequest.setStatus(status);
			 updateLocalVehicleRequest.setStatusLabel(statusLable);
			 if ("In Process".equals(statusLable)) {
			 updateLocalVehicleRequest.setRequestProcessorRemark(remark);
			 }
			 updateLocalVehicleRequest.setVehilceDetails(vehilceDetails);
			 updateLocalVehicleRequest.setAmount(amount);
			 updateLocalVehicleRequest.setStatusByUserId(userId);
			 updateLocalVehicleRequest.setStatusDate(new Date());
			 User user = null;
			 try {
			      user = userLocalService.getUser(userId);
			      updateLocalVehicleRequest.setStatusByUserName(user.getFullName());
			      updateLocalVehicleRequest.setStatusByUserUuid(user.getUserUuid());
			 } catch (Exception e) {
			     e.printStackTrace();
			 }
			 updateLocalVehicleRequest=localVehicleRequestPersistence.update(updateLocalVehicleRequest);
			
	
			
			 }
		 
		 private Date getFormattedDate(String date) {
				SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");
				if(date != null && !date.isEmpty()) {
					try {
						return smt.parse(date);
					} catch (Exception e) {
						_log.error("Exception on parsing date : "+e.getMessage());
					}
				}
				return null;
			}
}