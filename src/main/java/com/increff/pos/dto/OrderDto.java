package com.increff.pos.dto;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.OrderHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderFormPost;
import com.increff.pos.model.OrderFormUpdate;
import com.increff.pos.service.OrderService;

@Service
@Validated
public class OrderDto {

	@Autowired
	private OrderService service;
	
	@Transactional(rollbackOn = ApiException.class)
	public int add(OrderFormPost form) throws ApiException {
		return  service.addCheck(OrderHelper.convert(form));
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public OrderData get(int id) throws ApiException {
		return OrderHelper.convert(service.getCheck(id));
	}
	
	@Transactional
	public List<OrderData> getAll() {
		return OrderHelper.convert(service.getAll());
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,OrderFormUpdate form) throws ApiException {
		OrderHelper.trimSpaces(form);
		service.update(id, OrderHelper.convert(form));
	}
}
