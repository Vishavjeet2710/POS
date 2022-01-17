package com.increff.pos.xmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.increff.pos.model.BrandData;

@XmlRootElement
public class BrandXmlRootElement {
	private List<BrandData> brandDatas;

	public List<BrandData> getBrandDatas() {
		return brandDatas;
	}

	public void setBrandDatas(List<BrandData> brandDatas) {
		this.brandDatas = brandDatas;
	}
}
