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
	public void add(ProductForm form) throws  ApiException {
		ProductHelper.checkEmpty(form);
		ProductHelper.trimSpaces(form);
		ProductHelper.checkNegativeMrp(form);
		brandService.getCheck(form.getBrand_category());
		service.addCheck(ProductHelper.convert(form));
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductData get(int id) throws ApiException {
		return ProductHelper.convert(service.getCheck(id));
	}
	
	@Transactional
	public List<ProductData> getAll(){
		List<ProductData> results = new ArrayList<ProductData>();
		List<ProductPojo> list= service.getAll();
		for(ProductPojo p : list) {
			results.add(ProductHelper.convert(p));
		}
		return results;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,ProductForm form) throws  ApiException {
		ProductHelper.checkEmpty(form);
		ProductHelper.trimSpaces(form);
		ProductHelper.checkNegativeMrp(form);
		brandService.getCheck(form.getBrand_category());
		service.update(id, ProductHelper.convert(form));
	}
}
