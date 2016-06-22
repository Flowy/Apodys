package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;

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
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona)
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void manyHours() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona)
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void fewHours() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona)
        );

        assertValid(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

}