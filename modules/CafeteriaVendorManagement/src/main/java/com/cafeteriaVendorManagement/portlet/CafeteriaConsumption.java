package com.cafeteriaVendorManagement.portlet;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.InventoryQty;
import com.plng.common.schema.service.CafeInventoryLocalService;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.CanteenInventoryLocalService;
import com.plng.common.schema.service.InventoryQtyLocalService;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
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
		"javax.portlet.display-name=CafeteriaConsumption",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/cafeteria_consumption/view.jsp",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe_consumption.js",
		"javax.portlet.name=" + CafeteriaVendorManagementPortletKeys.CAFETERIACONSUMPTION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaConsumption extends MVCPortlet {
	
	 private static final Log _log = LogFactoryUtil.getLog(CafeteriaConsumption.class);

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		  _log.info("CafeteriaConsumption  MVCPortlet >>>");
		
		  JSONArray consArray = JSONFactoryUtil.createJSONArray();
		JSONObject invConsObj = null;
		CafeItemMaster itemMaster =null;
		List<InventoryQty> inventoryQties =null;
		 try {
	            inventoryQties = _inventoryQtyLocalService.getInventoryQties(-1, -1).stream().sorted(Comparator.comparing(InventoryQty::getDate).reversed()).filter(item -> item.getQty() > 0).collect(Collectors.toList());
	            for (InventoryQty inventoryQty : inventoryQties) {
	            	invConsObj = JSONFactoryUtil.createJSONObject();
	                long itemId = inventoryQty.getItem_id();
	                float qty = inventoryQty.getQty();
	                itemMaster= _cafeItemMasterLocalService.getCafeItemMaster(itemId);
	                invConsObj.put("itemName", itemMaster.getItemDesc());
	                invConsObj.put("itemId", itemId);
	                invConsObj.put("qty", qty);
	                consArray.put(invConsObj);
	            }
	            renderRequest.setAttribute("consArray",consArray );
	        } catch (Exception e) {
	            _log.debug("Error processing inventory data: " + e.getMessage());
	        }

		super.render(renderRequest, renderResponse);
	}
	@Reference private CafeInventoryLocalService _cafeInventoryLocalService;
	@Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	
	 @Reference private InventoryQtyLocalService _inventoryQtyLocalService;
	 @Reference private CanteenInventoryLocalService _canteenInventoryLocalService;
}