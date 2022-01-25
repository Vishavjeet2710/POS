package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.UpdateBrandForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.springTest.AbstractUnitTest;

@Transactional
public class BrandDtoTest extends AbstractUnitTest{

	@Autowired
	private BrandDto dto;
	
	@Autowired
	private BrandService service;
	
	private int idTest;
	
	@Before
	public void init() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("nike");
		brandPojo.setCategory("clothes");
		idTest = service.add(brandPojo);
	}
	
	@Test
	public void testAdd() throws ApiException {
		BrandForm brandForm = new BrandForm();
		brandForm.setBrand("amul");
		brandForm.setCategory("milk");
		dto.add(brandForm);
		assertEquals(2, dto.getAll().size());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandData brandData = dto.get(idTest);
		assertEquals("nike", brandData.getBrand());
		assertEquals("clothes", brandData.getCategory());
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("mcd");
		brandPojo.setCategory("food");
		int id = service.add(brandPojo);
		UpdateBrandForm updatebrandForm = new UpdateBrandForm();
		updatebrandForm.setBrand("  kfc  ");
		updatebrandForm.setCategory("  chicken  ");
		updatebrandForm.setExBrand("mcd");
		updatebrandForm.setExCategory("food");
		dto.update(updatebrandForm);
		BrandData brandData = dto.get(id);
		assertEquals("kfc", brandData.getBrand());
		assertEquals("chicken", brandData.getCategory());
	}
}
