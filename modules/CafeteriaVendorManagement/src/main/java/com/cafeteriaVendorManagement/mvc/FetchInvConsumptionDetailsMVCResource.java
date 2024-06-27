package com.cafeteriaVendorManagement.mvc;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.plng.common.schema.model.CafeInvConsumption;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.service.CafeInvConsumptionLocalService;
import com.plng.common.schema.service.CafeItemMasterLocalService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +CafeteriaVendorManagementPortletKeys.CAFETERIACONSUMPTION,
			"mvc.command.name="+CafeteriaVendorManagementPortletKeys.INV_CONSUMPTION_DETAILS
		},
		service = MVCResourceCommand.class
	)
public class FetchInvConsumptionDetailsMVCResource extends BaseMVCResourceCommand  {
	private static final Log _log = LogFactoryUtil.getLog(FetchInvConsumptionDetailsMVCResource.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		List<CafeInvConsumption> cafeInvConsumptionList =null;
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();
		CafeItemMaster itemMaster =null;
		JSONObject invConsumptionObj =null;
		try {
			cafeInvConsumptionList = _cafeInvConsumptionLocalService.getCafeInvConsumptions(-1, -1).stream().sorted(Comparator.comparing(CafeInvConsumption::getPrimaryKey).reversed()).collect(Collectors.toList());
			for (CafeInvConsumption cafeInvConsumption : cafeInvConsumptionList) {
				 invConsumptionObj = JSONFactoryUtil.createJSONObject();
				 itemMaster = _cafeItemMasterLocalService.getCafeItemMaster(cafeInvConsumption.getItem());
				 
				  invConsumptionObj.put("date", cafeInvConsumption.getDate());
				  invConsumptionObj.put("quantity", cafeInvConsumption.getQuantity());
				  invConsumptionObj.put("reason", cafeInvConsumption.getReason());
				  invConsumptionObj.put("item",itemMaster.getItemDesc() );
				  jsonArray.put(invConsumptionObj);
			}
		} catch (Exception e) {
			_log.error("Exception getting Entries >>>"+e.getMessage());
		}
			
    	resourceResponse.getWriter().print(jsonArray);
	}
	 @Reference private CafeInvConsumptionLocalService _cafeInvConsumptionLocalService;
	 @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
		
}