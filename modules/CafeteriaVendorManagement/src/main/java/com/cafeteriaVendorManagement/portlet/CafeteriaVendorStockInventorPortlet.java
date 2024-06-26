package com.cafeteriaVendorManagement.portlet;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.plng.common.schema.model.CafStoreInventory;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.CafeVendorMaster;
import com.plng.common.schema.service.CafStoreInventoryLocalService;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.CafeVendorMasterLocalService;
import com.plng.common.schema.service.CafeVendorPurchaseLocalService;

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
		"javax.portlet.display-name=CafeteriaVendorStockInventory",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe_stock.js",
		"javax.portlet.init-param.view-template=/caf_vendor_stock_inventory/view.jsp",
		"javax.portlet.name=" + CafeteriaVendorManagementPortletKeys.CAFETERIAVENDORSTOCKINVENTORY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaVendorStockInventorPortlet extends MVCPortlet {
	
	 private static final Log _log = LogFactoryUtil.getLog(CafeteriaVendorStockInventorPortlet.class);
	
	@Override
		public void render(RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {
		_log.info("CafeteriaVendorStockInventorPortlet -- serveResource >>>>>>>>>>>>");

		List<CafeVendorMaster> cafeVendorMastersList =null;
		try {
			cafeVendorMastersList = _cafeVendorMasterLocalService.getCafeVendorMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS).stream().sorted(Comparator.comparing(CafeVendorMaster::getCreateDate).reversed()).collect(Collectors.toList());
			renderRequest.setAttribute("cafeVendorMastersList", cafeVendorMastersList);
		} catch (Exception e) {
			_log.debug("Exception Fetching vendors  List"+e.getMessage());
		}
		
		List<CafeItemMaster> cafeItemMasterList =null;
		try {
			cafeItemMasterList = _cafeItemMasterLocalService.getCafeItemMasters(QueryUtil.ALL_POS, QueryUtil.ALL_POS)
					.stream()
					.filter(item -> item.getStatus() == 1)
					.sorted(Comparator.comparing(CafeItemMaster::getPrimaryKey).reversed())
					.collect(Collectors.toList());
			renderRequest.setAttribute("cafeItemMasterList", cafeItemMasterList);
		} catch (Exception e) {
			_log.debug("Exception Fetching Item List >>>"+e.getMessage());
		}
		
		List<CafStoreInventory> cafStoreInventories =null;
		try {
			 cafStoreInventories = _cafStoreInventoryLocalService.getCafStoreInventories(-1, -1);
		} catch (Exception e) {
			_log.debug("Exception Fetching cafStoreInventories List >>>"+e.getMessage());
		}
		renderRequest.setAttribute("cafeStockEntriesByDateList", cafStoreInventories);
		
			super.render(renderRequest, renderResponse);
		}
	 
	 @Reference private CafeVendorMasterLocalService _cafeVendorMasterLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService; 
	 
	 @Reference private CafStoreInventoryLocalService _cafStoreInventoryLocalService;
	 @Reference private CafeVendorPurchaseLocalService _cafeVendorPurchaseLocalService;
}



