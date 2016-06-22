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
import java.time.LocalTime;
import java.util.Collections;

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
        EmployeeShifts planSmien = combine(
                td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona)
        );
        assertValid(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void overloaded() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona)
        );
        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    private RuleInvestigator investigator2 = new FreeTimeAfterShift(2, LocalTime.of(18, 0), Duration.ofHours(24L));

    @Test
    public void freeTime2() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 4), td.testovanaZona)
        );
        assertValid(investigator2.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void overloaded2() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona)
        );
        assertBroken(investigator2.findOffenders(Collections.singletonList(planSmien)));
    }

}