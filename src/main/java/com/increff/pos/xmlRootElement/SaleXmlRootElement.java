package com.increff.pos.xmlRootElement;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.increff.pos.model.SaleReportData;

@XmlRootElement
public class SaleXmlRootElement {
	private List<SaleReportData> saleReportDatas;

	public List<SaleReportData> getSaleReportDatas() {
		return saleReportDatas;
	}

	public void setSaleReportDatas(List<SaleReportData> saleReportDatas) {
		this.saleReportDatas = saleReportDatas;
	}
}
