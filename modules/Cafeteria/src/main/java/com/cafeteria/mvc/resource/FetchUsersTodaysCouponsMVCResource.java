package com.cafeteria.mvc.resource;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.mvc.util.CafeteriaUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +CafeteriaPortletKeys.CAFETERIA,
			"mvc.command.name="+ CafeteriaPortletKeys.CAFETERIA_USERS_COUPONS
		},
		service = MVCResourceCommand.class
	)
public class FetchUsersTodaysCouponsMVCResource extends BaseMVCResourceCommand {
	private static final Log _log = LogFactoryUtil.getLog(FetchUsersTodaysCouponsMVCResource.class);
	@Reference(unbind = "-")
	private CafeteriaUtil cafeteriaUtil;
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId = themeDisplay.getUserId();
		 long employeeId = 0 ;
		  EmployeeDirectory employeeDetailsByUserId =null;
			try {
				 employeeDetailsByUserId = _employeeDirectoryLocalService.getEmployeeDetailsByUserId(userId);
				  employeeId = employeeDetailsByUserId.getEmployeeId();
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
			
			ContractLogin contEmpByUserId = null;
	        long contEmpuserId=0;
			try {
				 contEmpByUserId = _contractLoginLocalService.getContEmpByLrId(userId);
				 contEmpuserId = contEmpByUserId.getUserId();
				 _log.info("contEmpuserId >>>>"+contEmpuserId);
			} catch (Exception e1) {
				_log.error("Exec >>>"+e1.getMessage());
			}
			
		Date date = new Date();
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		
		List<CouponStatus> couponsByUser =null;
		List<CafeItemMaster> cafeItemMasterList=null;
		if(employeeId!=0) {
			try {
				couponsByUser = _couponStatusLocalService.getCouponsByUser(employeeId, date, -1, -1).stream().filter(detail -> detail.getValue_quant() >0).sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
		                .collect(Collectors.toList());
				for (CouponStatus couponStatus : couponsByUser) {
					JSONObject couponObject = JSONFactoryUtil.createJSONObject();
					long item_id = couponStatus.getItem_id();
					cafeItemMasterList=_cafeItemMasterLocalService.getCafeItemMasters(-1, -1);
					String itemNameById = cafeteriaUtil.getItemNameById(item_id, cafeItemMasterList);
					couponObject.put("itemName", itemNameById);
					couponObject.put("qty", couponStatus.getRequested_quant());
					couponObject.put("couponNo", couponStatus.getCoupon_id());
					couponObject.put("status", couponStatus.getStatus());
					jsonArray.put(couponObject);
				}
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
		}else if(contEmpuserId!=0) {
			couponsByUser = _couponStatusLocalService.getCouponsByUser(contEmpuserId, date, -1, -1).stream().filter(detail -> detail.getValue_quant() >0).sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
	                .collect(Collectors.toList());
			for (CouponStatus couponStatus : couponsByUser) {
				JSONObject couponObject = JSONFactoryUtil.createJSONObject();
				long item_id = couponStatus.getItem_id();
				cafeItemMasterList=_cafeItemMasterLocalService.getCafeItemMasters(-1, -1);
				String itemNameById = cafeteriaUtil.getItemNameById(item_id, cafeItemMasterList);
				couponObject.put("itemName", itemNameById);
				couponObject.put("qty", couponStatus.getRequested_quant());
				couponObject.put("couponNo", couponStatus.getCoupon_id());
				couponObject.put("status", couponStatus.getStatus());
				jsonArray.put(couponObject);
			}
		}
		else{
			couponsByUser = _couponStatusLocalService.getCouponsByUser(userId, date, -1, -1).stream().filter(detail -> detail.getValue_quant() >0).sorted(Comparator.comparing(CouponStatus::getCreated).reversed())
	                .collect(Collectors.toList());
			for (CouponStatus couponStatus : couponsByUser) {
				JSONObject couponObject = JSONFactoryUtil.createJSONObject();
				long item_id = couponStatus.getItem_id();
				cafeItemMasterList=_cafeItemMasterLocalService.getCafeItemMasters(-1, -1);
				String itemNameById = cafeteriaUtil.getItemNameById(item_id, cafeItemMasterList);
				couponObject.put("itemName", itemNameById);
				couponObject.put("qty", couponStatus.getRequested_quant());
				couponObject.put("couponNo", couponStatus.getCoupon_id());
				couponObject.put("status", couponStatus.getStatus());
				jsonArray.put(couponObject);
			}
		}
		resourceResponse.getWriter().print(jsonArray); 
	}

	@Reference private CouponStatusLocalService _couponStatusLocalService;
	 @Reference EmployeeDirectoryLocalService _employeeDirectoryLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference private ContractLoginLocalService _contractLoginLocalService;
	 
}
