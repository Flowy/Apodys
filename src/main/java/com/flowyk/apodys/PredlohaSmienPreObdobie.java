package com.flowyk.apodys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PredlohaSmienPreObdobie {
    List<PredlohaSmenyPreObdobie> supisPredloh;
    Period dlzkaObdobia;

    public PredlohaSmienPreObdobie(List<PredlohaSmenyPreObdobie> supis, Period dlzkaObdobia) {
        this.supisPredloh = supis;
        this.dlzkaObdobia = dlzkaObdobia;
    }

    public List<Smena> vygenerujOd(LocalDate datum, ZoneId zona) {
        List<Smena> vysledok = new ArrayList<>(supisPredloh.size());
        for (PredlohaSmenyPreObdobie predloha: supisPredloh) {
            vysledok.add(predloha.vygenerujOd(datum, zona));
        }
        return vysledok;
    }

    public Period dlzkaObdobia() {
        return dlzkaObdobia;
    }

    public static class PredlohaSmenyPreObdobie extends PredlohaSmeny {
        Period startDay;

        public PredlohaSmenyPreObdobie(PredlohaSmeny predloha, Period startDay) {
            super(predloha.typ, predloha.startTime, predloha.endTime, predloha.countedDuration);
            this.startDay = startDay;
        }

        @Override
        public Smena vygenerujOd(LocalDate datum, ZoneId zona) {
            LocalDate posunutyDatum = datum.plus(startDay);
            return super.vygenerujOd(posunutyDatum, zona);
        }
    }
}
