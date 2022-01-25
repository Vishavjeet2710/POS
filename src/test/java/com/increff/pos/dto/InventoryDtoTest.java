package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.InventoryBarcodeData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.springTest.AbstractUnitTest;

public class InventoryDtoTest extends AbstractUnitTest {
	@Autowired
	private InventoryDto dto;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	
	private String barcodeTest;
	
	@Before
	public void init() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("nike");
		brandPojo.setCategory("clothes");
		int idBrand = brandService.add(brandPojo);
		ProductPojo productPojo = new ProductPojo();
		productPojo.setBrand_category(idBrand);
		productPojo.setBarcode("#1111");
		productPojo.setMrp(9999.99);
		productPojo.setName("Jacket");
		productService.add(productPojo);
		barcodeTest="#1111";
	}
	
	@Test
	public void testAdd() throws ApiException {
		InventoryForm inventoryForm = new InventoryForm();
		inventoryForm.setBarcode(barcodeTest);
		inventoryForm.setQuantity(20);
		dto.add(inventoryForm);
		assertEquals(1, dto.getAll().size());
	}
	
	@Test
	public void testGet() throws ApiException {
		InventoryForm inventoryForm = new InventoryForm();
		inventoryForm.setBarcode(barcodeTest);
		inventoryForm.setQuantity(20);
		dto.add(inventoryForm);
		InventoryBarcodeData inventoryBarcodeData = dto.getByBarcode(barcodeTest);
		assertEquals(20, inventoryBarcodeData.getQuantity());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		InventoryForm inventoryForm = new InventoryForm();
		inventoryForm.setBarcode(barcodeTest);
		inventoryForm.setQuantity(20);
		dto.add(inventoryForm);
		inventoryForm.setQuantity(50);
		dto.update(inventoryForm);
		InventoryBarcodeData inventoryBarcodeData = dto.getByBarcode(barcodeTest);
		assertEquals(50, inventoryBarcodeData.getQuantity());
	}
}
