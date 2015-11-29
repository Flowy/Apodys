package com.flowyk.apodys;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PredlohaSmienPreObdobie {
    List<PredlohaSmenyPreObdobie> supisPredloh;

    public PredlohaSmienPreObdobie() {
        this.supisPredloh = new ArrayList<>();
    }

    PredlohaSmienPreObdobie(List<PredlohaSmenyPreObdobie> supis) {
        this.supisPredloh = supis;
    }

    public List<Smena> vygenerujOd(LocalDate datum, ZoneId zona) {
        List<Smena> vysledok = new ArrayList<>(supisPredloh.size());
        for (PredlohaSmenyPreObdobie predloha: supisPredloh) {
            vysledok.add(predloha.vygenerujOd(datum, zona));
        }
        return vysledok;
    }

    public static class PredlohaSmenyPreObdobie extends PredlohaSmeny {
        Period startDay;

        public PredlohaSmenyPreObdobie(PredlohaSmeny predloha, Period startDay) {
            super(predloha.typ, predloha.startTime, predloha.endTime);
            this.startDay = startDay;
        }

        public PredlohaSmenyPreObdobie(TypPolozkyPlanu typ, LocalTime startTime, LocalTime endTime, Period startDay) {
            super(typ, startTime, endTime);
            this.startDay = startDay;
        }

        public PredlohaSmenyPreObdobie(TypPolozkyPlanu typ, LocalTime startTime, LocalTime endTime, Period startDay, Period timeSpan) {
            super(typ, startTime, endTime, timeSpan);
            this.startDay = startDay;
        }

        @Override
        public Smena vygenerujOd(LocalDate datum, ZoneId zona) {
            LocalDate posunutyDatum = datum.plus(startDay);
            return super.vygenerujOd(posunutyDatum, zona);
        }
    }
}
