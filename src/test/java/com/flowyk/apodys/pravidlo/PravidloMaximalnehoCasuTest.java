package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.*;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class PravidloMaximalnehoCasuTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void prekrocenie() throws Exception {
        PlanSmien plan = new PlanSmien();
        PolozkaPlanu smena = td.smena(48L);
        smena.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(smena);
        //nova smena
        PolozkaPlanu test = td.predlohaR2P.vygenerujOd(smena.koniec().toLocalDate().plusDays(1L), td.testovanaZona);
        test.setZamestnanec(td.zamestnanci.get(0));

        PravidloPlanovaniaSmien pravidlo = new PravidloMaximalnehoCasu(Duration.ofHours(55L), Period.ofDays(7));
        VysledokKontrolyPravidla vysledok = pravidlo.over(plan, test);
        assertTrue(vysledok.isBroken());
    }

    @Test
    public void splnenie() throws Exception {
        PlanSmien plan = new PlanSmien();
        PolozkaPlanu smena = td.smena(43L);
        smena.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(smena);

        PolozkaPlanu test = td.predlohaR2P.vygenerujOd(smena.koniec().toLocalDate().plusDays(1L), td.testovanaZona);
        test.setZamestnanec(td.zamestnanci.get(0));

        PravidloPlanovaniaSmien pravidlo = new PravidloMaximalnehoCasu(Duration.ofHours(55L), Period.ofDays(7));
        VysledokKontrolyPravidla vysledok = pravidlo.over(plan, test);
        assertFalse(vysledok.isBroken());
    }
}