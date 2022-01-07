package com.increff.pos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController(value = "")
public class InventoryController {

	@Autowired
	private InventoryService service;

	@ApiOperation(value = "Adds an Inventory")
	@PostMapping(value = "/api/inventory")
	public void add(@RequestBody InventoryForm form) {
		InventoryPojo p = convert(form);
		service.add(p);
	}
	
	@ApiOperation(value = "Gets the Inventory of one Product")
	@GetMapping(value = "/api/inventory/{id}")
	public InventoryData get(@PathVariable int id) throws ApiException {
		InventoryPojo p = service.get(id);
		return convert(p);
	}
	
	@ApiOperation(value = "Gets the Inventory of each product")
	@GetMapping(value = "/api/inventory")
	public List<InventoryData> getAll(){
		List<InventoryData> results = new ArrayList<InventoryData>();
		List<InventoryPojo> list = service.getAll();
		for(InventoryPojo p : list) {
			results.add(convert(p));
		}
		return results;
	}
	
	@ApiOperation(value = "Updates an Inventory")
	@PutMapping(value = "/api/inventory/{id}")
	public void update(@PathVariable int id,@RequestBody InventoryForm form) throws ApiException {
		InventoryPojo p = convert(form);
		service.update(id, p);
	}
	
	private InventoryData convert(InventoryPojo p) {
		InventoryData d = new InventoryData();
		d.setId(p.getId());
		d.setQuantity(p.getQuantity());
		return d;
	}

	private InventoryPojo convert(InventoryForm form) {
		InventoryPojo p = new InventoryPojo();
		p.setQuantity(form.getQuantity());
		p.setId(form.getId());
		return p;
	}
}
