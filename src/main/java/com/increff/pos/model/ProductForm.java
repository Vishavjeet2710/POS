package com.increff.pos.model;

public class ProductForm {
	private String name;
	private String barcode;
	private int brand_category;
	private double mrp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getBrand_category() {
		return brand_category;
	}

	public void setBrand_category(int brand_category) {
		this.brand_category = brand_category;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
}
