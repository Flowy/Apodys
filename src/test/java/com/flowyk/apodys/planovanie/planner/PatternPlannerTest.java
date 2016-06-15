package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.PlanSmien;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class PatternPlannerTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testNaplanuj() throws Exception {
        List<Zamestnanec> zamestnanci = Arrays.asList(td.zamestnanci.get(0));
        Planovac planovac = new PatternPlanner(zamestnanci, Arrays.asList(td.tyzden40(), td.tyzden32()));
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2015, 6, 1),
                LocalDate.of(2015, 6, 14),
                td.testovanaZona);
//        for (Shift polozka : planSmien) {
//            assertNotNull(polozka.vykonavatel());
//        }
    }

}