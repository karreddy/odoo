package com.cafeteria.mvc.resource;

import com.cafeteria.constants.CafeteriaPortletKeys;
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
import com.plng.common.schema.model.Coupon;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.ContractLoginLocalService;
import com.plng.common.schema.service.CouponLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;

import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +CafeteriaPortletKeys.CAFETERIA_ADMIN_LIVE,
			"mvc.command.name="+CafeteriaPortletKeys.CAFETERIA_ADMIN_COUPONS_DETAILS 
		},
		service = MVCResourceCommand.class
	)
public class FetchAdminCouponsMVCResource extends BaseMVCResourceCommand {
	private static final Log _log = LogFactoryUtil.getLog(FetchAdminCouponsMVCResource.class);
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
	        throws Exception {
	    _log.info("Cafeteria Admin Coupons..");
	    JSONArray clArray = JSONFactoryUtil.createJSONArray();
	    List<Coupon> couponsList = null;
	    CafeItemMaster fetchCafeItem = null;
	    JSONObject jsonObject = null;

	    try {
	        couponsList = _couponLocalService.getAllTodaysCoupons(-1, -1);
	        _log.info("couponsList size>>>" + couponsList.size());

	        for (Coupon coupon : couponsList) {
	            jsonObject = JSONFactoryUtil.createJSONObject();
	            long empid = coupon.getEmpid();
	            _log.info("Processing empid>>>" + empid);

	            try {
	                fetchCafeItem = _cafeItemMasterLocalService.fetchCafeItemMaster(coupon.getItem_id());
	                if (Validator.isNotNull(empid)) {
	                    try {
	                        EmployeeDirectory empdetails = _employeeDirectoryLocalService.getEmployeeDirectory(empid);
	                        if (Validator.isNotNull(empdetails)) {
	                            _log.info("Employee details found for empid>>>" + empid);
	                            jsonObject.put("empid", coupon.getEmpid());
	                            jsonObject.put("itemDesc", fetchCafeItem.getItemDesc());
	                            jsonObject.put("empName", empdetails.getEmployeeName());
	                            jsonObject.put("itemId", coupon.getItem_id());
	                            jsonObject.put("couponId", coupon.getCoupon_id());
	                            jsonObject.put("req_for", coupon.getReq_for());
	                            jsonObject.put("created", coupon.getCreated());
	                            jsonObject.put("requested_quant", coupon.getRequested_quant());
	                            jsonObject.put("value_quant", coupon.getValue_quant());
	                            clArray.put(jsonObject);
	                            continue;
	                        }
	                    } catch (Exception e) {
	                        _log.debug("Employee not found, checking contract employee");
	                    }

	                    try {
	                        ContractLogin contEmpByLrId = _contractLoginLocalService.getContEmpByUserId(empid);
	                        if (Validator.isNotNull(contEmpByLrId)) {
	                            _log.info("Contract employee details found for empid>>>" + empid);
	                            jsonObject.put("empid", coupon.getEmpid());
	                            jsonObject.put("itemDesc", fetchCafeItem.getItemDesc());
	                            jsonObject.put("empName", contEmpByLrId.getName());
	                            jsonObject.put("itemId", coupon.getItem_id());
	                            jsonObject.put("couponId", coupon.getCoupon_id());
	                            jsonObject.put("req_for", coupon.getReq_for());
	                            jsonObject.put("created", coupon.getCreated());
	                            jsonObject.put("requested_quant", coupon.getRequested_quant());
	                            jsonObject.put("value_quant", coupon.getValue_quant());
	                            clArray.put(jsonObject);
	                            continue;
	                        }
	                    } catch (Exception e) {
	                        _log.debug("Contract employee not found, checking user");
	                    }

	                    try {
	                        User user = _userLocalService.getUser(empid);
	                        if (Validator.isNotNull(user)) {
	                            _log.info("User details found for empid>>>" + empid);
	                            jsonObject.put("empid", coupon.getEmpid());
	                            jsonObject.put("itemDesc", fetchCafeItem.getItemDesc());
	                            jsonObject.put("empName", user.getFullName());
	                            jsonObject.put("itemId", coupon.getItem_id());
	                            jsonObject.put("couponId", coupon.getCoupon_id());
	                            jsonObject.put("req_for", coupon.getReq_for());
	                            jsonObject.put("created", coupon.getCreated());
	                            jsonObject.put("requested_quant", coupon.getRequested_quant());
	                            jsonObject.put("value_quant", coupon.getValue_quant());
	                            clArray.put(jsonObject);
	                        }
	                    } catch (Exception e) {
	                        _log.error("Error fetching user details: " + e.getMessage(), e);
	                    }
	                }
	            } catch (Exception e) {
	                _log.error("Error processing coupon for empid " + empid + ": " + e.getMessage(), e);
	            }
	        }
	    } catch (Exception e) {
	        _log.error("Error fetching today's coupons: " + e.getMessage(), e);
	    }

	    String jsonString = clArray.toString();
	    resourceResponse.setContentType("application/json");
	    PrintWriter writer = resourceResponse.getWriter();
	    writer.print(jsonString);
	}
	
	 @Reference private CouponLocalService _couponLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference EmployeeDirectoryLocalService _employeeDirectoryLocalService;
	 @Reference UserLocalService  _userLocalService;
	 @Reference private ContractLoginLocalService _contractLoginLocalService;
		
	 } 
	   

