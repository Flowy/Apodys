package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;

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
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                combine(td.predlohaO75.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void onlySunday() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void onlySaturday() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void differentEmployee() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(1))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void sameShifts() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 7)), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 8)), td.zamestnanci.get(0))
        );

        assertValid(investigator.findOffenders(planSmien));
    }
}