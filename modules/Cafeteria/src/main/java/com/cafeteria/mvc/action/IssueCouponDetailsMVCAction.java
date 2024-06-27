package com.cafeteria.mvc.action;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.constants.CafeteriaWebConstantKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.plng.common.schema.model.ContractLogin;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.service.CafeteriaCouponsLocalService;
import com.plng.common.schema.service.CanteenIssueLocalService;
import com.plng.common.schema.service.ContractLoginLocalService;
import com.plng.common.schema.service.CouponLocalService;
import com.plng.common.schema.service.CouponStatusLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
   immediate = true,
   property = {
		   "javax.portlet.name="+CafeteriaPortletKeys.CAFETERIA_ADMIN_LIVE,
		    "mvc.command.name="+CafeteriaPortletKeys.CAFETERIA_ISSUE_COUPON
		   },
   service = {MVCActionCommand.class}
)
public class IssueCouponDetailsMVCAction extends BaseMVCActionCommand {
   private static final Log _log = LogFactoryUtil.getLog(IssueCouponDetailsMVCAction.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
	   _log.info("IssueCouponDetailsMVCAction.doProcessAction()..... ");
	      ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
	      String coupon_id = ParamUtil.getString(actionRequest, "couponNo");
	      long empid = ParamUtil.getLong(actionRequest, "eId");
	      String req_for = ParamUtil.getString(actionRequest, "reqFor");
	      String itemName = ParamUtil.getString(actionRequest, "item");
	      long item_id = ParamUtil.getLong(actionRequest, "itemId");
	      float requested_quant = ParamUtil.getFloat(actionRequest, "ItemQty");
	      float value_quant = ParamUtil.getFloat(actionRequest, "value_quant");
	      Date created = ParamUtil.getDate(actionRequest, "createDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	      _log.info("empid >>>>"+empid);
	      
	      if(empid!=0) {
	    	  _canteenIssueLocalService.saveCanteenIssue(coupon_id, empid, req_for, item_id, requested_quant, value_quant, created);
		      _couponStatusLocalService.updateCoupon(coupon_id);
		      _couponLocalService.couponDeletion(coupon_id);
		      
		      EmployeeDirectory employeeDirectory =null;
		      long userId = 0 ;
		      ContractLogin contEmpByUserId = null;
		        long contEmpuserId=0;
		      try {
				 employeeDirectory = _employeeDirectoryLocalService.getEmployeeDirectory(empid);
				   _log.info("employeeDirectory >>>>"+employeeDirectory);
				   if(Validator.isNotNull(employeeDirectory)) {
					  long empId = employeeDirectory.getUserId();
					   JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
						String message="Your Order With Coupon Code "+coupon_id+" For "+requested_quant+" "+itemName+" Has Been Issued";
						messageJSONObject.put(CafeteriaWebConstantKeys.USER_ID, empId);
						messageJSONObject.put(CafeteriaWebConstantKeys.NOTIFICATION_TEXT, message);
						messageJSONObject.put(CafeteriaWebConstantKeys.TITLE, "Canteen Coupon Issuance.");
						messageJSONObject.put(CafeteriaWebConstantKeys.SENDER_NAME, themeDisplay.getUser().getFullName(true,true));
						messageJSONObject.put(CafeteriaWebConstantKeys.LINK,"/cafeteria");
						String jsonString = messageJSONObject.toString();
						UserNotificationEventLocalServiceUtil.addUserNotificationEvent(empId,"com_cafeteria_CafeteriaPortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,themeDisplay.getUserId(), jsonString, false,new ServiceContext());
				   }
			} catch (Exception e) {
					   _log.info("else  contEmpByUserId >>>>");
					   try {
							 contEmpByUserId = _contractLoginLocalService.getContEmpByUserId(empid);
							 if(Validator.isNotNull(contEmpByUserId)) {
								 contEmpuserId = contEmpByUserId.getLrUserId();
								 JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
									String message="Your Order With Coupon Code "+coupon_id+" For "+requested_quant+" "+itemName+" Has Been Issued";
									messageJSONObject.put(CafeteriaWebConstantKeys.USER_ID, contEmpuserId);
									messageJSONObject.put(CafeteriaWebConstantKeys.NOTIFICATION_TEXT, message);
									messageJSONObject.put(CafeteriaWebConstantKeys.TITLE, "Canteen Coupon Issuance.");
									messageJSONObject.put(CafeteriaWebConstantKeys.SENDER_NAME, themeDisplay.getUser().getFullName(true,true));
									messageJSONObject.put(CafeteriaWebConstantKeys.LINK,"/cafeteria");
									String jsonString = messageJSONObject.toString();
									UserNotificationEventLocalServiceUtil.addUserNotificationEvent(contEmpuserId,"com_cafeteria_CafeteriaPortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,themeDisplay.getUserId(), jsonString, false,new ServiceContext());
							 }
							 
						} catch (Exception e1) {
							 _log.info("inside catch >>>>"+contEmpuserId);
								JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
								String message="Your Order With Coupon Code "+coupon_id+" For "+requested_quant+" "+itemName+" Has Been Issued";
								messageJSONObject.put(CafeteriaWebConstantKeys.USER_ID, empid);
								messageJSONObject.put(CafeteriaWebConstantKeys.NOTIFICATION_TEXT, message);
								messageJSONObject.put(CafeteriaWebConstantKeys.TITLE, "Canteen Coupon Issuance.");
								messageJSONObject.put(CafeteriaWebConstantKeys.SENDER_NAME, themeDisplay.getUser().getFullName(true,true));
								messageJSONObject.put(CafeteriaWebConstantKeys.LINK,"/cafeteria");
								String jsonString = messageJSONObject.toString();
								
								UserNotificationEventLocalServiceUtil.addUserNotificationEvent(empid,"com_cafeteria_CafeteriaPortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,themeDisplay.getUserId(), jsonString, false,new ServiceContext());
						       
						}
			}
	      }
	      
	      String redirectURL = ParamUtil.getString(actionRequest,CafeteriaWebConstantKeys.REDIRECT_URL);
			_log.info("redirectURL :" + redirectURL);
			actionResponse.sendRedirect(redirectURL);
	   }
	 
	   @Reference private CafeteriaCouponsLocalService _cafeteriaCouponsLocalService;
	   
	   @Reference private CanteenIssueLocalService _canteenIssueLocalService;
	   @Reference private CouponStatusLocalService _couponStatusLocalService;
	   @Reference private CouponLocalService _couponLocalService;
	   @Reference EmployeeDirectoryLocalService _employeeDirectoryLocalService;
	   @Reference private ContractLoginLocalService _contractLoginLocalService;
		
	}
