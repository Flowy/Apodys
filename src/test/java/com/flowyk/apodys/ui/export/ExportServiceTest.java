package com.flowyk.apodys.ui.export;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.TestovacieData;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.ZakladnyPlanovac;
import com.flowyk.apodys.ui.Context;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class ExportServiceTest {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testRead() throws Exception {
        File file = new File(this.getClass().getResource("testReadFile.xml").toURI());
        Context context = new ExportService().read(file);

    }

    @Test
    public void testSave() throws Exception {
        Planovac planovac = new ZakladnyPlanovac(td.zamestnanci, td.tyzdennyPlan);
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 6),
                td.testovanaZona);
        Context context = new Context(planSmien, td.zamestnanci, Arrays.asList());
        context.setEmployees(td.zamestnanci);
        context.setWorkplan(planSmien);
        File file = File.createTempFile("testSave", ".xml");
        new ExportService().save(file, context);

        logger.info("Result file: " + file.getAbsolutePath());
    }
}