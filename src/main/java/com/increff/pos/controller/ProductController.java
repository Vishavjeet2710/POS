package com.increff.pos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController(value = "")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@ApiOperation(value = "Adds a Product")
	@RequestMapping(path = "/api/product",method = RequestMethod.PUT)
	public void add(@RequestBody ProductForm form) {
		ProductPojo p = convert(form);
		service.add(p);
	}

	@ApiOperation(value = "Gets a Product")
	@RequestMapping(path = "/api/product/{id}",method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {
		ProductPojo p = service.get(id);
		return convert(p);
	}
	
	@ApiOperation(value = "Gets all products")
	@RequestMapping(path="/api/product",method = RequestMethod.GET)
	public List<ProductData> getAll(){
		List<ProductData> results = new ArrayList<ProductData>();
		List<ProductPojo> list = service.getAll();
		for(ProductPojo p : list) {
			results.add(convert(p));
		}
		return results;
	}
	
	@ApiOperation(value = "Updates a Product")
	@RequestMapping(value = "/api/product/{id}",method = RequestMethod.POST)
	public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {
		ProductPojo p = convert(form);
		service.update(id, p);
	}
	
	private ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setBarcode(p.getBarcode());
		d.setBrand_category(p.getBrand_category());
		d.setName(p.getName());
		d.setMrp(p.getMrp());
		d.setId(p.getId());
		return d;
	}

	private ProductPojo convert(ProductForm form) {
		ProductPojo p = new ProductPojo();
		p.setBarcode(form.getBarcode());
		p.setName(form.getName());
		p.setMrp(form.getMrp());
		p.setBrand_category(form.getBrand_category());
		return p;
	}
}
