package com.cafeteriaVendorManagement.portlet;

import com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.plng.common.schema.model.CafeVendorMaster;
import com.plng.common.schema.service.CafeVendorMasterLocalService;

import java.io.IOException;
import java.util.List;

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
		"javax.portlet.display-name=CafeteriaVendorManagement",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe_vendor.js",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"javax.portlet.init-param.view-template=/caf_vendor_master/view.jsp",
		"javax.portlet.name=" + CafeteriaVendorManagementPortletKeys.CAFETERIAVENDORMANAGEMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaVendorManagementPortlet extends MVCPortlet {
	private static final Log _log = LogFactoryUtil.getLog(CafeteriaVendorManagementPortlet.class);
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		List<CafeVendorMaster> cafeVendorMasterDetails =null;
		try {
			cafeVendorMasterDetails=_cafeVendorMasterLocalService.getCafeVendorMasterDetails(-1, -1);
			renderRequest.setAttribute("cafeVendorMasterDetails", cafeVendorMasterDetails);
		} catch (Exception e) {
			_log.debug(e.getMessage());
		}
		
		super.render(renderRequest, renderResponse);
	}
	@Reference private CafeVendorMasterLocalService _cafeVendorMasterLocalService;

}