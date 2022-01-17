package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.model.ApiException;
import com.increff.pos.pojo.OrderItemPojo;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemDao dao;
	
	@Transactional
	public void add(OrderItemPojo p) {
		dao.add(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void addCheck(OrderItemPojo p) throws ApiException {
		OrderItemPojo ex = dao.select(p.getId());
		if(ex!=null) {
			throw new ApiException("OrderItemPojo with given Id already exists, Id"+ p.getId());
		}
		add(p);
	}
	
	@Transactional
	public OrderItemPojo get(int id) {
		return dao.select(id);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo getCheck(int id) throws ApiException {
		OrderItemPojo p = get(id);
		if(p==null) {
			throw new ApiException("OrderItemPojo with given Id does not exist, Id" + id);
		}
		return p;
	}
	
	@Transactional
	public List<OrderItemPojo> getAll(){
		return dao.selectAll();
	}
	
	@Transactional
	public List<OrderItemPojo> getByOrderId(int orderId){
		return dao.selectByOrderId(orderId);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,OrderItemPojo p) throws ApiException {
		OrderItemPojo ex = getCheck(id);
		ex.setOrderId(p.getOrderId());
		ex.setProductId(p.getOrderId());
		ex.setQuantity(p.getQuantity());
		ex.setSellingPrice(p.getSellingPrice());
		dao.update(id, p);
	}
}
