package com.increff.pos.helper;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;

public class InventoryHelper {
	public static InventoryData convert(InventoryPojo p) {
		InventoryData d = new InventoryData();
		d.setId(p.getId());
		d.setQuantity(p.getQuantity());
		return d;
	}

	public static InventoryPojo convert(InventoryForm form) {
		InventoryPojo p = new InventoryPojo();
		p.setQuantity(form.getQuantity());
		p.setId(form.getId());
		return p;
	}
	
	public static void checkNegative(InventoryForm form) throws ApiException {
		if(form.getQuantity()<0) {
			throw new ApiException("Inventory Quantity can't be negative");
		}
	}
}
