package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.TestovacieData;
import com.flowyk.apodys.planovanie.pravidlo.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import static org.junit.Assert.*;

public class PlanovacSPravidlamiTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Ignore("comparing nothing")
    @Test
    public void testNaplanuj() throws Exception {
        PlanovacSPravidlami planovac = new PlanovacSPravidlami(td.zamestnanci, td.tyzdennyPlan);

        planovac.pridat(new PravidloMaximalnehoCasu(Duration.ofHours(33L), Period.ofDays(4)));
        planovac.pridat(new PravidloVolnoPoSmene(td.predlohaN2P, Duration.ofHours(12L)));
        planovac.pridat(new PravidloVolnoPoSmene(td.predlohaR2P, Duration.ofHours(1L)));
        planovac.pridat(new PravidloVolnoPoDvochSmenach(td.predlohaN2P, td.predlohaN2P, Duration.ofHours(48L)));
        planovac.pridat(new PravidloMaximalnehoCasu(Duration.ofHours(55L), Period.ofDays(7)));


        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 3),
                td.testovanaZona);
        for (Shift polozka: planSmien) {
            assertNotNull(polozka.vykonavatel());
        }
        System.out.println(planSmien);
    }
}