package com.flowyk.apodys.bussiness.entity;

import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.time.*;

public class PredlohaSmenyTest {

    TestovacieData td;

    @Before
    public void setUp() {
        td = new TestovacieData();
    }

    @Test
    public void spravnyKoncovyCas() {
        Shift smena = td.predlohaR2P.vygenerujOd(LocalDate.now(td.testovanaZona), td.testovanaZona);
        assertEquals(td.predlohaR2P.endTime, smena.koniec().toLocalTime());
    }

    /**
     * pri prechode na DST sa dlzka smeny skrati o hodinu
     * predpoklada zakladnu dlzku smeny 12H
     */
    @Test
    public void dlzkaSmenyPriPrechodeNaDST() {
        Shift smena = td.predlohaN2P.vygenerujOd(LocalDate.of(2015, 3, 28), td.testovanaZona);
        assertEquals(
                Duration.ofHours(11L),
                Duration.between(smena.zaciatok(), smena.koniec()));
    }

    /**
     * pri prechode z DST sa dlzka smeny natiahne o hodinu
     * predpoklada zakladnu dlzku smeny 12H
     */
    @Test
    public void dlzkaSmenyPriPrechodeZDST() {
        Shift smena = td.predlohaN2P.vygenerujOd(LocalDate.of(2015, 10, 24), td.testovanaZona);
        assertEquals(
                Duration.ofHours(13L),
                Duration.between(smena.zaciatok(), smena.koniec()));
    }

    @Test
    public void dlzkaSmenyVBeznyDen() {
        Shift smena = td.predlohaP1C.vygenerujOd(LocalDate.of(2015, 1, 1), td.testovanaZona);
        assertEquals(
                Duration.between(td.predlohaP1C.startTime, td.predlohaP1C.endTime),
                Duration.between(smena.zaciatok(), smena.koniec()));
    }
}