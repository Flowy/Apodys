package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.TestovacieData;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class SameShiftOnWeekendTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new OneShiftAtTime();

    @Test
    public void differentShift() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                td.combine(td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        td.assertCrimes(2, investigator.findOffenders(planSmien));
    }

    @Test
    public void onlySunday() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        td.assertCrimes(1, investigator.findOffenders(planSmien));
    }

    @Test
    public void onlySaturday() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0))
        );

        td.assertCrimes(1, investigator.findOffenders(planSmien));
    }

    @Test
    public void differentEmployee() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(1))
        );

        td.assertCrimes(2, investigator.findOffenders(planSmien));
    }

    @Test
    public void sameShifts() {
        PlanSmien planSmien = td.combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                td.combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        td.assertCrimes(0, investigator.findOffenders(planSmien));
    }
}