package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.XmlExport;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.planner.PatternPlanner;
import com.flowyk.apodys.planovanie.planner.ZakladnyPlanovac;
import com.flowyk.apodys.test.TestovacieData;
import com.google.common.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.flowyk.apodys.test.TestHelper.combine;

public class ExportTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    TestovacieData td;
    Export export;
    Context context;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
        export = new Export();
        context = new Context(new Export());
        context.getShiftTemplates().addAll(td.predlohaN2P, td.predlohaO75, td.predlohaP1C, td.predlohaR2P);
    }

    @Test
    public void testRead() throws Exception {
        File file = new File(this.getClass().getResource("testReadFile.xml").toURI());
        XmlExport data = export.read(file);

//        data.getShifts().forEach(shift -> Assert.assertNotNull(shift.getEmployee()));

    }

    //TODO: move somewhere else - this is test of planner
    @Test
    public void testSave() throws Exception {
        List<EmployeeShifts> employeeShifts = context.getEmployeeShifts();
        employeeShifts.addAll(combine(td.zamestnanci));
        Planovac planovac = new ZakladnyPlanovac(td.tyzdennyPlan);
        planovac.naplanuj(
                employeeShifts,
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 6));
        File file = File.createTempFile("testSave", ".xml");
        context.saveTo(file);

        logger.info("Result file:///" + file.getAbsolutePath());
    }

    @Test
    public void exportPatternPlanned() throws IOException {
        List<EmployeeShifts> employeeShifts = context.getEmployeeShifts();
        employeeShifts.addAll(combine(Arrays.asList(td.zamestnanci.get(0), td.zamestnanci.get(1))));
        Planovac planovac = new PatternPlanner(Arrays.asList(td.tyzden40(), td.tyzden32()));
        planovac.naplanuj(
                employeeShifts,
                LocalDate.of(2016, 6, 22),
                LocalDate.of(2016, 7, 30));
        File file = File.createTempFile("testSave", ".xml");
        context.saveTo(file);

        logger.info("Result file:///" + file.getAbsolutePath());
    }
}