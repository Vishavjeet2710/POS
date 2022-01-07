package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.BlankException;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//TODO work in git branches not directly in master
//TODO download clipIt or any other tool -> suggestion
//TODO add arrow keys in IDE -> suggestion

//TODO change java version to 1.8 -> very necessary
//TODO add transaction only if you are connecting with DB

@Api
//TODO move common part of rest end point to this annotation
@RestController(value = "")
public class BrandController {
	
	@Autowired
	private BrandDto dto;
	
	@ApiOperation(value = "Adds a Brand Master")
	@PostMapping(value = "/api/brand")
	public void add(@RequestBody BrandForm form) throws BlankException {
		dto.add(form);
	}

	@ApiOperation(value = "Gets a Brand Master")
	@GetMapping(value = "/api/brand/{id}")
	public BrandData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	//TODO Get all brand master check naming convention in ApiOperation
	@ApiOperation(value = "Gets all Brand Masters")
	@GetMapping(value = "/api/brand")
	public List<BrandData> getAll() {
		return dto.getAll();
	}

	@ApiOperation(value = "Updates a Brand Master")
	@PutMapping(value = "/api/brand/{id}")
	public void Update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException, BlankException {
		dto.update(id, form);
	}
}
