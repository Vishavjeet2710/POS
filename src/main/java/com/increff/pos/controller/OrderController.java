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

import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController(value = "")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@ApiOperation(value = "Generates an Order")
	@PostMapping(value = "/api/order")
	public void add(@RequestBody OrderForm form) {
		OrderPojo p = convert(form);
		service.add(p);
	}

	@ApiOperation(value = "Gets an Order's Date Time")
	@GetMapping(value = "/api/order/{id}")
	public OrderData get(@PathVariable int id) throws ApiException {
		OrderPojo p = service.get(id);
		return convert(p);
	}
	
	@ApiOperation(value = "Gets every order's Date Time")
	@GetMapping(value = "/api/order")
	public List<OrderData> getAll(){
		List<OrderData> results = new ArrayList<OrderData>();
		List<OrderPojo> list = service.getAll();
		for(OrderPojo p : list) {
			results.add(convert(p));
		}
		return results;
	}

	@ApiOperation(value = "Updates order's Date Time")
	@PutMapping(value = "/api/order/{id}")
	public void update(@PathVariable int id,@RequestBody OrderForm form) throws ApiException {
		OrderPojo p = convert(form);
		service.update(id, p);
	}
	
	private OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setTime(p.getTime());
		d.setId(p.getId());
		return d;
	}

	private OrderPojo convert(OrderForm form) {
		OrderPojo p = new OrderPojo();
		p.setTime(form.getTime());
		return p;
	}
}
