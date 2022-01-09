package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.OrderItemHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;

@Service
@Validated
public class OrderItemDto {

	@Autowired
	private OrderItemService service;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(OrderItemForm form) throws ApiException {
		ProductPojo productPojo = productService.getCheck(form.getProductId());
		orderService.getCheck(form.getOrderId());
		InventoryPojo inventoryPojo = inventoryService.getCheck(form.getProductId());
		
		OrderItemPojo p = OrderItemHelper.convert(form);
		OrderItemHelper.setSellingPrice(p,productPojo.getMrp());
		
		OrderItemHelper.updateInventory(p,inventoryPojo);
		inventoryService.update(form.getProductId(), inventoryPojo);
		service.addCheck(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public OrderItemData get(int id) throws ApiException {
		return OrderItemHelper.convert(service.getCheck(id));
	}
	
	@Transactional
	public List<OrderItemData> getAll(){
		List<OrderItemData> results = new ArrayList<OrderItemData>();
		List<OrderItemPojo> list = service.getAll();
		for(OrderItemPojo p : list) {
			results.add(OrderItemHelper.convert(p));
		}
		return results;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,OrderItemForm form) throws ApiException {
		
		ProductPojo productPojo = productService.getCheck(form.getProductId());
		orderService.getCheck(form.getOrderId());
		InventoryPojo inventoryPojo = inventoryService.get(form.getProductId());
		OrderItemPojo p = OrderItemHelper.convert(form);
		OrderItemHelper.setSellingPrice(p,productPojo.getMrp());
		
		OrderItemPojo ex = service.getCheck(id);
		InventoryPojo exInventoryPojo = inventoryService.getCheck(ex.getProductId());
		OrderItemHelper.updateExInventory(ex,exInventoryPojo);
		inventoryService.update(ex.getProductId(), exInventoryPojo);
		
		OrderItemHelper.updateInventory(p,inventoryPojo);
		inventoryService.update(form.getProductId(), inventoryPojo);
		service.update(id, p);
	
	}
}
