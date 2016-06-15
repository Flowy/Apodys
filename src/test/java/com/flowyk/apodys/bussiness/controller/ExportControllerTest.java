package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.XmlDataWrapper;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.planner.PatternPlanner;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.planner.ZakladnyPlanovac;
import com.flowyk.apodys.ui.Context;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExportControllerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testRead() throws Exception {
        File file = new File(this.getClass().getResource("testReadFile.xml").toURI());
        XmlDataWrapper data = new ExportController().read(file);

        data.getShifts().forEach(shift -> Assert.assertNotNull(shift.getEmployee()));

    }

    @Test
    public void testSave() throws Exception {
        Planovac planovac = new ZakladnyPlanovac(td.tyzdennyPlan);
        List<Shift> shifts = planovac.naplanuj(
                td.zamestnanci,
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 6),
                td.testovanaZona);
        Context context = new Context(shifts, td.zamestnanci, Arrays.asList(td.predlohaN2P, td.predlohaO75, td.predlohaP1C, td.predlohaR2P));
        File file = File.createTempFile("testSave", ".xml");
        new ExportController().save(file, context);

        logger.info("Result file:///" + file.getAbsolutePath());
    }

    @Test
    public void exportPatternPlanned() throws IOException {
        List<Zamestnanec> employees = Arrays.asList(td.zamestnanci.get(0), td.zamestnanci.get(1));
        Planovac planovac = new PatternPlanner(Arrays.asList(td.tyzden40(), td.tyzden32()));
        List<Shift> shifts = planovac.naplanuj(
                employees,
                LocalDate.of(2016, 6, 16),
                LocalDate.of(2016, 6, 30),
                td.testovanaZona
        );
        Context context = new Context(shifts, td.zamestnanci, Arrays.asList(td.predlohaN2P, td.predlohaO75, td.predlohaP1C, td.predlohaR2P));
        File file = File.createTempFile("testSave", ".xml");
        new ExportController().save(file, context);

        logger.info("Result file:///" + file.getAbsolutePath());
    }
}