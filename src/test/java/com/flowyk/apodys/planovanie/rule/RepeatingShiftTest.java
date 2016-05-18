package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.TestovacieData;
import static org.junit.Assert.*;

import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.planovanie.RuleOffender;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class RepeatingShiftTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    private TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    private RuleInvestigator investigator = new RepeatingShift();

    @Test
    public void threeShiftsInRow() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(0))
        );

        Collection<RuleOffender> crimes = investigator.findOffenders(planSmien);
        assertTrue("Found " + crimes.size() + " instead of ONE", crimes.size() == 1);
    }

    @Test
    public void shiftsInRowDifferentEmployees() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(1))
        );

        Collection<RuleOffender> crimes = investigator.findOffenders(planSmien);
        assertTrue("Found " + crimes.size() + " instead of ZERO", crimes.size() == 0);
    }

    @Test
    public void threeShiftsNotInRow() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0))
        );

        Collection<RuleOffender> crimes = investigator.findOffenders(planSmien);
        assertTrue("Found " + crimes.size() + " instead of ZERO", crimes.isEmpty());
    }

    @Test
    public void threeDifferentShiftsInRow() {
        PlanSmien planSmien = combine(new PlanSmien(new ArrayList<>(), td.zamestnanci),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 1), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaN2P.vygenerujOd(LocalDate.of(2016, 5, 2), td.testovanaZona), td.zamestnanci.get(0)),
                combine(td.predlohaR2P.vygenerujOd(LocalDate.of(2016, 5, 3), td.testovanaZona), td.zamestnanci.get(0))
        );

        Collection<RuleOffender> crimes = investigator.findOffenders(planSmien);
        assertTrue("Found " + crimes.size() + " instead of ZERO", crimes.isEmpty());
    }

    private Shift combine(Shift shift, Zamestnanec employee) {
        shift.setZamestnanec(employee);
        return shift;
    }

    private PlanSmien combine(PlanSmien planSmien, Shift... shifts) {
        for (Shift shift: shifts) {
            planSmien.pridatPolozku(shift);
        }
        return planSmien;
    }

}