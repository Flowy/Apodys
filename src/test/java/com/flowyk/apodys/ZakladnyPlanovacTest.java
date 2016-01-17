package com.flowyk.apodys;

import com.flowyk.apodys.export.ApodysData;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.ZakladnyPlanovac;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class ZakladnyPlanovacTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testNaplanuj() throws Exception {
        Planovac planovac = new ZakladnyPlanovac(td.zamestnanci, td.tyzdennyPlan);
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 27),
                td.testovanaZona);
        for (PolozkaPlanu polozka : planSmien) {
            assertNotNull(polozka.vykonavatel());
        }
    }

    @Test
    public void printXML() throws JAXBException {
        Planovac planovac = new ZakladnyPlanovac(td.zamestnanci, td.tyzdennyPlan);
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 27),
                td.testovanaZona);
        JAXBContext jaxbContext = JAXBContext.newInstance(ApodysData.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "TODO: schema location");
        marshaller.marshal(new ApodysData(planSmien, td.zamestnanci), System.out);
    }
}