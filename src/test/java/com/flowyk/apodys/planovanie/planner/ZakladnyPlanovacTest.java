package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.test.TestovacieData;
import com.flowyk.apodys.planovanie.Planovac;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ZakladnyPlanovacTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testNaplanuj() throws Exception {
        Planovac planovac = new ZakladnyPlanovac(td.zamestnanci, td.tyzdennyPlan);
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 27),
                td.testovanaZona);
        for (Shift polozka : planSmien) {
            assertNotNull(polozka.vykonavatel());
        }
    }
}