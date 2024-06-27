package com.cafeteriaVendorManagement.mvc.action;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementWebConstantKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.plng.common.schema.service.CafeVendorMasterLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
   immediate = true,
   property = {
		   "javax.portlet.name="+CafeteriaVendorManagementPortletKeys.CAFETERIAVENDORMANAGEMENT,
		    "mvc.command.name="+CafeteriaVendorManagementPortletKeys.VENDOR_MASTER
		   },
   service = {MVCActionCommand.class}
)
public class SaveVendorMasterMVCAction extends BaseMVCActionCommand {
   private static final Log _log = LogFactoryUtil.getLog(SaveVendorMasterMVCAction.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
	   _log.info("SaveVendorMaster.doProcessAction()..... ");
	      String vendorName = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.VENDOR_NAME);
	      String vendorAddress = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.VENDOR_ADDRESS);
	      String vendorSapId = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.VENDOR_SAP_ID);

		String redirectURL = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.REDIRECT_URL);
		_log.info("redirectURL :" + redirectURL);
			 if(vendorName.isEmpty()&&vendorAddress.isEmpty()) {
				 	SessionErrors.add(actionRequest, "vendor-details-empty");
				    actionResponse.sendRedirect(redirectURL);
			 }else {
				 _cafeVendorMasterLocalService.addCafeteriaVendorMaster(vendorName, vendorAddress,vendorSapId);
				 _log.info("vendorName :" + vendorName + "--vendorAddress:" + vendorAddress );	
		      actionResponse.sendRedirect(redirectURL);
			 }
   }
   @Reference private CafeVendorMasterLocalService _cafeVendorMasterLocalService;
}
