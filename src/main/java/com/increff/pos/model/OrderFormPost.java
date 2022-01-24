package com.increff.pos.model;

import java.util.List;

public class OrderFormPost {
	private List<OrderItemForm> orderItemForms;

	public List<OrderItemForm> getOrderItemForms() {
		return orderItemForms;
	}

	public void setOrderItemForms(List<OrderItemForm> orderItemForms) {
		this.orderItemForms = orderItemForms;
	}

}
