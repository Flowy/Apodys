package com.flowyk.apodys;

import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class PlanovacTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testNaplanuj() throws Exception {
        Planovac planovac = new Planovac(td.zamestnanci, td.tyzdennyPlan);
        PlanSmien planSmien = planovac.naplanuj(
                ZonedDateTime.of(2015, 11, 30, 0, 0, 0, 0, td.testovanaZona),
                ZonedDateTime.of(2015, 12, 27, 0, 0, 0, 0, td.testovanaZona));
        for (PolozkaPlanu polozka: planSmien.polozky) {
            assertNotNull(polozka.vykonavatel());
        }
    }
}