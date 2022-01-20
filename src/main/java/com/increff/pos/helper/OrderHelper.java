package com.increff.pos.helper;

import java.util.ArrayList;
import java.util.List;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderFormPost;
import com.increff.pos.model.OrderFormUpdate;
import com.increff.pos.model.TimeData;
import com.increff.pos.pojo.OrderPojo;

public class OrderHelper {
	public static OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setTime(p.getTime());
		d.setId(p.getId());
		return d;
	}

	public static OrderPojo convert(OrderFormPost form) {
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
}
