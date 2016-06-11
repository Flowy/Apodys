package com.flowyk.apodys.ui.export;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.planovanie.planner.PatternPlanner;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.planner.ZakladnyPlanovac;
import com.flowyk.apodys.ui.Context;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExportServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

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
        Context context = new Context(planSmien, Arrays.asList(td.predlohaN2P, td.predlohaO75, td.predlohaP1C, td.predlohaR2P));
        File file = File.createTempFile("testSave", ".xml");
        new ExportService().save(file, context);

        logger.info("Result file:///" + file.getAbsolutePath());
    }

    @Test
    public void exportPatternPlanned() throws IOException {
        List<Zamestnanec> employees = Arrays.asList(td.zamestnanci.get(0), td.zamestnanci.get(1));
        Planovac planovac = new PatternPlanner(employees, Arrays.asList(td.tyzden40(), td.tyzden32()));
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2016, 6, 1),
                LocalDate.of(2016, 6, 14),
                td.testovanaZona
        );
        Context context = new Context(planSmien, Arrays.asList(td.predlohaN2P, td.predlohaO75, td.predlohaP1C, td.predlohaR2P));
        File file = File.createTempFile("testSave", ".xml");
        new ExportService().save(file, context);

        logger.info("Result file:///" + file.getAbsolutePath());
    }
}