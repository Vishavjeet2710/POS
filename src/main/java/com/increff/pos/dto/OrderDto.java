package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.increff.pos.download.OrderDownload;
import com.increff.pos.download.SaleDownload;
import com.increff.pos.helper.OrderHelper;
import com.increff.pos.helper.OrderItemHelper;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderFormPost;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.model.SaleReportData;
import com.increff.pos.model.SaleReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.xmlRootElement.OrderXmlRootElement;
import com.increff.pos.xmlRootElement.SaleXmlRootElement;

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
	private BrandService brandService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Transactional(rollbackOn = ApiException.class)
	public void addOrder(OrderFormPost form) throws ApiException {
		int orderId=  orderService.addCheck(OrderHelper.convert(form));
		List<OrderItemForm> orderItemForms = form.getOrderItemForms();
		for(OrderItemForm orderItemForm : orderItemForms) {
			orderItemForm.setOrderId(orderId);
			ProductPojo productPojo = productService.getCheckByBarcode(orderItemForm.getBarcode());
			orderService.getCheck(orderId);
			InventoryPojo inventoryPojo = inventoryService.getCheck(productPojo.getId());
			
			OrderItemPojo p = OrderItemHelper.convert(orderItemForm);
			p.setProductId(productPojo.getId());
			OrderItemHelper.setSellingPrice(p,productPojo.getMrp());
			
			OrderItemHelper.updateInventory(p,inventoryPojo);
			inventoryService.update(productPojo.getId(), inventoryPojo);
			orderItemService.addCheck(p);
		}
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
	public void DownloadSaleReport(HttpServletRequest request, HttpServletResponse response,SaleReportForm saleReportForm) throws ApiException{
		String startDateTime = OrderHelper.getStartDateTime(saleReportForm);
		String endDateTime = OrderHelper.getEndDateTime(saleReportForm);
		List<OrderPojo> list = orderService.getDateTime(startDateTime, endDateTime);
		List<SaleReportData> saleReportDatas = new ArrayList<SaleReportData>();
		Map<String, Integer> Category_Quantity_Map = new HashMap<String, Integer>();
		Map<String, Double> Category_Mrp_Map = new HashMap<String, Double>();
		for(OrderPojo p : list) {
			List<OrderItemPojo> orderItemPojos = orderItemService.getByOrderIdBrandCategory(p.getId(), saleReportForm.getBrand(), saleReportForm.getCategory());
			for(OrderItemPojo orderItemPojo : orderItemPojos) {
				ProductPojo productPojo = productService.getCheck(orderItemPojo.getProductId());
				BrandPojo brandPojo = brandService.getCheck(productPojo.getBrand_category());
				if(Category_Quantity_Map.containsKey(brandPojo.getCategory())) {
					Category_Quantity_Map.put(brandPojo.getCategory(), Category_Quantity_Map.get(brandPojo.getCategory())+orderItemPojo.getQuantity());
					Category_Mrp_Map.put(brandPojo.getCategory(), (Category_Mrp_Map.get(brandPojo.getCategory())+(orderItemPojo.getSellingPrice()*orderItemPojo.getQuantity())));
				}else {
					Category_Quantity_Map.put(brandPojo.getCategory(), orderItemPojo.getQuantity());
					Category_Mrp_Map.put(brandPojo.getCategory(),(orderItemPojo.getSellingPrice()*orderItemPojo.getQuantity()));
				}
			}
		}
		for(String category: Category_Quantity_Map.keySet()) {
			SaleReportData saleReportData = new SaleReportData();
			saleReportData.setCategory(category);
			saleReportData.setQuantity(Category_Quantity_Map.get(category));
			saleReportData.setRevenue(Category_Mrp_Map.get(category));
			saleReportDatas.add(saleReportData);
		}
		SaleXmlRootElement saleXmlRootElement = new SaleXmlRootElement();
		saleXmlRootElement.setSaleReportDatas(saleReportDatas);
		SaleDownload.OrderDownloadHelper(saleXmlRootElement, request, response);
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
