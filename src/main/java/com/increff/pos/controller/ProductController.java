package com.increff.pos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.download.ProductDownload;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.xmlRootElement.ProductXmlRootElement;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/product")
@RestController(value = "")
public class ProductController {
	
	@Autowired
	private ProductDto dto;
	
	@ApiOperation(value = "Add a Product")
	@PostMapping(value = "")
	public void add(@RequestBody ProductForm form) throws  ApiException {
		dto.add(form);
	}

	@ApiOperation(value = "Get a Product")
	@GetMapping(value = "/{id}")
	public ProductData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}
	
	@ApiOperation(value = "Get all products")
	@GetMapping(value = "")
	public List<ProductData> getAll(){
		return dto.getAll();
	}
	
	@ApiOperation(value = "Update a Product")
	@PutMapping(value = "/update")
	public void update(@RequestBody ProductForm form) throws ApiException {
		dto.update(form);
	}
	
	@ApiOperation(value = "Download Product Report")
	@GetMapping(value = "/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws ApiException {
		ProductXmlRootElement productDatas = new ProductXmlRootElement();
		productDatas.setProductDatas(dto.getAll());
		ProductDownload.ProductDownloadHelper(productDatas, request, response);
	}
}
