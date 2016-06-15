package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.ui.Context;
import com.flowyk.apodys.bussiness.entity.ApodysData;

import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Singleton
public class ExportController {

    public ApodysData read(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ApodysData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (ApodysData) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public void save(File file, Context context) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ApodysData.class);

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new ApodysData(context.getWorkplan(), context.getShiftTemplates()), file);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}