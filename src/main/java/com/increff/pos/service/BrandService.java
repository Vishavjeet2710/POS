package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.model.ApiException;
import com.increff.pos.pojo.BrandPojo;

@Service
public class BrandService {

	@Autowired
	public BrandDao dao;

	@Transactional
	public void add(BrandPojo p) {
		dao.insert(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void addCheck(BrandPojo p) throws ApiException {
		BrandPojo ex = dao.select(p.getId());
		if(ex!=null) {
			throw new ApiException("Brand with given id already exists, ID"+ p.getId());
		}
		ex = dao.selectByCategoryBrand(p.getCategory(), p.getBrand());
		if(ex!=null) {
			throw new ApiException("Brand with given brand and category already exists, brand"+ p.getBrand()+ " and category "+ p.getCategory());
		}
		add(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		return dao.select(id);
	}

	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, BrandPojo p) throws ApiException {
		BrandPojo p_ex = getCheck(id);
		p_ex.setCategory(p.getCategory());
		p_ex.setBrand(p.getBrand());
		dao.update(id, p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = get(id);
		if (p == null) {
			throw new ApiException("Given id does not exist, ID: " + id);
		}
		return p;
	}
}
