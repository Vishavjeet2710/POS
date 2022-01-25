package com.increff.pos.model;

public class SaleReportForm {
	private String startDateTime;
	private String endDateTime;
	private String brand;
	private String category;

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		if(category=="Choose Category..." || category=="choose category...") {
			category="";
		}
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
