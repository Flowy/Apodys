package com.flowyk.apodys.ui.export;

import com.flowyk.apodys.ui.Context;
import com.flowyk.apodys.ui.config.event.ContextUpdated;
import com.google.common.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

@Singleton
public class ExportService {

    @Inject
    private EventBus eventBus;

    public Context read(File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ApodysData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ApodysData data = (ApodysData) unmarshaller.unmarshal(file);
            //TODO: change to XmlLoaded
            eventBus.post(new ContextUpdated(data));
            //TODO: delete and catch with event
            return new Context(data.getPlanSmien(), data.getSmeny() != null ? data.getSmeny() : new ArrayList<>());
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
