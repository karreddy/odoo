package com.cafeteriaVendorManagement.portlet;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.plng.common.schema.model.CafStoreInventory;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.CafeVendorPurchase;
import com.plng.common.schema.service.CafStoreInventoryLocalService;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.CafeItemMasterLocalServiceUtil;
import com.plng.common.schema.service.CafeVendorPurchaseLocalService;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author USER
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=pll",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CafeteriaInventory",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe_inv.js",
		"javax.portlet.init-param.view-template=/caf_inventory/view.jsp",
		"javax.portlet.name=" + CafeteriaVendorManagementPortletKeys.CAFETERIAINVENTORY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaInventoryPortlet extends MVCPortlet {
	 private static final Log _log = LogFactoryUtil.getLog(CafeteriaInventoryPortlet.class);
	 @Override
	 public void render(RenderRequest renderRequest, RenderResponse renderResponse)
	         throws IOException, PortletException {

	     List<CafStoreInventory> cafStoreInventories = null;
	     List<CafeVendorPurchase> cafeVendorPurchases = null;
	     CafeItemMaster cafeItemMaster = null;
	     try {
	         cafStoreInventories = _cafStoreInventoryLocalService.getCafStoreInventories(-1, -1).stream().sorted(Comparator.comparing(CafStoreInventory::getDate).reversed()).filter(detail -> detail.getQty() > 0).collect(Collectors.toList());

	         cafeVendorPurchases = _cafeVendorPurchaseLocalService.getCafeVendorPurchases(-1, -1);

	         Map<Long, Float> itemIdToUnitPrice = cafeVendorPurchases.stream().collect(Collectors.toMap(CafeVendorPurchase::getItem_id,CafeVendorPurchase::getUnit_price,(u1, u2) -> u2,LinkedHashMap::new));

	         JSONArray itemIdAndNameArray = JSONFactoryUtil.createJSONArray();

	         if (cafStoreInventories != null) {
	             for (CafStoreInventory inventory : cafStoreInventories) {
	                 long itemId = inventory.getItem_id();
	                 float qty = inventory.getQty();
	                 cafeItemMaster = CafeItemMasterLocalServiceUtil.getCafeItemMaster(itemId);
	                 String itemName = cafeItemMaster.getItemDesc();

	                 JSONObject inventoryObject = JSONFactoryUtil.createJSONObject();
	                 
	                 inventoryObject.put("itemId", String.valueOf(itemId));
	                 inventoryObject.put("itemName", itemName);
	                 inventoryObject.put("qty", qty);
	                 inventoryObject.put("unitPrice", itemIdToUnitPrice.getOrDefault(itemId, 0.0f));
	                 itemIdAndNameArray.put(inventoryObject);
	             }
	         }

	         renderRequest.setAttribute("itemIdAndNameArray", itemIdAndNameArray);

	     } catch (Exception e) {
	         _log.debug(e.getMessage());
	     }

	     super.render(renderRequest, renderResponse);
	 }
	
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService; 
	 @Reference private CafStoreInventoryLocalService _cafStoreInventoryLocalService;
	 @Reference private CafeVendorPurchaseLocalService _cafeVendorPurchaseLocalService;
	
}