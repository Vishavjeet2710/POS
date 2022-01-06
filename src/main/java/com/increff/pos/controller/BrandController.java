package com.increff.pos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController(value = "")
public class BrandController {

	@Autowired
	private BrandService service;

	@ApiOperation(value = "Adds a Brand Master")
	@PostMapping(value = "/api/brand")
	public void add(@RequestBody BrandForm form) {
		BrandPojo p = convert(form);
		service.add(p);
	}

	@ApiOperation(value = "Gets a Brand Master")
	@GetMapping(value = "/api/brand/{id}")
	public BrandData get(@PathVariable int id) throws ApiException {
		BrandPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets all Brand Masters")
	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
	public List<BrandData> getAll() {
		List<BrandPojo> list = service.getAll();
		List<BrandData> results = new ArrayList<BrandData>();
		for (BrandPojo p : list) {
			results.add(convert(p));
		}
		return results;
	}

	@ApiOperation(value = "Updates a Brand Master")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.POST)
	public void Update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException {
		BrandPojo p = convert(form);
		service.update(id, p);
	}

	private BrandData convert(BrandPojo p) {
		BrandData d = new BrandData();
		d.setCategory(p.getCategory());
		d.setBrand(p.getBrand());
		d.setId(p.getId());
		return d;
	}

	private BrandPojo convert(BrandForm form) {
		BrandPojo p = new BrandPojo();
		p.setBrand(form.getBrand());
		p.setCategory(form.getCategory());
		return p;
	}
}
