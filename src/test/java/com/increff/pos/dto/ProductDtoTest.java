package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.springTest.AbstractUnitTest;

public class ProductDtoTest extends AbstractUnitTest{
	@Autowired
	private ProductDto dto;
	
	@Autowired
	private BrandService brandService;
	
	@Before
	public void init() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("nike");
		brandPojo.setCategory("clothes");
		brandService.add(brandPojo);
	}
	
	@Test
	public void testAdd() throws ApiException {
		ProductForm productForm = new ProductForm();
		productForm.setBarcode("1#001");
		productForm.setBrand("nike");
		productForm.setCategory("clothes");
		productForm.setName("Air");
		productForm.setMrp(7000.00);
		dto.add(productForm);
		assertEquals(1, dto.getAll().size());
	}
	
	@Test
	public void testGet() throws ApiException {
		ProductForm productForm = new ProductForm();
		productForm.setBarcode("1#002   ");
		productForm.setBrand("nike        ");
		productForm.setCategory("clothes");
		productForm.setName("Air2");
		productForm.setMrp(70000.00);
		int id = dto.add(productForm);
		ProductData productData = dto.get(id);
		assertEquals("nike", productData.getBrand());
		assertEquals("1#002", productData.getBarcode());
//		assertEquals(7000.0, productData.getMrp());
		assertEquals("clothes", productData.getCategory());
		assertEquals("air2", productData.getName());
		assertEquals(id, productData.getId());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		ProductForm productForm = new ProductForm();
		productForm.setBarcode("1#002   ");
		productForm.setBrand("nike        ");
		productForm.setCategory("clothes");
		productForm.setName("Air2");
		productForm.setMrp(70000.00);
		int id = dto.add(productForm);
		ProductForm productForm2 = new ProductForm();
		productForm2.setBarcode("1#002   ");
		productForm2.setBrand("nike        ");
		productForm2.setCategory("clothes");
		productForm2.setName("Jacket");
		productForm2.setMrp(5000.00);
		dto.update(productForm2);
		ProductData productData = dto.get(id);
		assertEquals("nike", productData.getBrand());
		assertEquals("1#002", productData.getBarcode());
		assertEquals("clothes", productData.getCategory());
		assertEquals("jacket", productData.getName());
	}
}
