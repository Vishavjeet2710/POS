package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;

@Service
public class OrderService {
	@Autowired
	OrderDao dao;
	
	@Transactional
	public void add(OrderPojo p) {
		dao.insert(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo get(int id) throws ApiException {
		return getCheck(id);
	}
	
	@Transactional
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,OrderPojo p) throws ApiException {
		OrderPojo ex = getCheck(id);
		ex.setTime(p.getTime());
		dao.update(id, p);
	}

	@Transactional(rollbackOn = ApiException.class)
	private OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if(p==null) {
			throw new ApiException("Order with the given ID does not exist, ID: "+id);
		}
		return p;
	}
}
