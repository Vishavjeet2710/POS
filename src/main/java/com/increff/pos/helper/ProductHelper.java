package com.increff.pos.helper;

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
}
