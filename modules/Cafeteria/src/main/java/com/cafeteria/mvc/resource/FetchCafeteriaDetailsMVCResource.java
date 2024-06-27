package com.cafeteria.mvc.resource;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.constants.CafeteriaWebConstantKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.ContractLogin;
import com.plng.common.schema.model.CouponStatus;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.ContractLoginLocalService;
import com.plng.common.schema.service.CouponStatusLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;

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
			"javax.portlet.name=" +CafeteriaPortletKeys.CAFETERIA_REPORT,
			"mvc.command.name="+ CafeteriaPortletKeys.CAFETERIA_REPORT_MONTHLY_DETAILS
		},
		service = MVCResourceCommand.class
	)
public class FetchCafeteriaDetailsMVCResource extends BaseMVCResourceCommand {
	private static final Log _log = LogFactoryUtil.getLog(FetchCafeteriaDetailsMVCResource.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("FetchCafeteriaDetailsMVCResource.doServeResource() ..");
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId = themeDisplay.getUserId();
		ContractLogin contEmpByUserId;
		long contEmpuserId=0;
		try {
			contEmpByUserId = _contractLoginLocalService.getContEmpByLrId(userId);
			 contEmpuserId = contEmpByUserId.getUserId();
			 _log.info("contEmpuserId >>>>"+contEmpuserId);
		} catch (Exception e1) {
			_log.error("Exec >>>"+e1.getMessage());
		}
		
		_log.info("userId  >>>>"+userId);
		long employeeId = 0 ;
		  EmployeeDirectory employeeDetailsByUserId =null;
			try {
				 employeeDetailsByUserId = _employeeDirectoryLocalService.getEmployeeDetailsByUserId(userId);
				  employeeId = employeeDetailsByUserId.getEmployeeId();
				  _log.info("employeeId  >>>>"+employeeId);
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
			
			
			
		int selMonth = ParamUtil.getInteger(resourceRequest, CafeteriaWebConstantKeys.SELECTED_MONTH);
		int selYear = ParamUtil.getInteger(resourceRequest,CafeteriaWebConstantKeys.SELECTED_YEAR);
		  _log.info("selMonth  >>>>"+selMonth+"    selYear  >>>>"+selYear);
		List<CouponStatus> monthlyDetails =  null;
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		 CafeItemMaster cafeItemMaster =null;
		 if(employeeId!=0) {
			 try {
				 monthlyDetails = _couponStatusLocalService.getMonthlyDetails(employeeId, selMonth, selYear).stream().filter(detail ->"Issued".equals(detail.getStatus())).sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
			                .collect(Collectors.toList());
				 for (CouponStatus couponStatus : monthlyDetails) {
					 JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					 
					 long item_id = couponStatus.getItem_id();
					 cafeItemMaster=_cafeItemMasterLocalService.getCafeItemMaster(item_id);
					 String itemDesc = cafeItemMaster.getItemDesc();
					 jsonObject.put("date", couponStatus.getCreated());
					 jsonObject.put("reqFor", couponStatus.getReq_for());
					 jsonObject.put("qty", couponStatus.getRequested_quant());
					 jsonObject.put("itemName", itemDesc);
					 jsonObject.put("value",couponStatus.getValue_quant() );
					 jsonArray.put(jsonObject);
				}
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		 }else if(contEmpuserId!=0){
			 try {
				 monthlyDetails = _couponStatusLocalService.getMonthlyDetails(contEmpuserId, selMonth, selYear).stream().filter(detail -> "Issued".equals(detail.getStatus())).sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
			                .collect(Collectors.toList());
				 for (CouponStatus couponStatus : monthlyDetails) {
					 JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					 
					 long item_id = couponStatus.getItem_id();
					 cafeItemMaster=_cafeItemMasterLocalService.getCafeItemMaster(item_id);
					 String itemDesc = cafeItemMaster.getItemDesc();
					 jsonObject.put("date", couponStatus.getCreated());
					 jsonObject.put("reqFor", couponStatus.getReq_for());
					 jsonObject.put("qty", couponStatus.getRequested_quant());
					 jsonObject.put("itemName", itemDesc);
					 jsonObject.put("value",couponStatus.getValue_quant() );
					 jsonArray.put(jsonObject);
				}
			} catch (Exception e) {
				_log.error(e.getMessage());
			}
		 }else {
			 
			 try {
			 monthlyDetails = _couponStatusLocalService.getMonthlyDetails(userId, selMonth, selYear).stream().filter(detail -> "Issued".equals(detail.getStatus())).sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
		                .collect(Collectors.toList());
			 for (CouponStatus couponStatus : monthlyDetails) {
				 JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
				 
				 long item_id = couponStatus.getItem_id();
				 cafeItemMaster=_cafeItemMasterLocalService.getCafeItemMaster(item_id);
				 String itemDesc = cafeItemMaster.getItemDesc();
				 jsonObject.put("date", couponStatus.getCreated());
				 jsonObject.put("reqFor", couponStatus.getReq_for());
				 jsonObject.put("qty", couponStatus.getRequested_quant());
				 jsonObject.put("itemName", itemDesc);
				 jsonObject.put("value",couponStatus.getValue_quant() );
				 jsonArray.put(jsonObject);
			}
		} catch (Exception e) {
			_log.error(e.getMessage());
		} 
		 }
		
		resourceResponse.getWriter().print(jsonArray); 
	}
	 @Reference private CouponStatusLocalService _couponStatusLocalService;
	 @Reference EmployeeDirectoryLocalService _employeeDirectoryLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference private ContractLoginLocalService _contractLoginLocalService;
	   
}
