package com.increff.pos.helper;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;

public class OrderItemHelper {
	public static OrderItemData convert(OrderItemPojo p) {
		OrderItemData d = new OrderItemData();
		d.setId(p.getId());
		d.setOrderId(p.getOrderId());
		d.setQuantity(p.getQuantity());
		d.setSellingPrice(p.getSellingPrice());
		return d;
	}
	
	public static OrderItemPojo convert(OrderItemForm form) {
		OrderItemPojo p = new OrderItemPojo();
		p.setOrderId(form.getOrderId());
		p.setQuantity(form.getQuantity());
		return p;
	}
	
	public static void setSellingPrice(OrderItemPojo p,double sellingPrice) {
		p.setSellingPrice(sellingPrice);
	}
	
	public static void updateInventory(OrderItemPojo p,InventoryPojo inventoryPojo) throws ApiException {
		checkInventory(p,inventoryPojo);
		inventoryPojo.setQuantity(inventoryPojo.getQuantity()-p.getQuantity());
	}
	
	public static void updateExInventory(OrderItemPojo p,InventoryPojo inventoryPojo) {
		inventoryPojo.setQuantity(inventoryPojo.getQuantity()+p.getQuantity());
	}
	
	public static void checkInventory(OrderItemPojo p,InventoryPojo inventoryPojo) throws ApiException {
		if(p.getQuantity()>inventoryPojo.getQuantity()) {
			throw new ApiException("Not enough inventory");
		}
	}
}
