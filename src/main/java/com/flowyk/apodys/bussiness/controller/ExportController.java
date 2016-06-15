package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.ui.Context;
import com.flowyk.apodys.bussiness.entity.XmlDataWrapper;

import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Singleton
public class ExportController {

    public XmlDataWrapper read(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlDataWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (XmlDataWrapper) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save(File file, Context context) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlDataWrapper.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(
                    new XmlDataWrapper(context.getShifts(), context.getEmployees(), context.getShiftTemplates()), file);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
