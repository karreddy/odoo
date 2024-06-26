package com.cafeteria.mvc.resource;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.mvc.util.CafeteriaUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.InventoryQty;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.InventoryQtyLocalService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +CafeteriaPortletKeys.CAFETERIA_ADMIN_LIVE,
			"mvc.command.name="+CafeteriaPortletKeys.CAFETERIA_LIVE_MENU
		},
		service = MVCResourceCommand.class
	)
public class FetchLiveInventoryDetailsMVCResource extends BaseMVCResourceCommand  {
	private static final Log _log = LogFactoryUtil.getLog(FetchLiveInventoryDetailsMVCResource.class);
	
	@Reference(unbind = "-")
	private CafeteriaUtil cafeteriaUtil;
	
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("FetchLiveInventoryDetailsMVCResource.doServeResource()...");
	
		List<InventoryQty> inventoryQties = null;
		CafeItemMaster cafeItemMaster =null;
		JSONArray jSONArray = JSONFactoryUtil.createJSONArray();
			try {
				inventoryQties = _inventoryQtyLocalService.getInventoryQties(-1, -1);
				for (InventoryQty inventoryQty : inventoryQties) {
					if (inventoryQty.getQty()>0) {
						JSONObject object = JSONFactoryUtil.createJSONObject();
						float qty = inventoryQty.getQty();
						long item_id = inventoryQty.getItem_id();
						 cafeItemMaster = _cafeItemMasterLocalService.fetchCafeItemMaster(item_id);
						 object.put("itemName", cafeItemMaster.getItemDesc());
						 object.put("itemQty", inventoryQty.getQty());
						 jSONArray.put(object);
					}
				}
			} catch (Exception e) {
				_log.debug("Exception getting Entries >>>"+e.getMessage());
			}
		resourceResponse.getWriter().print(jSONArray);
	}
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
	 @Reference private InventoryQtyLocalService _inventoryQtyLocalService;
}
