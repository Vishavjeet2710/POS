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

import com.increff.pos.download.OrderDownload;
import com.increff.pos.dto.OrderDto;
import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderFormPost;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.OrderPostReturn;
import com.increff.pos.xmlRootElement.OrderXmlRootElement;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api")
@RestController(value = "")
public class OrderController {
	
	@Autowired
	private OrderDto dto;
	
	@Autowired
	private OrderItemDto orderItemdto;
	
	@ApiOperation(value = "Add an Order")
	@PostMapping(value = "/order")
	public OrderPostReturn addOrder(@RequestBody OrderFormPost form) throws ApiException {
		OrderPostReturn orderPostReturn = new OrderPostReturn();
		orderPostReturn.setId(dto.add(form));
		return orderPostReturn;
	}

	@ApiOperation(value = "Get an Order")
	@GetMapping(value = "/order/{orderId}")
	public List<OrderItemData> getOrderItems(@PathVariable int orderId) throws ApiException {
		return orderItemdto.getByOrderId(orderId);
	}
	
	@ApiOperation(value = "Get all orders")
	@GetMapping(value = "/order")
	public List<OrderData> getAllOrder(){
		return dto.getAll();
	}
	
	
	@ApiOperation(value = "Add OrderItem")
	@PostMapping(value = "/orderitem")
	public void addOrderItem(@RequestBody OrderItemForm form) throws ApiException {
		orderItemdto.add(form);
	}
	
	@ApiOperation(value = "Get OrderItem")
	@GetMapping(value = "/orderitem/{id}")
	public OrderItemData getOrderItem(@PathVariable int id) throws ApiException {
		return orderItemdto.get(id);
	}
	
	@ApiOperation(value = "Get All OrderItems")
	@GetMapping(value = "/orderitem")
	public List<OrderItemData> getAllOrderItem() throws ApiException{
		return orderItemdto.getAll();
	}
	
	@ApiOperation(value = "Update OrderItem")
	@PutMapping(value = "/orderitem/{id}")
	public void updateOrderItem(@PathVariable int id,@RequestBody OrderItemForm form) throws ApiException {
		orderItemdto.update(id, form);
	}
	@ApiOperation(value = "Convert Brand Object to XML")
	@GetMapping(value = "/order/download/{id}")
	public void download(HttpServletRequest request, HttpServletResponse response,@PathVariable("id") int id) throws ApiException {
		OrderXmlRootElement orderDatas = new OrderXmlRootElement();
		orderDatas.setOrderItemDatas(orderItemdto.getByOrderId(id));
		OrderData orderData = dto.get(id);
		orderDatas.setDateTime(orderData.getTime());
		orderDatas.setOrderId(id);
		OrderDownload.OrderDownloadHelper(orderDatas, request, response);
	}
}
