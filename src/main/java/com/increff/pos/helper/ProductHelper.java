package com.increff.pos.helper;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;

public class ProductHelper {
	public static ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setBarcode(p.getBarcode());
		d.setBrand_category(p.getBrand_category());
		d.setName(p.getName());
		d.setMrp(p.getMrp());
		d.setId(p.getId());
		return d;
	}

	public static ProductPojo convert(ProductForm form) {
		ProductPojo p = new ProductPojo();
		p.setBarcode(form.getBarcode());
		p.setName(form.getName());
		p.setMrp(form.getMrp());
		p.setBrand_category(form.getBrand_category());
		return p;
	}
	
	public static void checkNegativeMrp(ProductForm form) throws ApiException {
		if(form.getMrp()<0 || form.getBrand_category()<0) {
			throw new ApiException("Mrp or BrandCategory can not be negative");
		}		
	}

	public static void checkEmpty(ProductForm form) throws ApiException {
		if(form.getName().isEmpty() || form.getBarcode().isEmpty()) {
			throw new ApiException("Brand and Category can not be empty or null");
		}
	}
	
	public static void trimSpaces(ProductForm form) {
		form.setName(form.getName().toLowerCase().trim());
		form.setBarcode(form.getBarcode().toLowerCase().trim());
	}
}
