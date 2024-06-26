package com.cafeteriaVendorManagement.util;

import com.plng.common.schema.model.CafeItemMaster;

import java.util.List;

import org.osgi.service.component.annotations.Component;
@Component(immediate = true, service = CafeteriaVendorManagementUtil.class)
public class CafeteriaVendorManagementUtil {
	
	public String getItemNameById(long itemId, List<CafeItemMaster> cafeItemMasterList) {
	    String itemName = null;
	    for (CafeItemMaster item : cafeItemMasterList) {
	        if (item.getItemId() == itemId) {
	            itemName = item.getItemDesc();
	            break;
	        }
	    }
	    return itemName;
	}

}
