package com.increff.pos.helper;

import java.text.DecimalFormat;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;

public class ProductHelper {
	public static ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setBarcode(p.getBarcode());
		d.setName(p.getName());
		d.setMrp(roundTwoDecimals(p.getMrp()));
		d.setId(p.getId());
		return d;
	}

	private static double roundTwoDecimals(double d) {
        DecimalFormat twoDecimals = new DecimalFormat("0.00");
        return Double.valueOf(twoDecimals.format(d));
	}
	
	public static ProductPojo convert(ProductForm form) {
		ProductPojo p = new ProductPojo();
		p.setBarcode(form.getBarcode());
		p.setName(form.getName());
		p.setMrp(roundTwoDecimals(form.getMrp()));
		return p;
	}
	
	public static void checkNegativeMrp(ProductForm form) throws ApiException {
		if(form.getMrp()<0) {
			throw new ApiException("Mrp or BrandCategory can not be negative");
		}		
	}

	public static void checkEmpty(ProductForm form) throws ApiException {
		if(form.getName().isEmpty() || form.getBarcode().isEmpty() || form.getBrand().isEmpty() || form.getCategory().isEmpty()) {
			throw new ApiException("Brand and Category can not be empty or null");
		}
	}
	
	public static void trimSpaces(ProductForm form) {
		form.setName(form.getName().toLowerCase().trim());
		form.setBarcode(form.getBarcode().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
		form.setBrand(form.getBrand().toLowerCase().trim());
	}
}
