package com.flowyk.apodys.bussiness.entity;

import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PredlohaSmenyTest {

    TestovacieData td;

    @Before
    public void setUp() {
        td = new TestovacieData();
    }

    @Test
    public void spravnyKoncovyCas() {
        Shift smena = td.predlohaR2P.vygenerujOd(LocalDate.now(td.testovanaZona));
        assertEquals(td.predlohaR2P.endTime, smena.getKoniec().toLocalTime());
    }

    /**
     * pri prechode na DST sa dlzka smeny skrati o hodinu
     * predpoklada zakladnu dlzku smeny 12H
     */
    @Test
    public void dlzkaSmenyPriPrechodeNaDST() {
        Shift smena = td.predlohaN2P.vygenerujOd(LocalDate.of(2015, 3, 28));
        assertEquals(
                Duration.ofHours(11L),
                Duration.between(smena.getZaciatok(), smena.getKoniec()));
    }

    /**
     * pri prechode z DST sa dlzka smeny natiahne o hodinu
     * predpoklada zakladnu dlzku smeny 12H
     */
    @Test
    public void dlzkaSmenyPriPrechodeZDST() {
        Shift smena = td.predlohaN2P.vygenerujOd(LocalDate.of(2015, 10, 24));
        assertEquals(
                Duration.ofHours(13L),
                Duration.between(smena.getZaciatok(), smena.getKoniec()));
    }

    @Test
    public void dlzkaSmenyVBeznyDen() {
        Shift smena = td.predlohaP1C.vygenerujOd(LocalDate.of(2015, 1, 1));
        assertEquals(
                Duration.between(td.predlohaP1C.startTime, td.predlohaP1C.endTime),
                Duration.between(smena.getZaciatok(), smena.getKoniec()));
    }
}