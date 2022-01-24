package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.helper.InventoryHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.InventoryBarcodeData;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
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
		ProductPojo productPojo =	productService.getCheckByBarcode(form.getBarcode());
		InventoryPojo p = InventoryHelper.convert(form);
		p.setId(productPojo.getId());
		service.addCheck(p);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public InventoryData get(int id) throws ApiException {
		InventoryPojo p = service.getCheck(id);
		ProductPojo productPojo =	productService.getCheck(p.getId());
		InventoryData data = InventoryHelper.convert(p);
		data.setBarcode(productPojo.getBarcode());
		return data;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public InventoryBarcodeData getByBarcode(String barcode) throws ApiException {
		InventoryHelper.checkBarcode(barcode);
		ProductPojo productPojo =	productService.getCheckByBarcode(barcode);
		InventoryPojo p = service.getCheck(productPojo.getId());
		InventoryBarcodeData data = new InventoryBarcodeData();
		data.setQuantity(p.getQuantity());
		data.setBarcode(productPojo.getBarcode());
		data.setMrp(productPojo.getMrp());
		return data;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public List<InventoryData> getAll() throws ApiException{
		List<InventoryData> results = new ArrayList<InventoryData>();
		List<InventoryPojo> list = service.getAll();
		for(InventoryPojo p : list) {
			ProductPojo productPojo =	productService.getCheck(p.getId());
			InventoryData data = InventoryHelper.convert(p);
			data.setBarcode(productPojo.getBarcode());
			results.add(data);
		}
		return results;
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void update(InventoryForm form) throws ApiException {
		InventoryHelper.checkNegative(form);
		ProductPojo productPojo =	productService.getCheckByBarcode(form.getBarcode());
		InventoryPojo p = InventoryHelper.convert(form);
		p.setId(productPojo.getId());
		service.update(productPojo.getId(), InventoryHelper.convert(form));
	}
}
