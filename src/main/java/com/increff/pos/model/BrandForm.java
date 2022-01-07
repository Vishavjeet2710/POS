package com.increff.pos.model;

import javax.validation.constraints.NotBlank;

public class BrandForm {
	@NotBlank(message = "Brand Name can Not be Empty")
	private String brand;
	@NotBlank(message = "Category Name can Not be Empty")
	private String category;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
