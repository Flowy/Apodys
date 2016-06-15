package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

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
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                combine(td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void onlySunday() {
        List<Shift> planSmien = combine(
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void onlySaturday() {
        List<Shift> planSmien = combine(
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void differentEmployee() {
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(1))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void sameShifts() {
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        assertValid(investigator.findOffenders(planSmien));
    }
}