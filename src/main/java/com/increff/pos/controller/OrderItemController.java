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

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/orderitem")
@RestController(value = "")
public class OrderItemController {
	@Autowired
	private OrderItemDto dto;
	
	@ApiOperation(value = "Add OrderItem")
	@PostMapping(value = "")
	public void add(@RequestBody OrderItemForm form) throws ApiException {
		dto.add(form);
	}
	
	@ApiOperation(value = "Get OrderItem")
	@GetMapping(value = "/{id}")
	public OrderItemData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}
	
	@ApiOperation(value = "Get All OrderItems")
	@GetMapping
	public List<OrderItemData> getAll(){
		return dto.getAll();
	}
	
	@ApiOperation(value = "Update OrderItem")
	@PutMapping(value = "/{id}")
	public void update(@PathVariable int id,@RequestBody OrderItemForm form) throws ApiException {
		dto.update(id, form);
	}
}