package com.cafeteriaVendorManagement.mvc.action;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.plng.common.schema.service.CafStoreInventoryLocalService;
import com.plng.common.schema.service.CafeVendorPurchaseLocalService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
   immediate = true,
   property = {
		   "javax.portlet.name="+CafeteriaVendorManagementPortletKeys.CAFETERIAVENDORSTOCKINVENTORY,
		    "mvc.command.name="+CafeteriaVendorManagementPortletKeys.CAFETERIA_VENDOR_STOCK_INVENTORY
		   },
   service = {MVCActionCommand.class}
)
public class SaveVendorStockEntryMVCAction extends BaseMVCActionCommand {
   private static final Log _log = LogFactoryUtil.getLog(SaveVendorStockEntryMVCAction.class);

   protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
	   _log.info("SaveItemMasterMVCAction.doProcessAction()..... ");
	      long vendorId = ParamUtil.getLong(actionRequest, "vendor");
	      long itemid = ParamUtil.getLong(actionRequest, "item");
	      long quantity = ParamUtil.getLong(actionRequest, "Qty");
	      float unitPrice = ParamUtil.getFloat(actionRequest, "unitPrice");
	      String invoiceNo = ParamUtil.getString(actionRequest, "invcNo");
	//      Date invoiceDate = ParamUtil.getDate(actionRequest, "invcDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	      float totalAmount = ParamUtil.getFloat(actionRequest, "totalAmt");
	      String redirectURL = ParamUtil.getString(actionRequest, "redirectURL");
			_log.info("redirectURL :" + redirectURL);
			 Date invoiceDate = null;
			 String invoiceDateString = ParamUtil.getString(actionRequest, "invcDate");
			 _log.info("redirectURL :" + redirectURL);
			    _log.info("invoiceDateString >>>>>" + invoiceDateString);

			    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    try {
			        invoiceDate = dateFormat1.parse(invoiceDateString);
			        _log.info("Parsed invoiceDate with dateFormat1 >>>>>" + invoiceDate);
			    } catch (ParseException e1) {
			        try {
			            invoiceDate = dateFormat2.parse(invoiceDateString);
			            _log.info("Parsed invoiceDate with dateFormat2 >>>>>" + invoiceDate);
			        } catch (ParseException e2) {
			            _log.error("Error parsing invoice date: " + e2.getMessage());
			            SessionErrors.add(actionRequest, "invalid-date-format");
			            actionResponse.sendRedirect(redirectURL);
			            return;
			        }
			    }
	      if(vendorId!=0&& itemid !=0) {
	    	  try {
				_cafeVendorPurchaseLocalService.saveVendorpurchase(invoiceDate, vendorId, itemid, invoiceNo, quantity, unitPrice, totalAmount);
				  _cafStoreInventoryLocalService.saveStoreInventory(itemid, quantity);
			} catch (Exception e) {
				_log.debug("Error saving data"+e.getMessage());
			}
	      }else {
			 SessionErrors.add(actionRequest, "stock-details-empty");
		 }
      actionResponse.sendRedirect(redirectURL);
   }


 @Reference private CafStoreInventoryLocalService _cafStoreInventoryLocalService;
 @Reference private CafeVendorPurchaseLocalService _cafeVendorPurchaseLocalService;
 
}
