package com.increff.pos.xmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.increff.pos.model.OrderItemData;

@XmlRootElement
public class OrderXmlRootElement {
	private List<OrderItemData> orderItemDatas;
	private int orderId;
	private String dateTime;
	private String date;
	private String time;
	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<OrderItemData> getOrderItemDatas() {
		return orderItemDatas;
	}
	
	public void setOrderItemDatas(List<OrderItemData> orderItemDatas) {
		this.orderItemDatas = orderItemDatas;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String string) {
		date = string.substring(0, 10);
		time = string.substring(11, 19);
		this.dateTime = string;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}	
}
