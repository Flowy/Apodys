package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.TestovacieData;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class RovnakeSmenyCezVikendTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void nedelaInaSmena() throws Exception {
        PlanSmien plan = new PlanSmien();

        PolozkaPlanu sobota = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 5), td.testovanaZona);
        sobota.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(sobota);

        PolozkaPlanu nedela = td.predlohaP1C.vygenerujOd(LocalDate.of(2015, 12, 6), td.testovanaZona);
        nedela.setZamestnanec(td.zamestnanci.get(0));

        assertEquals(VysledokKontrolyPravidla.BROKEN, new RovnakeSmenyCezVikend().over(plan, nedela));
    }

    @Test
    public void lenNedela() {
        PlanSmien plan = new PlanSmien();

        PolozkaPlanu nedela = td.predlohaP1C.vygenerujOd(LocalDate.of(2015, 12, 6), td.testovanaZona);
        nedela.setZamestnanec(td.zamestnanci.get(0));

        assertEquals(VysledokKontrolyPravidla.BROKEN, new RovnakeSmenyCezVikend().over(plan, nedela));
    }

    @Test
    public void nedelaRovnakaAkoSobota() {
        PlanSmien plan = new PlanSmien();

        PolozkaPlanu sobota = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 5), td.testovanaZona);
        sobota.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(sobota);

        PolozkaPlanu nedela = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 6), td.testovanaZona);
        nedela.setZamestnanec(td.zamestnanci.get(0));

        assertEquals(VysledokKontrolyPravidla.OK, new RovnakeSmenyCezVikend().over(plan, nedela));
    }

    @Test
    public void nedelaInyZamestnanec() {
        PlanSmien plan = new PlanSmien();

        PolozkaPlanu sobota = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 5), td.testovanaZona);
        sobota.setZamestnanec(td.zamestnanci.get(0));
        plan.pridatPolozku(sobota);

        PolozkaPlanu nedela = td.predlohaR2P.vygenerujOd(LocalDate.of(2015, 12, 6), td.testovanaZona);
        nedela.setZamestnanec(td.zamestnanci.get(1));

        assertEquals(VysledokKontrolyPravidla.BROKEN, new RovnakeSmenyCezVikend().over(plan, nedela));
    }

}