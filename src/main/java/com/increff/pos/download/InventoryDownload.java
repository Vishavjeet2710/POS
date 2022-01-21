package com.increff.pos.download;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

import com.increff.pos.converter.InventoryXmlConverter;
import com.increff.pos.fop.InventoryFop;
import com.increff.pos.model.ApiException;
import com.increff.pos.xmlRootElement.InventoryXmlRootElement;

public class InventoryDownload {
	public static void InventoryDownloadHelper(InventoryXmlRootElement inventoryDatas,HttpServletRequest request, HttpServletResponse response) throws ApiException {
		InventoryXmlConverter.jaxbObjectToXML(inventoryDatas);
		ByteArrayOutputStream out;
		try {
			out = InventoryFop.downloadPDF();
		} catch (FOPException | IOException | TransformerException e) {
			throw new ApiException("Error occured while using Apache Fop"+e);
		}
		//Prepare response
        response.setContentType("application/pdf");
        response.setContentLength(out.size());
        
        //Send content to Browser
        try {
			response.getOutputStream().write(out.toByteArray());
		} catch (IOException e) {
			throw new ApiException("Input Output Exception while converting pdf to byte array"+e);
		}
        try {
			response.getOutputStream().flush();
		} catch (IOException e) {
			throw new ApiException("Error occured while flushing output stream");
		}
	}
}