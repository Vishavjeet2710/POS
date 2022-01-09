package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.InventoryHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;

@Service
@Validated
public class InventoryDto {
	
	@Autowired
	private InventoryService service;
	
	@Autowired
	private ProductService productService;

	@Transactional(rollbackOn = ApiException.class)
	public void add(InventoryForm form) throws ApiException {
		InventoryHelper.checkNegative(form);
		productService.getCheck(form.getId());
		service.addCheck(InventoryHelper.convert(form));
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public InventoryData get(int id) throws ApiException {
		return InventoryHelper.convert(service.getCheck(id));
	}
	
	@Transactional
	public List<InventoryData> getAll(){
		List<InventoryData> results = new ArrayList<InventoryData>();
		List<InventoryPojo> list = service.getAll();
		for(InventoryPojo p : list) {
			results.add(InventoryHelper.convert(p));
		}
		return results;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,InventoryForm form) throws ApiException {
		InventoryHelper.checkNegative(form);
		productService.getCheck(form.getId());
		service.update(id, InventoryHelper.convert(form));
	}
}
