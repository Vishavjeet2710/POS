package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.ProductPojo;

@Service
public class ProductService {
	
	@Autowired
	public ProductDao dao;
	
	@Transactional
	public void add(ProductPojo p) {
		normalize(p);
		dao.insert(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<ProductPojo> getAll(){
		return dao.selectAll();
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,ProductPojo p) throws ApiException {
		ProductPojo ex = getCheck(id);
		ex.setBarcode(p.getBarcode());
		ex.setBrand_category(p.getBrand_category());
		ex.setName(p.getName());
		ex.setMrp(p.getMrp());
		dao.update(id, p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	private ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if(p==null) {
			throw new ApiException("Product with given Id does not exist, ID: "+id);
		}
		return p;
	}

	private void normalize(ProductPojo p) {
		p.setBarcode(p.getBarcode().toLowerCase().trim());
		p.setName(p.getName().toLowerCase().trim());
	}
}
