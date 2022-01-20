package com.increff.pos.converter;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.increff.pos.xmlRootElement.OrderXmlRootElement;

public class OrderXmlConverter {
	public static void jaxbObjectToXML(OrderXmlRootElement orderItemDatas) 
    {
        try
        {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(OrderXmlRootElement.class);
             
            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
          //Store XML to File
            File file = new File("orderList.xml");
             
            //Writes XML file to file-system
            jaxbMarshaller.marshal(orderItemDatas, file); 
 
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
