package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.TestovacieData;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class MaximalneOpakovanieSmienTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void druheOpakovanie() {
        PlanSmien plan = new PlanSmien();

        plan.pridatPolozku(prvaSmena());
        assertEquals(VysledokKontrolyPravidla.OK, new MaximalneOpakovanieSmien(2).over(plan, druhaSmena()));
    }

    @Test
    public void tretieOpakovanie() {
        PlanSmien plan = new PlanSmien();
        plan.pridatPolozku(prvaSmena());
        plan.pridatPolozku(druhaSmena());

        assertEquals(VysledokKontrolyPravidla.BROKEN, new MaximalneOpakovanieSmien(2).over(plan, tretiaSmena()));
    }

    private PolozkaPlanu prvaSmena() {
        PolozkaPlanu prvaSmena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 1), td.testovanaZona);
        prvaSmena.setZamestnanec(td.zamestnanci.get(0));
        return prvaSmena;
    }

    private PolozkaPlanu druhaSmena() {
        PolozkaPlanu druhaSmena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 2), td.testovanaZona);
        druhaSmena.setZamestnanec(td.zamestnanci.get(0));
        return druhaSmena;
    }

    private PolozkaPlanu tretiaSmena() {
        PolozkaPlanu druhaSmena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 3), td.testovanaZona);
        druhaSmena.setZamestnanec(td.zamestnanci.get(0));
        return druhaSmena;
    }
}