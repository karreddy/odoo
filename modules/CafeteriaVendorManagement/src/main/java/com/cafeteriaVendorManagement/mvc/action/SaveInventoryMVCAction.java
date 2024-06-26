package com.cafeteriaVendorManagement.mvc.action;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.plng.common.schema.service.CafMdpInventoryLocalService;
import com.plng.common.schema.service.CafStoreInventoryLocalService;
import com.plng.common.schema.service.CanteenInventoryLocalService;
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
		   "javax.portlet.name="+CafeteriaVendorManagementPortletKeys.CAFETERIAINVENTORY,
		    "mvc.command.name="+CafeteriaVendorManagementPortletKeys.CAFETERIA_INVENTORY
		   },
   service = {MVCActionCommand.class}
)
public class SaveInventoryMVCAction extends BaseMVCActionCommand {
   private static final Log _log = LogFactoryUtil.getLog(SaveInventoryMVCAction.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
	   _log.info("SaveInventoryMVCAction.doProcessAction()..... ");
	   Date date = ParamUtil.getDate(actionRequest, "date",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	      long inventory = ParamUtil.getLong(actionRequest, "inventory");
	      long itemId = ParamUtil.getLong(actionRequest, "item");
	      float quantity = ParamUtil.getLong(actionRequest, "Qty");
	      float unitPrice = ParamUtil.getLong(actionRequest, "unitPrice");
	      boolean OneItemB = ParamUtil.getBoolean(actionRequest, "OneItemB"); 
	      boolean res_guest = unitPrice > 100 ;
	      
	      if (inventory==1) {
	    	  try {
				_canteenInventoryLocalService.saveMdpInventory(date, itemId, quantity, unitPrice, date, 0, res_guest, OneItemB);
				  _inventoryQtyLocalService.saveInventoryQty(itemId, quantity);
				  _cafStoreInventoryLocalService.deductStoreInventory(itemId, quantity);
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
	      }else {
	    	  try {
				_cafMdpInventoryLocalService.saveMdpInventory(date,inventory, itemId, quantity, unitPrice);
				  _cafStoreInventoryLocalService.deductStoreInventory(itemId, quantity);
			} catch (Exception e) {
				_log.debug(e.getMessage());
			}
	      }
	      
	      String redirectURL = ParamUtil.getString(actionRequest, "redirectURL");
			_log.info("redirectURL :" + redirectURL);
			
	      actionResponse.sendRedirect(redirectURL);
	   }
   
 
 @Reference private InventoryQtyLocalService _inventoryQtyLocalService;
 @Reference private CafMdpInventoryLocalService _cafMdpInventoryLocalService;
 @Reference private CanteenInventoryLocalService _canteenInventoryLocalService;
 @Reference private CafStoreInventoryLocalService _cafStoreInventoryLocalService;
}
