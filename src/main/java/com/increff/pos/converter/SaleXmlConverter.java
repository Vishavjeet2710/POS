package com.increff.pos.converter;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.increff.pos.xmlRootElement.SaleXmlRootElement;

public class SaleXmlConverter {
	public static void jaxbObjectToXML(SaleXmlRootElement saleDatas) 
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(SaleXmlRootElement.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
          //Store XML to File
            File file = new File("saleList.xml");
             
            //Writes XML file to file-system
            jaxbMarshaller.marshal(saleDatas, file); 
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
