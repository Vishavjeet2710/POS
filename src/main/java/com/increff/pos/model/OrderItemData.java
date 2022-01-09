package com.increff.pos.model;

public class OrderItemData extends OrderItemForm{
	private int id;
	private double sellingPrice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
}
