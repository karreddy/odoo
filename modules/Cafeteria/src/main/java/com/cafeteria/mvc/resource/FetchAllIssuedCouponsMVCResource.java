package com.cafeteria.mvc.resource;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.mvc.util.CafeteriaUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.ContractLogin;
import com.plng.common.schema.model.CouponStatus;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.ContractLoginLocalService;
import com.plng.common.schema.service.CouponStatusLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;

import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +CafeteriaPortletKeys.CAFETERIA_ADMIN_LIVE,
			"mvc.command.name="+CafeteriaPortletKeys.CAFETERIA_ALL_ISSUED_COUPONS
		},
		service = MVCResourceCommand.class
	)
public class FetchAllIssuedCouponsMVCResource extends BaseMVCResourceCommand  {
	private static final Log _log = LogFactoryUtil.getLog(FetchAllIssuedCouponsMVCResource.class);
	
	@Reference(unbind = "-")
	private CafeteriaUtil cafeteriaUtil;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("Fetch All Issued Coupons MVCResource...");
		
		 JSONArray clArray = JSONFactoryUtil.createJSONArray();
		List<CouponStatus> IssuedCouponList =null;
		  JSONObject jsonObject = null;
		  
		  
		try {
			IssuedCouponList = _couponStatusLocalService.getAllTodaysCoupons()
								.stream()
							    .sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
							    .collect(Collectors.toList());
			_log.info("IssuedCouponList >>>"+IssuedCouponList);
			for (CouponStatus couponStatus : IssuedCouponList) {
				  jsonObject = JSONFactoryUtil.createJSONObject();
				long couEmpid = couponStatus.getEmpid();
				long item_id = couponStatus.getItem_id();
				String itemDesc =null;
				
				try {
					CafeItemMaster cafeItemMaster = _cafeItemMasterLocalService.getCafeItemMaster(item_id);
					itemDesc = cafeItemMaster.getItemDesc();
				}catch(Exception e) {
					_log.error("Exeception getting Item Name>>>"+e.getMessage());
				}
				
				if(Validator.isNotNull(couEmpid)) {
					 try {
	                        EmployeeDirectory empdetails = _employeeDirectoryLocalService.getEmployeeDirectory(couEmpid);
	                        if (Validator.isNotNull(empdetails)) {
	                            _log.info("Employee details found for couEmpid>>>" + couEmpid);
	                            jsonObject.put("empId", couponStatus.getEmpid());
	                            jsonObject.put("qty", couponStatus.getRequested_quant());
	                            jsonObject.put("itemDesc",itemDesc );
	                            jsonObject.put("empName",empdetails.getEmployeeName());
	                            
	                            clArray.put(jsonObject);
	                            continue;
	                        }
	                    } catch (Exception e) {
	                        _log.debug("Employee not found, checking contract employee");
	                    }
					
					 try {
	                        ContractLogin contEmpByLrId = _contractLoginLocalService.getContEmpByUserId(couEmpid);
	                        if (Validator.isNotNull(contEmpByLrId)) {
	                            _log.info("Contract employee details found for couEmpid>>>" + couEmpid);
	                            jsonObject.put("empId", couponStatus.getEmpid());
	                            jsonObject.put("qty", couponStatus.getRequested_quant());
	                            jsonObject.put("itemDesc",itemDesc );
	                            jsonObject.put("empName",contEmpByLrId.getName());
	                            
	                            clArray.put(jsonObject);
	                            continue;
	                        }
	                    } catch (Exception e) {
	                        _log.debug("Contract employee not found, checking user");
	                    }
					
					 try {
	                        User user = _userLocalService.getUser(couEmpid);
	                        if (Validator.isNotNull(user)) {
	                            _log.info("User details found for couEmpid>>>" + couEmpid);
	                            jsonObject.put("empId", couponStatus.getEmpid());
	                            jsonObject.put("qty", couponStatus.getRequested_quant());
	                            jsonObject.put("itemDesc",itemDesc );
	                            jsonObject.put("empName",user.getFullName());
	                            
	                            clArray.put(jsonObject);
	                        }
	                    } catch (Exception e) {
	                        _log.error("Error fetching user details: " + e.getMessage(), e);
	                    }
					
				}
			}
		} catch (Exception e) {
			_log.error("Exe getting data >>>"+e.getMessage());
		}
		
		String jsonString = clArray.toString();
	    resourceResponse.setContentType("application/json");
	    PrintWriter writer = resourceResponse.getWriter();
	    writer.print(jsonString);
	}
	@Reference CouponStatusLocalService  _couponStatusLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference EmployeeDirectoryLocalService _employeeDirectoryLocalService;
	 @Reference private ContractLoginLocalService _contractLoginLocalService;
	 @Reference UserLocalService  _userLocalService;
}
