package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
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

    private Shift prvaSmena() {
        Shift prvaSmena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 1), td.testovanaZona);
        prvaSmena.setZamestnanec(td.zamestnanci.get(0));
        return prvaSmena;
    }

    private Shift druhaSmena() {
        Shift druhaSmena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 2), td.testovanaZona);
        druhaSmena.setZamestnanec(td.zamestnanci.get(0));
        return druhaSmena;
    }

    private Shift tretiaSmena() {
        Shift druhaSmena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 3), td.testovanaZona);
        druhaSmena.setZamestnanec(td.zamestnanci.get(0));
        return druhaSmena;
    }
}