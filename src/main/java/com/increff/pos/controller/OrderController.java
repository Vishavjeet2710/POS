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

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;

import com.increff.pos.model.SaleReportForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/order")
@RestController(value = "")
public class OrderController {
	
	@Autowired
	private OrderDto orderDto;
	
	@ApiOperation(value = "Add an Order")
	@PostMapping(value = "")
	public void addOrder(@RequestBody OrderForm form) throws ApiException {
		orderDto.addOrder(form);
	}
	
	@ApiOperation(value = "Get an Order")
	@GetMapping(value = "/{orderId}")
	public OrderData getOrderItems(@PathVariable int orderId) throws ApiException {
		return orderDto.getByOrderId(orderId);
	}
	
	@ApiOperation(value = "Get all orders")
	@GetMapping(value = "")
	public List<OrderData> getAllOrder() throws ApiException{
		return orderDto.getAll();
	}
	
	@ApiOperation(value = "Return list")
	@PutMapping(value = "/download/")
	public void downloadSaleReport(HttpServletRequest request, HttpServletResponse response,@RequestBody SaleReportForm saleReportForm) throws ApiException{
		orderDto.DownloadSaleReport(request,response,saleReportForm);
	}
	
	
	@ApiOperation(value = "Convert Brand Object to XML")
	@GetMapping(value = "/download/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id) throws ApiException {
		orderDto.download(request, response, id);
	}
}
