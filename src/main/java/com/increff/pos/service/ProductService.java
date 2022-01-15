package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.ApiException;
import com.increff.pos.pojo.ProductPojo;

@Service
public class ProductService {
	
	@Autowired
	public ProductDao dao;
	
	@Transactional
	public void add(ProductPojo p) {
		dao.insert(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void addCheck(ProductPojo p) throws ApiException {
		ProductPojo ex = dao.select(p.getId());
		if(ex!=null) {
			throw new ApiException("Object with the given ID already exists, ID"+p.getId());	
		}
		ex = dao.selectByBarcode(p.getBarcode());
		if(ex!=null) {
			throw new ApiException("Object with the given barcode already exists, ID"+p.getBarcode());	
		}
		add(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		return dao.select(id);
	}

	@Transactional
	public ProductPojo getByBarcode(String barcode) {
		return dao.selectByBarcode(barcode);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo getCheckByBarcode(String barcode) throws ApiException {
		ProductPojo ex = getByBarcode(barcode);
		if(ex==null) {
			throw new ApiException("Product with given barcode does not exists");
		}
		return ex;
	}
	
	@Transactional
	public List<ProductPojo> getAll(){
		return dao.selectAll();
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(ProductPojo p) throws ApiException {
		ProductPojo ex = dao.selectByBarcode(p.getBarcode());
		if(ex==null) {
			throw new ApiException("Product with given barcode does not exist");
		}
		ex.setBarcode(p.getBarcode());
		ex.setBrand_category(p.getBrand_category());
		ex.setName(p.getName());
		ex.setMrp(p.getMrp());
		dao.update(ex.getId(), p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = get(id);
		if(p==null) {
			throw new ApiException("Product with given Id does not exist, ID: "+id);
		}
		return p;
	}
}
