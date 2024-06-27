package com.cafeteria.mvc.action;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.constants.CafeteriaWebConstantKeys;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.ContractLogin;
import com.plng.common.schema.model.Coupon;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.ContractLoginLocalService;
import com.plng.common.schema.service.CouponLocalService;
import com.plng.common.schema.service.CouponStatusLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;
import com.plng.common.schema.service.InventoryQtyLocalService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
   immediate = true,
   property = {
		   "javax.portlet.name="+CafeteriaPortletKeys.CAFETERIA,
		    "mvc.command.name="+CafeteriaPortletKeys.CAFETERIA_REPORT_DETAILS
		   },
   service = {MVCActionCommand.class}
)
public class SaveCafeteriaCouponDetails extends BaseMVCActionCommand {
   private static final Log _log = LogFactoryUtil.getLog(SaveCafeteriaCouponDetails.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
	      _log.info("SaveCafeteriaCouponDetails.doProcessAction()..... ");
	      ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	      long userId = ParamUtil.getLong(actionRequest,CafeteriaWebConstantKeys.EMP_ID);
	      _log.info("userId..... "+userId );
	      long employeeId = 0 ;
	      String location = null;
	      EmployeeDirectory employeeDetailsByUserId =null;
			try {
				 employeeDetailsByUserId = _employeeDirectoryLocalService.getEmployeeDetailsByUserId(userId);
				  employeeId = employeeDetailsByUserId.getEmployeeId();
				   location = employeeDetailsByUserId.getLocation();
				  
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
			
			Date date = ParamUtil.getDate(actionRequest, CafeteriaWebConstantKeys.DATE ,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int monthInt = calendar.get(Calendar.MONTH) + 1;
			String month = String.format("%02d", monthInt);
	      String eName = ParamUtil.getString(actionRequest, CafeteriaWebConstantKeys.EMP_NAME);
	      String requestFor = ParamUtil.getString(actionRequest, CafeteriaWebConstantKeys.REQUEST_FOR);
	      int item = ParamUtil.getInteger(actionRequest, CafeteriaWebConstantKeys.ITEM_ID);
	      long availQty = ParamUtil.getLong(actionRequest, CafeteriaWebConstantKeys.AVAILABLE_QTY);
	      float reqQty = ParamUtil.getFloat(actionRequest, CafeteriaWebConstantKeys.RQ);
	       float itemVal = ParamUtil.getFloat(actionRequest, CafeteriaWebConstantKeys.ITEM_VAL);
	       float totalAmount = ParamUtil.getFloat(actionRequest, "totalAmount");
	       _log.info("totalAmount..... "+totalAmount );
		  long companyId = CompanyLocalServiceUtil.getCompanyIdByUserId(userId);
		  Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);
	      long groupId = group.getGroupId();
	      String redirectURL = ParamUtil.getString(actionRequest, CafeteriaWebConstantKeys.REDIRECT_URL);
	    
		    int startCid = 1000;
			long cId = startCid+counterLocalService.increment(Coupon.class.getName());
			String couponNo =null;
			if(employeeId!=0) {
				 couponNo = generateCouponNumber(employeeId, requestFor,cId);
			}else if(contEmpuserId!=0) {
				couponNo = generateCouponNumber(contEmpuserId, requestFor,cId);
			}
			else {
				couponNo = generateCouponNumber(userId, requestFor,cId);
			}
			
			if (totalAmount<3000 && Validator.isNotNull(eName) &&  Validator.isNotNull(itemVal) && employeeId!=0) {
			_couponStatusLocalService.savecouponStatus(employeeId, cId, requestFor, item, reqQty, itemVal, month, couponNo);
			_couponLocalService.saveCoupon(employeeId, cId, requestFor, item, reqQty, itemVal, month, couponNo);
			_inventoryQtyLocalService.deductInventoryQty(item, reqQty);
			String itemDesc = null;
			try {
				CafeItemMaster fetchCafeItemMaster = _cafeItemMasterLocalService.fetchCafeItemMaster(item);
				itemDesc = fetchCafeItemMaster.getItemDesc();
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
			
			
	    	JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
	  		String message="Order for "+(int)reqQty+" "+itemDesc+" has been placed ";
	  		messageJSONObject.put(CafeteriaWebConstantKeys.LINK, userId);
	  		messageJSONObject.put(CafeteriaWebConstantKeys.NOTIFICATION_TEXT, message);
	  		messageJSONObject.put(CafeteriaWebConstantKeys.TITLE, "Canteen Coupon Issuance.");
	  		messageJSONObject.put(CafeteriaWebConstantKeys.SENDER_NAME, themeDisplay.getUser().getFullName(true,true));
	  		messageJSONObject.put(CafeteriaWebConstantKeys.LINK,"/cafeteria-admin");
	  		String jsonString = messageJSONObject.toString();
	  		List<User> userByRole =null;
	  		long cafeAdminId = 0;
	  		try {
				 userByRole = getUserByRole(themeDisplay.getCompanyId(), "Cafeteria Admin");
				for (User user : userByRole) {
					cafeAdminId = user.getUserId();
					_log.info(">>>>>>"+cafeAdminId);
					try {
						UserNotificationEventLocalServiceUtil.addUserNotificationEvent(cafeAdminId,"com_cafeteria_CafeteriaAdminLivePortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,themeDisplay.getUserId(), jsonString, false,new ServiceContext());
					} catch (Exception e) {
						_log.debug(e.getMessage());
					}
				}
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
			}else if(totalAmount<3000 && Validator.isNotNull(eName) &&  Validator.isNotNull(itemVal) && contEmpuserId!=0) {
				_couponStatusLocalService.savecouponStatus(contEmpuserId, cId, requestFor, item, reqQty, itemVal, month, couponNo);
				_couponLocalService.saveCoupon(contEmpuserId, cId, requestFor, item, reqQty, itemVal, month, couponNo);
				_inventoryQtyLocalService.deductInventoryQty(item, reqQty);
				
				String itemDesc = null;
				try {
					CafeItemMaster fetchCafeItemMaster = _cafeItemMasterLocalService.fetchCafeItemMaster(item);
					itemDesc = fetchCafeItemMaster.getItemDesc();
				} catch (Exception e) {
					_log.debug(e.getMessage());
				}
				
		    	JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
		  		
		    	String message="Order for "+(int)reqQty+" "+itemDesc+" has been placed ";
		  		messageJSONObject.put(CafeteriaWebConstantKeys.LINK, userId);
		  		messageJSONObject.put(CafeteriaWebConstantKeys.NOTIFICATION_TEXT, message);
		  		messageJSONObject.put(CafeteriaWebConstantKeys.TITLE, "Canteen Coupon Issuance.");
		  		messageJSONObject.put(CafeteriaWebConstantKeys.SENDER_NAME, themeDisplay.getUser().getFullName(true,true));
		  		messageJSONObject.put(CafeteriaWebConstantKeys.LINK,"/cafeteria-admin");
		  		String jsonString = messageJSONObject.toString();
		  		List<User> userByRole =null;
		  		long cafeAdminId = 0;
		  		try {
					 userByRole = getUserByRole(themeDisplay.getCompanyId(), "Cafeteria Admin");
					for (User user : userByRole) {
						cafeAdminId = user.getUserId();
						_log.info(">>>>>>"+cafeAdminId);
						try {
							_log.info("inside try >>>>>>"+cafeAdminId);
							_log.info("inside try  >>>>>>"+userId);
							UserNotificationEventLocalServiceUtil.addUserNotificationEvent(cafeAdminId,"com_cafeteria_CafeteriaAdminLivePortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,userId, jsonString, false,new ServiceContext());
						} catch (Exception e) {
							_log.error(e.getMessage());
						}
					}
				} catch (Exception e) {
					_log.debug(e.getMessage());
				}	
			}else if(totalAmount<3000 && Validator.isNotNull(eName) &&  Validator.isNotNull(itemVal) && userId!=0) {
				_log.info("else if userId >>>"+userId);
				_couponStatusLocalService.savecouponStatus(userId, cId, requestFor, item, reqQty, itemVal, month, couponNo);
				_couponLocalService.saveCoupon(userId, cId, requestFor, item, reqQty, itemVal, month, couponNo);
				_inventoryQtyLocalService.deductInventoryQty(item, reqQty);
				
				String itemDesc = null;
				try {
					CafeItemMaster fetchCafeItemMaster = _cafeItemMasterLocalService.fetchCafeItemMaster(item);
					itemDesc = fetchCafeItemMaster.getItemDesc();
				} catch (Exception e) {
					_log.debug(e.getMessage());
				}
				
		    	JSONObject messageJSONObject = JSONFactoryUtil.createJSONObject();
		  		
		    	String message="Order for "+(int)reqQty+" "+itemDesc+" has been placed ";
		  		messageJSONObject.put(CafeteriaWebConstantKeys.LINK, userId);
		  		messageJSONObject.put(CafeteriaWebConstantKeys.NOTIFICATION_TEXT, message);
		  		messageJSONObject.put(CafeteriaWebConstantKeys.TITLE, "Canteen Coupon Issuance.");
		  		messageJSONObject.put(CafeteriaWebConstantKeys.SENDER_NAME, themeDisplay.getUser().getFullName(true,true));
		  		messageJSONObject.put(CafeteriaWebConstantKeys.LINK,"/cafeteria-admin");
		  		String jsonString = messageJSONObject.toString();
		  		List<User> userByRole =null;
		  		long cafeAdminId = 0;
		  		try {
					 userByRole = getUserByRole(themeDisplay.getCompanyId(), "Cafeteria Admin");
					for (User user : userByRole) {
						cafeAdminId = user.getUserId();
						_log.info(">>>>>>"+cafeAdminId);
						try {
							_log.info("inside try >>>>>>"+cafeAdminId);
							_log.info("inside try  >>>>>>"+userId);
							UserNotificationEventLocalServiceUtil.addUserNotificationEvent(cafeAdminId,"com_cafeteria_CafeteriaAdminLivePortlet", (new Date()).getTime(),UserNotificationDeliveryConstants.TYPE_WEBSITE,userId, jsonString, false,new ServiceContext());
						} catch (Exception e) {
							_log.error(e.getMessage());
						}
					}
				} catch (Exception e) {
					_log.debug(e.getMessage());
				}	
			}
			else {
			SessionErrors.add(actionRequest,CafeteriaWebConstantKeys.EMPTY_DETAILS );
			}		
			actionResponse.sendRedirect(redirectURL);
   }
	   
		public List<User> getUserByRole(long companyId, String roleName) {
			Role role = null;
			try {
				role = _roleLocalService.getRole(companyId, roleName);
			} catch (PortalException e) {
				_log.debug("Exception on fetching data : "+e.getMessage());
			}
			if(Validator.isNotNull(role)) {
				return _userLocalService.getRoleUsers(role.getRoleId());
			}
			return Collections.emptyList();
		}  
		
		private String generateCouponNumber(long userId, String reqFor,long cId) {
			//coupon id format 837SB1069 
			//-->837-userID   S-reqFor B-Month 1069-Cid
			Date date1= new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(date1);
	        String[] dateParts = dateString.split("-");
		    int monthIndex = Integer.parseInt(dateParts[1]) - 1;
		    String[] months = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
		    String month = months[monthIndex];
		   
		    String couponNo = userId + reqFor + month + cId;

		    return couponNo;
		}
	
	 @Reference private RoleLocalService _roleLocalService;
	 @Reference private UserLocalService _userLocalService;
	 @Reference private CounterLocalService counterLocalService;
	 @Reference private InventoryQtyLocalService _inventoryQtyLocalService;
	 @Reference private CouponStatusLocalService _couponStatusLocalService;
	 @Reference private CouponLocalService _couponLocalService;
	 @Reference EmployeeDirectoryLocalService _employeeDirectoryLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference private ContractLoginLocalService _contractLoginLocalService;
	 
}
