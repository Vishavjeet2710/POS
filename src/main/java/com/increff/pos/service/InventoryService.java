package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryDao dao;
	
	@Transactional
	public void add(InventoryPojo p) {
		dao.insert(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public InventoryPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<InventoryPojo> getAll(){
		return dao.selectAll();
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,InventoryPojo p) throws ApiException {
		InventoryPojo ex =getCheck(id);
		ex.setQuantity(p.getQuantity());
		dao.update(id, p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	private InventoryPojo getCheck(int id) throws ApiException {
		InventoryPojo p = dao.select(id);
		if(p==null) {
			throw new ApiException("Product with given id does not exist and so ");
		}
		return p;
	}
}
