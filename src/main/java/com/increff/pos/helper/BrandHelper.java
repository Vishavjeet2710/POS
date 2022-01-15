package com.increff.pos.helper;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.UpdateBrandForm;
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
	
	public static void checkEmpty(BrandForm form) throws ApiException {
		if(form.getBrand().isEmpty() || form.getCategory().isEmpty()) {
			throw new ApiException("Brand and Category can not be empty or null");
		}
	}
	
	public static void trimSpaces(BrandForm form) {
		form.setBrand(form.getBrand().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
	}

	public static BrandPojo convertUpdateCurr(UpdateBrandForm form) {
		BrandPojo p = new BrandPojo();
		p.setBrand(form.getBrand());
		p.setCategory(form.getCategory());
		return p;
	}

	public static BrandPojo convertUpdateEx(UpdateBrandForm form) {
		BrandPojo p = new BrandPojo();
		p.setBrand(form.getExBrand());
		p.setCategory(form.getExCategory());
		return p;
	}
}
