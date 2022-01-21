package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.download.OrderDownload;
import com.increff.pos.helper.OrderHelper;
import com.increff.pos.helper.OrderItemHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderFormPost;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.SaleReportData;
import com.increff.pos.model.SaleReportForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.xmlRootElement.OrderXmlRootElement;

@Service
@Validated
public class OrderDto {

	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Transactional(rollbackOn = ApiException.class)
	public int addOrder(OrderFormPost form) throws ApiException {
		return  orderService.addCheck(OrderHelper.convert(form));
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void addOrderItem(OrderItemForm form) throws ApiException {
		ProductPojo productPojo = productService.getCheckByBarcode(form.getBarcode());
		orderService.getCheck(form.getOrderId());
		InventoryPojo inventoryPojo = inventoryService.getCheck(productPojo.getId());
		
		OrderItemPojo p = OrderItemHelper.convert(form);
		p.setProductId(productPojo.getId());
		OrderItemHelper.setSellingPrice(p,productPojo.getMrp());
		
		OrderItemHelper.updateInventory(p,inventoryPojo);
		inventoryService.update(productPojo.getId(), inventoryPojo);
		orderItemService.addCheck(p);
	}
	
	@Transactional
	public List<List<OrderItemData>> getAll() throws ApiException{
		List<OrderPojo> orderDatas = orderService.getAll();
		List<List<OrderItemData>> results = new ArrayList<List<OrderItemData>>();
		for(OrderPojo p : orderDatas) {
			List<OrderItemPojo> list = orderItemService.getByOrderId(p.getId());
			List<OrderItemData> resultsItem = new ArrayList<OrderItemData>(); 
			for(OrderItemPojo orderItemPojo : list) {
				OrderItemData data = OrderItemHelper.convert(orderItemPojo);
				ProductPojo productPojo = productService.getCheck(orderItemPojo.getProductId());
				data.setMrp(data.getSellingPrice());;
				data.setBarcode(productPojo.getBarcode());
				resultsItem.add(data);
			}
			results.add(resultsItem);
		}
		return results;
	}
	// Delete this api......add use orderServide.getByOrderId for downloading
	@Transactional
	public List<OrderItemData> getByOrderId(int orderId) throws ApiException{
		List<OrderItemPojo> list = orderItemService.getByOrderId(orderId);
		List<OrderItemData> results = new ArrayList<OrderItemData>();
		for(OrderItemPojo p : list) {
			OrderItemData data = OrderItemHelper.convert(p);
			ProductPojo productPojo = productService.getCheck(p.getProductId());
			data.setMrp(data.getSellingPrice());;
			data.setBarcode(productPojo.getBarcode());
			results.add(data);
		}
		return results;
	}
	
	@Transactional
	public SaleReportData DownloadSaleReport(SaleReportForm saleReportForm) throws ApiException{
		System.out.println("I was called");
		String startDateTime = OrderHelper.getStartDateTime(saleReportForm);
		String endDateTime = OrderHelper.getEndDateTime(saleReportForm);
		List<OrderPojo> list = orderService.getDateTime(startDateTime, endDateTime);
		SaleReportData saleReportData = new SaleReportData();
		List<OrderData> orderDatas = new ArrayList<OrderData>();
		for(OrderPojo p : list) {
			OrderData orderData = new OrderData();
			orderData.setId(p.getId());
			orderDatas.add(orderData);
		}
		saleReportData.setOrderDatas(orderDatas);
		return saleReportData;
	}
	
	
	public void download(HttpServletRequest request, HttpServletResponse response, int id) throws ApiException {
		OrderXmlRootElement orderDatas = new OrderXmlRootElement();
		List<OrderItemPojo> list = orderItemService.getByOrderId(id);
		List<OrderItemData> results = new ArrayList<OrderItemData>();
		for(OrderItemPojo p : list) {
			OrderItemData data = OrderItemHelper.convert(p);
			ProductPojo productPojo = productService.getCheck(p.getProductId());
			data.setMrp(data.getSellingPrice());;
			data.setBarcode(productPojo.getBarcode());
			results.add(data);
		}
		orderDatas.setOrderItemDatas(results);
		OrderPojo orderPojo = orderService.get(id);
		orderDatas.setDateTime(orderPojo.getTime());
		orderDatas.setOrderId(id);
		OrderDownload.OrderDownloadHelper(orderDatas, request, response);
	}
}
