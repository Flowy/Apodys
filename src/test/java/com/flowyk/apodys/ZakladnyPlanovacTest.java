package com.flowyk.apodys;

import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.ZakladnyPlanovac;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class ZakladnyPlanovacTest {

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
        for (PolozkaPlanu polozka: planSmien) {
            assertNotNull(polozka.vykonavatel());
        }
    }
}