package com.increff.pos.xmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.increff.pos.model.InventoryData;

@XmlRootElement
public class InventoryXmlRootElement {
	private List<InventoryData> inventoryDatas;

	public List<InventoryData> getInventoryDatas() {
		return inventoryDatas;
	}

	public void setInventoryDatas(List<InventoryData> inventoryDatas) {
		this.inventoryDatas = inventoryDatas;
	}
}
