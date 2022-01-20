package com.increff.pos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.InventoryBarcodeData;
import com.increff.pos.model.InventoryBarcodeForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/inventory")
@RestController(value = "")
public class InventoryController {

	@Autowired
	private InventoryDto dto;

	@ApiOperation(value = "Add an Inventory")
	@PostMapping(value = "")
	public void add(@RequestBody InventoryForm form) throws ApiException {
		dto.add(form);
	}
	
	@ApiOperation(value = "Get Inventory of one Product")
	@GetMapping(value = "/{id}")
	public InventoryData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}
	
	@ApiOperation(value = "Get Inventory of one Product")
	@PutMapping(value = "/getByBarcode")
	public InventoryBarcodeData getByBarcode(@RequestBody InventoryBarcodeForm form) throws ApiException {
		return dto.getByBarcode(form.getBarcode());
	}
	
	@ApiOperation(value = "Get Inventory of each product")
	@GetMapping(value = "")
	public List<InventoryData> getAll() throws ApiException{
		return dto.getAll();
	}
	
	@ApiOperation(value = "Update an Inventory")
	@PutMapping(value = "/update")
	public void update(@RequestBody InventoryForm form) throws ApiException {
		dto.update(form);
	}
}
