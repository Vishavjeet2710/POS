package com.increff.pos.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.springTest.AbstractUnitTest;

public class ProductServiceTest  extends AbstractUnitTest{
	@Autowired
	private ProductService service;
	
	@Test
	public void testAdd() {
		ProductPojo productPojo = new ProductPojo();
		productPojo.setBrand_category(1);
		productPojo.setName("Fat-4Per");
		productPojo.setMrp(50.00);
		productPojo.setBarcode("1@#111");
		service.add(productPojo);
	}
}
