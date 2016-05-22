package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.TestovacieData;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import static com.flowyk.apodys.TestHelper.*;

public class MaxTimeInPeriodTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new MaxTimeInPeriod(Duration.ofHours(21L), Period.ofDays(4));

    @Test
    public void overridingShiftsLong() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void manyHours() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void fewHours() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

    @Test
    public void manyHoursDifferentEmployees() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(1))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

}