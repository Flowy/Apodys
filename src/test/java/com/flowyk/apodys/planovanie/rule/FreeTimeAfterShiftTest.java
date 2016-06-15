package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.PlanSmien;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static com.flowyk.apodys.test.TestHelper.*;

public class FreeTimeAfterShiftTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new FreeTimeAfterShift(LocalTime.of(18, 0), Duration.ofHours(12L));

    @Test
    public void freeTime() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );
        assertValid(investigator.findOffenders(planSmien));
    }

    @Test
    public void freeTimeTwoEmployees() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(1))
        );
        assertValid(investigator.findOffenders(planSmien));
    }

    @Test
    public void overloaded() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );
        assertBroken(investigator.findOffenders(planSmien));
    }

    //free time after 2 shifts
    private RuleInvestigator investigator2 = new FreeTimeAfterShift(2, LocalTime.of(18, 0), Duration.ofHours(24L));

    @Test
    public void freeTime2() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 4), td.testovanaZona), td.zamestnanci.get(0))
        );
        assertValid(investigator2.findOffenders(planSmien));
    }

    @Test
    public void freeTime2TwoEmployees() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(1))
        );
        assertValid(investigator2.findOffenders(planSmien));
    }

    @Test
    public void overloaded2() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(0))
        );
        assertBroken(investigator2.findOffenders(planSmien));
    }

}