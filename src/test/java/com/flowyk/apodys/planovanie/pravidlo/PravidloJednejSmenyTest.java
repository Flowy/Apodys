package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.TestovacieData;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PravidloJednejSmenyTest {

    TestovacieData td;

    @Before
    public void setUp() {
        td = new TestovacieData();
    }

    /**
     * Pokus vytvorit dve rovnake smeny
     */
    @Test
    public void porusenie() {
        PlanSmien plan = new PlanSmien();
        PolozkaPlanu smena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 1), td.testovanaZona);
        smena.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(smena);

        assertEquals(VysledokKontrolyPravidla.BROKEN, new PravidloJednejSmeny().over(plan, smena));
    }

    @Test
    public void pass() {
        PlanSmien plan = new PlanSmien();
        PolozkaPlanu smena = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 1), td.testovanaZona);
        smena.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(smena);

        PolozkaPlanu test = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 2), td.testovanaZona);
        test.setZamestnanec(td.zamestnanci.get(0));
        assertEquals(VysledokKontrolyPravidla.OK, new PravidloJednejSmeny().over(plan, test));
    }

}