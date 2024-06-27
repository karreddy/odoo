package com.cafeteriaVendorManagement.mvc;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.plng.common.schema.service.CafeSystemEntryLocalService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" +CafeteriaVendorManagementPortletKeys.CAFETERIALIVEINVENTORY,
			"mvc.command.name="+CafeteriaVendorManagementPortletKeys.PANTRY_OFFICE_INVENTORY_DETAILS
		},
		service = MVCResourceCommand.class
	)
public class FetchSystemEntryLiveDetailsMVCResource extends BaseMVCResourceCommand  {
	private static final Log _log = LogFactoryUtil.getLog(FetchSystemEntryLiveDetailsMVCResource.class);
	@Override
	protected void doServeResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		_log.info("FetchSystemEntryDetailsMVCResource ---  doServeResource()   ...");
		
		String inventory = ParamUtil.getString(resourceRequest, "selInventory", null);
		_log.info("inventory >>>"+inventory);
		
		LocalDate today = LocalDate.now();
		Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
		int start=0;
		int end=10;
		
		JSONObject cafeInventoriesByDate = null;
//		if(Validator.isNotNull(inventory)) {
//			try {
//				  cafeInventoriesByDate = _cafeSystemEntryLocalService.getCafeInventoriesByDate(date, start, end,true);
//			} catch (Exception e) {
//				_log.info("Exception getting Entries >>>"+e.getMessage());
//			}
//			cafeInventoriesByDate.put("isSelected", true);
//			cafeInventoriesByDate.put("inventory", inventory);
//		}else {
//			try {
//				  cafeInventoriesByDate = _cafeSystemEntryLocalService.getCafeInventoriesByDate(date, start, end,false);
//			} catch (Exception e) {
//				_log.info("Exception getting Entries >>>"+e.getMessage());
//			}
//			cafeInventoriesByDate.put("isSelected", false);
//		}
		resourceResponse.getWriter().print(cafeInventoriesByDate);
	}
	 @Reference private CafeSystemEntryLocalService _cafeSystemEntryLocalService;
}
