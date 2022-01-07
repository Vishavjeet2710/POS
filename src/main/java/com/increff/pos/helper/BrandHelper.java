package com.increff.pos.helper;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;

public class BrandHelper {
	public static BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setCategory(p.getCategory());
		d.setBrand(p.getBrand());
		d.setId(p.getId());
		return d;
	}

	public static BrandPojo convert(BrandForm form) {
		BrandPojo p = new BrandPojo();
		p.setBrand(form.getBrand());
		p.setCategory(form.getCategory());
		return p;
	}
}
