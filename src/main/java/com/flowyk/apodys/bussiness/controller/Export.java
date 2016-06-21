package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.XmlExport;

import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Singleton
public class Export {

    XmlExport read(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlExport.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (XmlExport) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    void save(File file, XmlExport xmlExport) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlExport.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(xmlExport, file);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
