package com.increff.pos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.converter.BrandXmlConverter;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.fop.BrandFop;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.model.UpdateBrandForm;
import com.increff.pos.xmlRootElement.BrandXmlRootElement;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RequestMapping(value = "/api/brand")
@RestController(value = "")
public class BrandController {
	
	@Autowired
	private BrandDto dto;
	
	@ApiOperation(value = "Add a Brand Master")
	@PostMapping(value = "")
	public void add(@RequestBody BrandForm form) throws ApiException {
		dto.add(form);
	}

	@ApiOperation(value = "Get a Brand Master")
	@GetMapping(value = "/{id}")
	public BrandData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	@ApiOperation(value = "Get all Brand Masters")
	@GetMapping(value = "")
	public List<BrandData> getAll() {
		return dto.getAll();
	}

	@ApiOperation(value = "Update a Brand Master")
	@PutMapping(value = "/update")
	public void Update(@RequestBody UpdateBrandForm form) throws ApiException {
		dto.update(form);
	}
	
	@ApiOperation(value = "Convert Brand Object to XML")
	@GetMapping(value = "/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws ServletException, FOPException, IOException, TransformerException {
		BrandXmlRootElement brandDatas = new BrandXmlRootElement();
		brandDatas.setBrandDatas(getAll());
		BrandXmlConverter.jaxbObjectToXML(brandDatas);
		ByteArrayOutputStream out = BrandFop.downloadPDF();
		//Prepare response
        response.setContentType("application/pdf");
        response.setContentLength(out.size());
        
        System.out.println(out);
        
        //Send content to Browser
        response.getOutputStream().write(out.toByteArray());
        response.getOutputStream().flush();
	}
}
