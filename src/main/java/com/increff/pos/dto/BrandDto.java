package com.increff.pos.dto;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.BrandHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.BrandService;


@Service
@Validated
public class BrandDto {

	@Autowired 
	private BrandService service;

	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandForm form) throws ApiException {
		BrandHelper.checkEmpty(form);
		BrandHelper.trimSpaces(form);
		BrandPojo p = BrandHelper.convert(form);
		service.add(p);
		return;
	}

	@Transactional
	public BrandData get(int id) throws ApiException {
		return BrandHelper.convert(service.get(id));
	}
	
	@Transactional
	public List<BrandData> getAll(){
		List<BrandPojo> list = service.getAll();
		List<BrandData> data = new ArrayList<BrandData>();
		for(BrandPojo p : list) {
			data.add(BrandHelper.convert(p));
		}
		return data;
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void update(int id,BrandForm form) throws  ApiException {
		BrandHelper.checkEmpty(form);
		BrandHelper.trimSpaces(form);
		BrandPojo p = BrandHelper.convert(form);
		service.update(id,p);
	}
}
