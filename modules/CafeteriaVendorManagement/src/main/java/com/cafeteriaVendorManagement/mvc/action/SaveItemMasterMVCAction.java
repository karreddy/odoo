package com.cafeteriaVendorManagement.mvc.action;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementWebConstantKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.plng.common.schema.service.CafeItemMasterLocalService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
   immediate = true,
   property = {
		   "javax.portlet.name="+CafeteriaVendorManagementPortletKeys.CAFETERIAITEMMASTERMANAGEMENT,
		    "mvc.command.name="+CafeteriaVendorManagementPortletKeys.ITEM_MASTER
		   },
   service = {MVCActionCommand.class}
)
public class SaveItemMasterMVCAction extends BaseMVCActionCommand {
   private static final Log _log = LogFactoryUtil.getLog(SaveItemMasterMVCAction.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
	   _log.info("SaveItemMasterMVCAction.doProcessAction()..... ");
	      String itemDesc = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.ITEM_DESC);
	      String itemUnits = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.ITEM_UNITS);
	      String redirectURL = ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.REDIRECT_URL);
			_log.info("redirectURL :" + redirectURL);
	     
		    if(!itemDesc.isEmpty()&& !itemUnits.isEmpty()) {
		    	 _cafeItemMasterLocalService.addCafeItemMaster(itemDesc, itemUnits);
		    	 actionResponse.sendRedirect(redirectURL);
			 }
			else {
			      SessionErrors.add(actionRequest, "vendor-details-empty");
			}		
   }
   
  @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;


}
