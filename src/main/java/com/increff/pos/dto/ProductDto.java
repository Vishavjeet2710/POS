package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.ProductHelper;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.model.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;

@Service
@Validated
public class ProductDto {
	@Autowired
	private ProductService service;
	
	@Autowired
	private BrandService brandService;
	
	@Transactional(rollbackOn = ApiException.class)
	public int add(ProductForm form) throws  ApiException {
		ProductHelper.checkEmpty(form);
		ProductHelper.trimSpaces(form);
		ProductHelper.checkNegativeMrp(form);
		BrandPojo brandPojo =  brandService.getCheckByBrandCategory(form.getBrand(),form.getCategory());
		ProductPojo p = ProductHelper.convert(form);
		p.setBrand_category(brandPojo.getId());
		service.addCheck(p);
		return p.getId();
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductData get(int id) throws ApiException {
		ProductPojo p = service.getCheck(id);
		ProductData data = ProductHelper.convert(p);
		BrandPojo brandPojo =  brandService.get(p.getBrand_category());
		data.setBrand(brandPojo.getBrand());
		data.setCategory(brandPojo.getCategory());
		return data;
	}
	
	@Transactional
	public List<ProductData> getAll(){
		List<ProductData> results = new ArrayList<ProductData>();
		List<ProductPojo> list= service.getAll();
		for(ProductPojo p : list) {
			ProductData data = ProductHelper.convert(p);
			BrandPojo brandPojo =  brandService.get(p.getBrand_category());
			data.setBrand(brandPojo.getBrand());
			data.setCategory(brandPojo.getCategory());
			results.add(data);
		}
		return results;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(ProductForm form) throws  ApiException {
		ProductHelper.checkEmpty(form);
		ProductHelper.trimSpaces(form);
		ProductHelper.checkNegativeMrp(form);
		BrandPojo brandPojo = brandService.getCheckByBrandCategory(form.getBrand(),form.getCategory());
		ProductPojo p = ProductHelper.convert(form);
		p.setBrand_category(brandPojo.getId());
		service.update(p,form.getBrand(),form.getCategory());
	}
}
