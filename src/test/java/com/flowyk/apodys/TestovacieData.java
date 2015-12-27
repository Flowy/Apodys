package com.flowyk.apodys;

import org.junit.Assert;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class TestovacieData {

    public PredlohaSmeny predlohaR2P;
    public PredlohaSmeny predlohaP1C;
    public PredlohaSmeny predlohaO75;
    public PredlohaSmeny predlohaN2P;
    public ZoneId testovanaZona;
    public PredlohaSmienPreObdobie tyzdennyPlan;
    public List<Zamestnanec> zamestnanci;

    public TestovacieData() {
        predlohaR2P = new PredlohaSmeny(new TypPolozkyPlanu("R2P"), LocalTime.of(6, 0), LocalTime.of(18, 0), Duration.ofHours(12L));
        predlohaP1C = new PredlohaSmeny(new TypPolozkyPlanu("P1C"), LocalTime.of(9, 0), LocalTime.of(21, 0), Duration.ofHours(12L));
        predlohaO75 = new PredlohaSmeny(new TypPolozkyPlanu("07,5"), LocalTime.of(14, 0), LocalTime.of(22, 0), Duration.ofHours(8L));
        predlohaN2P = new PredlohaSmeny(new TypPolozkyPlanu("N2P"), LocalTime.of(18, 0), LocalTime.of(6, 0), Period.ofDays(1), Duration.ofHours(12L));
        testovanaZona = ZoneId.of("Europe/Bratislava");

        initTyzdennyPlan();
        zamestnanci = new ArrayList<>();
        initZamestnanci(zamestnanci);
    }


    public Smena smena(long hours) {
        ZonedDateTime zaciatok = ZonedDateTime.of(2015, 11, 29, 0, 0, 0, 0, testovanaZona);
        return new Smena(
                zaciatok,
                zaciatok.plusHours(hours),
                new TypPolozkyPlanu("test"));
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
        tyzdennyPlan = new PredlohaSmienPreObdobie(supis, Period.ofDays(7));
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

    private void initZamestnanci(List<Zamestnanec> zamestnanci) {
        zamestnanci.add(new Zamestnanec("Papa Smurf", "flowyk+testPapaSmurf@gmail.com"));
        zamestnanci.add(new Zamestnanec("Gargamel", "flowyk+testGargamel@gmail.com"));
        zamestnanci.add(new Zamestnanec("Darth Vader", "flowyk+testDdarthVader@gmail.com"));
        zamestnanci.add(new Zamestnanec("Alf", "flowyk+testAlf@gmail.com"));
        zamestnanci.add(new Zamestnanec("Tinky-Winky", "flowyk+testTinkyWinky@gmail.com"));
        zamestnanci.add(new Zamestnanec("Elmo", "flowyk+testElmo@gmail.com"));
        zamestnanci.add(new Zamestnanec("Cookie Monster", "flowyk+testCookieMonster@gmail.com"));

//        zamestnanci.add(new )
    }

}
