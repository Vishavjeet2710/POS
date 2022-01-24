package com.increff.pos.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.springTest.AbstractUnitTest;

public class BrandSericeTest extends AbstractUnitTest{
	@Autowired
	private BrandService service;
	
	@Test
	public void testAdd() {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("amul");
		brandPojo.setCategory("milk");
		service.add(brandPojo);
	}
}
