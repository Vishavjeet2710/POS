package com.increff.pos.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.springTest.AbstractUnitTest;


public class OrderDtoTest extends AbstractUnitTest{
	@Autowired
	private OrderDto dto;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private InventoryService inventoryService;
	
	private String barcodeTest;
	private double mrpTest;
	
	@Before
	public void init() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("nike");
		brandPojo.setCategory("clothes");
		brandService.add(brandPojo);
		int idBrand = brandService.add(brandPojo);
		ProductPojo productPojo = new ProductPojo();
		productPojo.setBrand_category(idBrand);
		productPojo.setBarcode("#1111");
		productPojo.setMrp(9999.99);
		productPojo.setName("Jacket");
		productService.add(productPojo);
		barcodeTest="#1111";
		mrpTest=9999.99;
		InventoryPojo inventoryPojo = new InventoryPojo();
		inventoryPojo.setId(productPojo.getId());
		inventoryPojo.setQuantity(20);
		inventoryService.add(inventoryPojo);
	}
	
	@Test
	public void testAdd() throws ApiException {
		List<OrderItemForm> forms = new ArrayList<OrderItemForm>();
		OrderItemForm form1 = new OrderItemForm();
		form1.setBarcode(barcodeTest);
		form1.setMrp(mrpTest);
		form1.setQuantity(5);
		forms.add(form1);
		OrderForm orderForm = new OrderForm();
		orderForm.setOrderItemForms(forms);
		dto.addOrder(orderForm);
		assertEquals(1, dto.getAll().size());
	}
	
	@Test
	public void testGet() throws ApiException {
		List<OrderItemForm> forms = new ArrayList<OrderItemForm>();
		OrderItemForm form1 = new OrderItemForm();
		form1.setBarcode(barcodeTest);
		form1.setMrp(mrpTest);
		form1.setQuantity(5);
		forms.add(form1);
		OrderForm orderForm = new OrderForm();
		orderForm.setOrderItemForms(forms);
		int id = dto.addOrder(orderForm);
		OrderData orderData = dto.getByOrderId(id);
		List<OrderItemData> orderItemDatas = orderData.getOrderItemDatas();
		assertEquals(barcodeTest, orderItemDatas.get(0).getBarcode());
		assertEquals(new Double(9999.99), orderItemDatas.get(0).getMrp());
		assertEquals(new Integer(5), orderItemDatas.get(0).getQuantity());
	}
}
