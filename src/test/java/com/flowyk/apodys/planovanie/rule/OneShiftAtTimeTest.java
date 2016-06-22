package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collections;

import static com.flowyk.apodys.test.TestHelper.*;

public class OneShiftAtTimeTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new OneShiftAtTime();

    @Test
    @Ignore("can not happen as there is Set of shifts that are compared based on start time")
    public void sameShiftTime() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1)),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1))
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void overlayedShiftTime() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1)),
                td.predlohaP1C.vygenerujOd(LocalDate.of(2016, 5, 1))
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void oneShiftAfterAnother() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 1)),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1))
        );

        assertValid(investigator.findOffenders(Collections.singletonList(planSmien)));
    }
}