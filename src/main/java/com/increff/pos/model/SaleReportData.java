package com.increff.pos.model;

import java.text.DecimalFormat;

public class SaleReportData {
	private int quantity;
	private double revenue;
	private String category;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = roundTwoDecimals(revenue);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	private static double roundTwoDecimals(double d) {
        DecimalFormat twoDecimals = new DecimalFormat("0.00");
        return Double.valueOf(twoDecimals.format(d));
	}
}
