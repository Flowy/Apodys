package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.TestovacieData;
import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class OneShiftAtTimeTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new OneShiftAtTime();

    @Test
    public void sameShiftTime() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0))
        );

        td.assertCrimes(2, investigator.findOffenders(planSmien));
    }

    @Test
    public void overlayedShiftTime() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                td.combine(td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0))
        );

        td.assertCrimes(2, investigator.findOffenders(planSmien));
    }

    @Test
    public void oneShiftAfterAnother() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0))
        );

        td.assertCrimes(0, investigator.findOffenders(planSmien));
    }
}