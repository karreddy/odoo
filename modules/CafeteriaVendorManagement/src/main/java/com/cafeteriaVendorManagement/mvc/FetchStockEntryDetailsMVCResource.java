package com.cafeteriaVendorManagement.mvc;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.CafeVendorMaster;
import com.plng.common.schema.model.CafeVendorPurchase;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.CafeVendorMasterLocalService;
import com.plng.common.schema.service.CafeVendorPurchaseLocalService;

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
			"javax.portlet.name=" +CafeteriaVendorManagementPortletKeys.CAFETERIAVENDORSTOCKINVENTORY,
			"mvc.command.name="+CafeteriaVendorManagementPortletKeys.VENDOR_STOCK_ENTRY_DETAILS
		},
		service = MVCResourceCommand.class
	)
public class FetchStockEntryDetailsMVCResource extends BaseMVCResourceCommand  {
	private static final Log _log = LogFactoryUtil.getLog(FetchStockEntryDetailsMVCResource.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("FetchStockEntryDetailsMVCResource.doServeResource()...");
		List<CafeVendorPurchase> cafeVendorPurchases =null;
		CafeItemMaster cafeItemMaster =null;
		CafeVendorMaster cafeVendor=null;
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		Date date = new Date();
			try {
					cafeVendorPurchases= _cafeVendorPurchaseLocalService.getTodaysStock(date, -1, -1);
				for (CafeVendorPurchase cafeVendorPurchase : cafeVendorPurchases) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					long item_id = cafeVendorPurchase.getItem_id();
					long vendor_id = cafeVendorPurchase.getVendor_id();
					 cafeVendor = _cafeVendorMasterLocalService.getCafeVendorMaster(vendor_id);
					 String vendorName = cafeVendor.getVendorName();
					 cafeItemMaster = _cafeItemMasterLocalService.getCafeItemMaster(item_id);
					 String itemDesc = cafeItemMaster.getItemDesc();
					 jsonObject.put("invDate",cafeVendorPurchase.getDate() );
					 jsonObject.put("vendor",vendorName);
					 jsonObject.put("itemDesc", itemDesc);
					 jsonObject.put("qty",cafeVendorPurchase.getQty() );
					 jsonObject.put("unitPrice",cafeVendorPurchase.getUnit_price() );
					 jsonObject.put("amount",cafeVendorPurchase.getAmount() );
					 jsonObject.put("invNo",cafeVendorPurchase.getInvoice_no() );
					 jsonArray.put(jsonObject );
				}
			} catch (Exception e) {
				_log.info("Exception getting Entries >>>"+e.getMessage());
			}
			resourceResponse.getWriter().print(jsonArray);
	}
	 @Reference private CafeVendorPurchaseLocalService _cafeVendorPurchaseLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference private CafeVendorMasterLocalService _cafeVendorMasterLocalService;
}
