package com.increff.pos.helper;

import java.util.ArrayList;
import java.util.List;

import com.increff.pos.model.ApiException;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderFormUpdate;
import com.increff.pos.model.SaleReportForm;
import com.increff.pos.model.TimeData;
import com.increff.pos.pojo.OrderPojo;

public class OrderHelper {
	public static OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setTime(p.getTime());
		d.setId(p.getId());
		return d;
	}

	public static OrderPojo convert(OrderForm form) {
		TimeData timeData = new TimeData();
		OrderPojo p = new OrderPojo();
		p.setTime(timeData.getTime());
		return p;
	}
	
	public static OrderPojo convert(OrderFormUpdate form) {
		OrderPojo p = new OrderPojo();
		p.setTime(form.getTime());
		return p;
	}
	
	public static void trimSpaces(OrderFormUpdate form) {
		form.setTime(form.getTime().toLowerCase().trim());
	}

	public static List<OrderData> convert(List<OrderPojo> list) {
		List<OrderData> results = new ArrayList<OrderData>();
		for(OrderPojo p : list) {
			results.add(convert(p));
		}
		return results;
	}

	public static String getEndDateTime(SaleReportForm saleReportForm) throws ApiException {
		if(saleReportForm.getEndDateTime()==null || saleReportForm.getEndDateTime()=="") {
			throw new ApiException("Select some end date time");
		}
		saleReportForm.setBrand(saleReportForm.getBrand().trim().toLowerCase());
		saleReportForm.setCategory(saleReportForm.getCategory().trim().toLowerCase());
		if(saleReportForm.getBrand()==null || saleReportForm.getBrand()=="") {
			throw new ApiException("Please enter some brand");
		}
		saleReportForm.setBrand(saleReportForm.getBrand().trim().toLowerCase());
		saleReportForm.setCategory(saleReportForm.getCategory().trim().toLowerCase());
		if(saleReportForm.getBrand()==null || saleReportForm.getBrand().isEmpty()) {
			throw new ApiException("Brand can not be null or empty");
		}
		return saleReportForm.getEndDateTime();
	}

	public static String getStartDateTime(SaleReportForm saleReportForm) throws ApiException {
		if(saleReportForm.getStartDateTime()==null || saleReportForm.getStartDateTime()=="") {
			throw new ApiException("Select some start date time");
		}
		return saleReportForm.getStartDateTime();
	}
}
