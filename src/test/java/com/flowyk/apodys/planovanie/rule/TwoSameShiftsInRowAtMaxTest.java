package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.test.TestovacieData;

import com.flowyk.apodys.planovanie.RuleInvestigator;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.flowyk.apodys.test.TestHelper.*;

public class TwoSameShiftsInRowAtMaxTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new TwoSameShiftsInRowAtMax();

    @Test
    public void threeShiftsInRow() {
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertBroken(investigator.findOffenders(planSmien));
    }

    @Test
    public void shiftsInRowDifferentEmployees() {
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(1))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

    @Test
    public void threeShiftsNotInRow() {
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

    @Test
    public void threeDifferentShiftsInRow() {
        List<Shift> planSmien = combine(
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(0))
        );

        assertValid(investigator.findOffenders(planSmien));
    }

}