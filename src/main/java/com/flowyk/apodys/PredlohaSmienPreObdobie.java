package com.flowyk.apodys;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PredlohaSmienPreObdobie {
    List<PredlohaSmenyPreObdobie> supisPredloh;
    Period dlzkaObdobia;

    public PredlohaSmienPreObdobie(List<PredlohaSmenyPreObdobie> supis, Period dlzkaObdobia) {
        this.supisPredloh = supis;
        this.dlzkaObdobia = dlzkaObdobia;
    }

    public List<Shift> vygenerujOd(LocalDate datum, ZoneId zona) {
        List<Shift> vysledok = new ArrayList<>(supisPredloh.size());
        for (PredlohaSmenyPreObdobie predloha: supisPredloh) {
            vysledok.add(predloha.vygenerujOd(datum, zona));
        }
        return vysledok;
    }

    public Period dlzkaObdobia() {
        return dlzkaObdobia;
    }

    public Collection<? extends Shift> vygenerujOdDo(LocalDate start, LocalDate end, ZoneId timezone) {
        List<Shift> vysledok = new ArrayList<>(supisPredloh.size());
        for (PredlohaSmenyPreObdobie predloha: supisPredloh) {
            if (start.plus(predloha.getStartDay()).isAfter(end)) {
                break;
            } else {
                vysledok.add(predloha.vygenerujOd(start, timezone));
            }
        }
        return vysledok;
    }

    public static class PredlohaSmenyPreObdobie extends PredlohaSmeny {
        Period startDay;

        public PredlohaSmenyPreObdobie(PredlohaSmeny predloha, Period startDay) {
            super(predloha.nazov, predloha.startTime, predloha.endTime, predloha.timeSpan, predloha.countedDuration);
            this.startDay = startDay;
        }

        @Override
        public Shift vygenerujOd(LocalDate datum, ZoneId zona) {
            LocalDate posunutyDatum = datum.plus(startDay);
            return super.vygenerujOd(posunutyDatum, zona);
        }

        public Period getStartDay() {
            return startDay;
        }
    }
}
