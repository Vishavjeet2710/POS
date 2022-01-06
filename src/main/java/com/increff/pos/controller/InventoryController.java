package com.increff.pos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/api/inventory",method = RequestMethod.PUT)
	public void add(@RequestBody InventoryForm form) {
		InventoryPojo p = convert(form);
		service.add(p);
	}
	
	@ApiOperation(value = "Gets the Inventory of one Product")
	@RequestMapping(value = "/api/inventory/{id}",method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {
		InventoryPojo p = service.get(id);
		return convert(p);
	}
	
	@ApiOperation(value = "Gets the Inventory of each product")
	@RequestMapping(value = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> getAll(){
		List<InventoryData> results = new ArrayList<InventoryData>();
		List<InventoryPojo> list = service.getAll();
		for(InventoryPojo p : list) {
			results.add(convert(p));
		}
		return results;
	}
	
	@ApiOperation(value = "Updates an Inventory")
	@RequestMapping(value = "/api/inventory/{id}", method = RequestMethod.POST)
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
