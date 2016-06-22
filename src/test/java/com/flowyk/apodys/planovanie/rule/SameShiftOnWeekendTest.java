package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Collections;

import static com.flowyk.apodys.test.TestHelper.*;

public class SameShiftOnWeekendTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new SameShiftOnWeekend();

    @Test
    public void differentShift() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)),
                td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 8))
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void onlySunday() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 8))
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void onlySaturday() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 7))
        );

        assertBroken(investigator.findOffenders(Collections.singletonList(planSmien)));
    }

    @Test
    public void sameShifts() {
        EmployeeShifts planSmien = combine(td.zamestnanci.get(0),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)),
                td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8))
        );

        assertValid(investigator.findOffenders(Collections.singletonList(planSmien)));
    }
}