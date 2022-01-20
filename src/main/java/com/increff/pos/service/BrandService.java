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
		//ID updates only when it inserts into db
		BrandPojo ex = dao.selectByCategoryBrand(p.getCategory(), p.getBrand());
		if(ex!=null) {
			throw new ApiException("Brand with given brand and category already exists, brand"+ p.getBrand()+ " and category "+ p.getCategory());
		}
		add(p);
	}

	@Transactional
	public BrandPojo get(int id) {
		return dao.select(id);
	}
	
	@Transactional
	public BrandPojo getByBrandCategory (String brand, String category) {
		return dao.selectByCategoryBrand(category, brand);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo getCheckByBrandCategory(String brand,String category) throws ApiException {
		BrandPojo p = getByBrandCategory(brand, category);
		if(p==null) {
			throw new ApiException("Brand with given category and brand name does not exists");
		}
		return p;
	}
		
	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(BrandPojo p_ex, BrandPojo p) throws ApiException {
		BrandPojo ex = getCheckByBrandCategory(p_ex.getBrand(),p_ex.getCategory());
		ex.setCategory(p.getCategory());
		ex.setBrand(p.getBrand());
		dao.update(ex.getId(), p);
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
