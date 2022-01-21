package com.increff.pos.xmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.increff.pos.model.ProductData;

@XmlRootElement
public class ProductXmlRootElement {
	private List<ProductData> productDatas;

	public List<ProductData> getProductDatas() {
		return productDatas;
	}

	public void setProductDatas(List<ProductData> productDatas) {
		this.productDatas = productDatas;
	}
}
