package com.cafeteria.portlet;

import com.cafeteria.constants.CafeteriaPortletKeys;
import com.cafeteria.constants.CafeteriaWebConstantKeys;
import com.cafeteria.mvc.util.CafeteriaUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.plng.common.schema.model.CafeItemMaster;
import com.plng.common.schema.model.CanteenInventory;
import com.plng.common.schema.model.ContractLogin;
import com.plng.common.schema.model.CouponStatus;
import com.plng.common.schema.model.EmployeeDirectory;
import com.plng.common.schema.model.InventoryQty;
import com.plng.common.schema.service.CafeItemMasterLocalService;
import com.plng.common.schema.service.CanteenInventoryLocalService;
import com.plng.common.schema.service.ContractLoginLocalService;
import com.plng.common.schema.service.CouponStatusLocalService;
import com.plng.common.schema.service.EmployeeDirectoryLocalService;
import com.plng.common.schema.service.InventoryQtyLocalService;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
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
		"com.liferay.portlet.display-category=PLNG",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=cafeteria",
		"javax.portlet.init-param.template-path=/",
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.init-param.view-template=/view.jsp",
		"com.liferay.portlet.header-portlet-javascript=/js/cafe.js",
		"javax.portlet.name=" + CafeteriaPortletKeys.CAFETERIA,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CafeteriaPortlet extends MVCPortlet {
    private static final Log _log = LogFactoryUtil.getLog(CafeteriaPortlet.class);

    @Reference(unbind = "-")
    private CafeteriaUtil cafeteriaUtil;

    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
        _log.info("CafeteriaPortlet.render()...");

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
        long userId = themeDisplay.getUserId();
        long employeeId = 0;
       
        ContractLogin contEmpByUserId = null;
        long contEmpuserId=0;
		try {
			 contEmpByUserId = _contractLoginLocalService.getContEmpByLrId(userId);
			 contEmpuserId = contEmpByUserId.getUserId();
			 _log.info("contEmpuserId >>>>"+contEmpuserId);
		} catch (Exception e1) {
			_log.error("Exec >>>"+e1.getMessage());
		}
        
		try {
            EmployeeDirectory employeeDetails = _employeeDirectoryLocalService.getEmployeeDetailsByUserId(userId);
            if (Validator.isNotNull(employeeDetails)) {
                employeeId = employeeDetails.getEmployeeId();
            }
        } catch (Exception e) {
            _log.debug(e.getMessage());
        }

        JSONArray inventoryJsonArray = JSONFactoryUtil.createJSONArray();
        CafeItemMaster itemMaster = null;
        CanteenInventory canteenInventory = null;
        List<CouponStatus> couponsByUser = null;
        try {
            List<InventoryQty> inventoryQties = _inventoryQtyLocalService.getInventoryQties(-1, -1)
                    .stream()
                    .sorted(Comparator.comparing(InventoryQty::getDate).reversed())
                    .filter(item -> item.getQty() > 0)
                    .collect(Collectors.toList());
            _log.info("inventoryQties size: " + inventoryQties.size());

            for (InventoryQty inventoryQty : inventoryQties) {
                long itemId = inventoryQty.getItem_id();
                float qty = inventoryQty.getQty();
                  List<CanteenInventory> canteenInventoryList = null;
				try {
					canteenInventoryList = _canteenInventoryLocalService.getByItemId(itemId);
				} catch (Exception e) {
					_log.error(e.getMessage());
				}
				
				canteenInventory = canteenInventoryList.get(0); 
                  itemMaster = _cafeItemMasterLocalService.getCafeItemMaster(itemId);

                JSONObject inventoryObject = JSONFactoryUtil.createJSONObject();
                inventoryObject.put("itemId", itemId);
                inventoryObject.put("itemName", itemMaster.getItemDesc());
                inventoryObject.put("qty", qty);
                inventoryObject.put("oneB", canteenInventory.getHamper());
                inventoryObject.put("res_guest", canteenInventory.getRes_guest());
                inventoryObject.put("unit_price", canteenInventory.getUnit_price());
                inventoryObject.put("resGuest", canteenInventory.getRes_guest());
                Date currentDate = new Date();

                if (employeeId != 0) {
                	  _log.info("couponsByUser employeeId>>>>"+employeeId);
                    couponsByUser = _couponStatusLocalService.getCouponsByUser(employeeId, currentDate, -1, -1);
                } else if (contEmpuserId!=0){
                	 _log.info("couponsByUser contEmpuserId>>>>"+contEmpuserId);
                    couponsByUser = _couponStatusLocalService.getCouponsByUser(contEmpuserId, currentDate, -1, -1);
                }else {
                	 _log.info("couponsByUser userId>>>>"+userId);
                	couponsByUser = _couponStatusLocalService.getCouponsByUser(userId, currentDate, -1, -1);
                }
                float totalReqQty = (float) couponsByUser.stream()
                        .filter(coupon -> coupon.getItem_id() == itemId && (coupon.getStatus().equalsIgnoreCase("Issued") || coupon.getStatus().equalsIgnoreCase("New")))
                        .mapToDouble(CouponStatus::getRequested_quant)
                        .sum();

                inventoryObject.put("totalReqQty", totalReqQty);
                inventoryJsonArray.put(inventoryObject);
            }

            renderRequest.setAttribute("itemIdAndNameArray", inventoryJsonArray);
        } catch (Exception e) {
            _log.debug("Error processing inventory data: " + e.getMessage());
        }

        try {
            _log.info("employeeId: " + employeeId + ":: userId: " + userId);
            double selfTotalAmount;
            double guestTotalAmount;
            List<CouponStatus> totalAmountList = null;

            if (employeeId != 0) {
            	_log.info("employeeId: " + employeeId + ":: userId: " + userId);
                totalAmountList = _couponStatusLocalService.getTotalAmount(employeeId);
            	_log.info("totalAmountList: " + totalAmountList );
            }
            else if(contEmpuserId!=0){
            	_log.info("contEmpuserId: " + contEmpuserId + ":: userId: " + userId);
                totalAmountList = _couponStatusLocalService.getTotalAmount(contEmpuserId); 
            }
            else{
            totalAmountList = _couponStatusLocalService.getTotalAmount(userId); 
        }

            selfTotalAmount = totalAmountList.stream()
                    .filter(couponStatus -> ("Issued".equals(couponStatus.getStatus()) || "New".equals(couponStatus.getStatus())) && "S".equals(couponStatus.getReq_for()))
                    .mapToDouble(CouponStatus::getValue_quant)
                    .sum();
            
            guestTotalAmount = totalAmountList.stream()
                    .filter(couponStatus -> ("Issued".equals(couponStatus.getStatus()) || "New".equals(couponStatus.getStatus())) && "G".equals(couponStatus.getReq_for()))
                    .mapToDouble(CouponStatus::getValue_quant)
                    .sum();

            
            DecimalFormat df = new DecimalFormat("#.##");
            renderRequest.setAttribute("selfTotalAmount", Float.parseFloat(df.format(selfTotalAmount)));
            renderRequest.setAttribute("guestTotalAmount", Float.parseFloat(df.format(guestTotalAmount)));
        } catch (Exception e) {
            _log.debug("Error calculating total amount: " + e.getMessage());
        }

        try {
            boolean isGrade5 = RoleLocalServiceUtil.hasUserRole(userId, themeDisplay.getCompanyId(), CafeteriaWebConstantKeys.ROLE_GRADE_5, false);
            renderRequest.setAttribute(CafeteriaWebConstantKeys.IS_ROLE, isGrade5);

            boolean isContEmp = RoleLocalServiceUtil.hasUserRole(userId, themeDisplay.getCompanyId(), "Contract Employee", false);
            renderRequest.setAttribute("isContEmp", isContEmp);

            LocalDate today = LocalDate.now();
            renderRequest.setAttribute(CafeteriaWebConstantKeys.CURRENT_MONTH, today.getMonth());
        } catch (PortalException e) {
            _log.debug("Error checking user role: " + e.getMessage());
        }

        super.render(renderRequest, renderResponse);
    }


    @Reference private CafeItemMasterLocalService _cafeItemMasterLocalService;
    @Reference private CouponStatusLocalService _couponStatusLocalService;
    @Reference private InventoryQtyLocalService _inventoryQtyLocalService;
    @Reference private CanteenInventoryLocalService _canteenInventoryLocalService;
    @Reference private EmployeeDirectoryLocalService _employeeDirectoryLocalService;
    @Reference private ContractLoginLocalService _contractLoginLocalService;
	 
}

