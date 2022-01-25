package com.increff.pos.model;

import java.util.List;

public class OrderData extends OrderFormUpdate {
	private int id;
	private List<OrderItemData> orderItemDatas;

	public List<OrderItemData> getOrderItemDatas() {
		return orderItemDatas;
	}

	public void setOrderItemDatas(List<OrderItemData> orderItemDatas) {
		this.orderItemDatas = orderItemDatas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
