package com.increff.pos.model;

public class InventoryBarcodeData extends InventoryBarcodeForm{
	private int quantity;
	private double mrp;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
}
