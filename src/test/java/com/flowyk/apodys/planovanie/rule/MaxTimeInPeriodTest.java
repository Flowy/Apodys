package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.flowyk.apodys.test.TestHelper.*;

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
        List<Shift> planSmien = combine(
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void manyHours() {
        List<Shift> planSmien = combine(
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void fewHours() {
        List<Shift> planSmien = combine(
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

    @Test
    public void manyHoursDifferentEmployees() {
        List<Shift> planSmien = combine(
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(1))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

}