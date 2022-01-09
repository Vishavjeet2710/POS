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

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderFormPost;
import com.increff.pos.model.OrderFormUpdate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/order")
@RestController(value = "")
public class OrderController {
	
	@Autowired
	private OrderDto dto;
	
	@ApiOperation(value = "Add an Order")
	@PostMapping(value = "")
	public void add(@RequestBody OrderFormPost form) throws ApiException {
		dto.add(form);
	}

	@ApiOperation(value = "Get an Order")
	@GetMapping(value = "/{id}")
	public OrderData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}
	
	@ApiOperation(value = "Get all orders")
	@GetMapping(value = "")
	public List<OrderData> getAll(){
		return dto.getAll();
	}

	@ApiOperation(value = "Update order")
	@PutMapping(value = "/{id}")
	public void update(@PathVariable int id,@RequestBody OrderFormUpdate form) throws ApiException {
		dto.update(id, form);
	}
}
