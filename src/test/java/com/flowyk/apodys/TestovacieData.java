package com.flowyk.apodys;

import org.junit.Assert;

import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TestovacieData {

    PredlohaSmeny predlohaR2P;
    PredlohaSmeny predlohaP1C;
    PredlohaSmeny predlohaO75;
    PredlohaSmeny predlohaN2P;
    ZoneId testovanaZona;
    PredlohaSmienPreObdobie tyzdennyPlan;

    TestovacieData() {
        predlohaR2P = new PredlohaSmeny(new TypPolozkyPlanu("R2P"), LocalTime.of(6, 0), LocalTime.of(18, 0));
        predlohaP1C = new PredlohaSmeny(new TypPolozkyPlanu("P1C"), LocalTime.of(9, 0), LocalTime.of(21, 0));
        predlohaO75 = new PredlohaSmeny(new TypPolozkyPlanu("07,5"), LocalTime.of(14, 0), LocalTime.of(22, 0));
        predlohaN2P = new PredlohaSmeny(new TypPolozkyPlanu("N2P"), LocalTime.of(18, 0), LocalTime.of(6, 0), Period.ofDays(1));
        testovanaZona = ZoneId.of("Europe/Bratislava");

        initTyzdennyPlan();
    }

    private void initTyzdennyPlan() {
        List<PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie> supis = new ArrayList<>();
        Period startDay = Period.ZERO;
        while (startDay.getDays() < 7) {
            if (startDay.getDays() < 5) {
                pridajSmenyPocasTyzdna(supis, startDay);
            } else {
                pridajSmenyPocasVikendu(supis, startDay);
            }
            startDay = startDay.plus(Period.ofDays(1));
        }
        Assert.assertEquals("Tyzdenny plan by mal mat 40 zaznamov", 40L, supis.size());
        tyzdennyPlan = new PredlohaSmienPreObdobie(supis);
    }


    private void pridajSmenyPocasTyzdna(List<PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie> supis, Period startDay) {
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaR2P, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaR2P, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaP1C, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaO75, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaN2P, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaN2P, startDay));
    }

    private void pridajSmenyPocasVikendu(List<PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie> supis, Period startDay) {
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaR2P, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaR2P, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaP1C, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaN2P, startDay));
        supis.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaN2P, startDay));
    }

}
