package com.cafeteriaVendorManagement.mvc.action;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementWebConstantKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.plng.common.schema.service.CafeInvConsumptionLocalService;
import com.plng.common.schema.service.CafeInventoryLocalService;
import com.plng.common.schema.service.InventoryQtyLocalService;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
   immediate = true,
   property = {
		   "javax.portlet.name="+CafeteriaVendorManagementPortletKeys.CAFETERIACONSUMPTION,
		    "mvc.command.name="+CafeteriaVendorManagementPortletKeys.CAFETERIA_INVENTORY_CONSUMPTION
		   },
   service = {MVCActionCommand.class}
)
public class SaveCafeteriaConsumption extends BaseMVCActionCommand {
  
	private static final Log _log = LogFactoryUtil.getLog(SaveCafeteriaConsumption.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
      _log.info("SaveCafeteriaConsumption.doProcessAction()..... ");
      
      Date date = ParamUtil.getDate(actionRequest,CafeteriaVendorManagementWebConstantKeys.DATE,new SimpleDateFormat(CafeteriaVendorManagementWebConstantKeys.DATE_FORMAT_YYYY_MM_DD));
      long itemId=ParamUtil.getLong(actionRequest, CafeteriaVendorManagementWebConstantKeys.ITEM);
      long invDisplay = ParamUtil.getLong(actionRequest, CafeteriaVendorManagementWebConstantKeys.INVENTORY_DISPLAY);
      float quantity = ParamUtil.getFloat(actionRequest, CafeteriaVendorManagementWebConstantKeys.QTY);
      String reason=ParamUtil.getString(actionRequest, CafeteriaVendorManagementWebConstantKeys.REASON);
    
      String redirectURL = ParamUtil.getString(actionRequest,CafeteriaVendorManagementWebConstantKeys.REDIRECT_URL);
		_log.info("redirectURL :" + redirectURL);
      if( !reason.isEmpty()) {
    	  _cafeInvConsumptionLocalService.addInventoryConsumption(date, itemId, invDisplay, quantity, reason);
    	  _inventoryQtyLocalService.deductInventoryQty(itemId, quantity);
		 }
	    else {
	    	SessionErrors.add(actionRequest, "cons-details-empty");
	       }
 
      actionResponse.sendRedirect(redirectURL);
   }
   
  @Reference private CafeInvConsumptionLocalService _cafeInvConsumptionLocalService;
  @Reference private CafeInventoryLocalService  _cafeInventoryLocalService;
  @Reference private InventoryQtyLocalService _inventoryQtyLocalService;
}
