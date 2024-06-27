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
import com.plng.common.schema.model.CanteenInventory;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.CanteenInventoryLocalService;

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
			"javax.portlet.name=" +CafeteriaVendorManagementPortletKeys.CAFETERIAINVENTORY,
			"mvc.command.name="+CafeteriaVendorManagementPortletKeys.PANTRY_OFFICE_INVENTORY_DETAILS
		},
		service = MVCResourceCommand.class
	)
public class FetchInventoryDetailsMVCResource extends BaseMVCResourceCommand  {
	private static final Log _log = LogFactoryUtil.getLog(FetchInventoryDetailsMVCResource.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("Fetch  Inventory  Details ...");
		
		List<CanteenInventory> todaysStock =null;
		CafeItemMaster cafeItemMaster =null;
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		Date date = new Date();
			try {
					todaysStock = _canteenInventoryLocalService.getTodaysStock(date, -1, -1)
							.stream()
							.filter(detail -> detail.getQuantity() > 0)
							.sorted(Comparator.comparing(CanteenInventory::getPrimaryKey).reversed())
			                .collect(Collectors.toList());
				for (CanteenInventory cafeVendorPurchase : todaysStock) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
					long item_id = cafeVendorPurchase.getItem_id();
					 cafeItemMaster = _cafeItemMasterLocalService.getCafeItemMaster(item_id);
					 String itemDesc = cafeItemMaster.getItemDesc();
					 jsonObject.put("date",cafeVendorPurchase.getDate() );
					 jsonObject.put("itemDesc", itemDesc);
					 jsonObject.put("qty",cafeVendorPurchase.getQuantity() );
					 jsonObject.put("unitPrice",cafeVendorPurchase.getUnit_price() );
					 jsonArray.put(jsonObject );
				}
			} catch (Exception e) {
				_log.info("Exception getting Entries >>>"+e.getMessage());
			}
		resourceResponse.getWriter().print(jsonArray);
	}
	
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference private CanteenInventoryLocalService _canteenInventoryLocalService;
}
