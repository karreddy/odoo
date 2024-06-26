package com.cafeteria.mvc.util;

import com.plng.common.schema.model.CafeItemMaster;

import java.util.List;

import org.osgi.service.component.annotations.Component;
@Component(immediate = true, service = CafeteriaUtil.class)
public class CafeteriaUtil {
	
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
