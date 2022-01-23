package com.increff.pos.dto;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.BrandHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.UpdateBrandForm;
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
		BrandHelper.checkEmpty(form);
		BrandPojo p = BrandHelper.convert(form);
		service.addCheck(p);
		return;
	}

	@Transactional
	public BrandData get(int id) throws ApiException {
		return BrandHelper.convert(service.getCheck(id));
	}
	
	@Transactional
	public List<BrandData> getAll(){
		return BrandHelper.convert(service.getAll());
		
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void update(UpdateBrandForm form) throws  ApiException {
		BrandHelper.checkEmpty(form);
		BrandHelper.trimSpaces(form);
		BrandHelper.checkEmpty(form);
		BrandPojo p = BrandHelper.convertUpdateCurr(form);
		BrandPojo p_ex = BrandHelper.convertUpdateEx(form);
		service.update(p_ex,p);
	}
}
