package com.flowyk.apodys.bussiness.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PredlohaSmienPreObdobie {
    private List<PredlohaSmenyPreObdobie> supisPredloh;
    private Period dlzkaObdobia;

    public PredlohaSmienPreObdobie(List<PredlohaSmenyPreObdobie> supis, Period dlzkaObdobia) {
        this.supisPredloh = supis;
        this.dlzkaObdobia = dlzkaObdobia;
    }

    public List<Shift> vygenerujOd(LocalDate datum) {
        List<Shift> vysledok = new ArrayList<>(supisPredloh.size());
        for (PredlohaSmenyPreObdobie predloha : supisPredloh) {
            vysledok.add(predloha.vygenerujOd(datum));
        }
        return vysledok;
    }

    public Period dlzkaObdobia() {
        return dlzkaObdobia;
    }

//    public Collection<? extends Shift> vygenerujOdDo(LocalDate start, LocalDate end) {
//        List<Shift> vysledok = new ArrayList<>(supisPredloh.size());
//        for (PredlohaSmenyPreObdobie predloha : supisPredloh) {
//            if (start.plus(predloha.getStartDay()).isAfter(end)) {
//                break;
//            } else {
//                vysledok.add(predloha.vygenerujOd(start));
//            }
//        }
//        return vysledok;
//    }

    public static class PredlohaSmenyPreObdobie implements PredlohaSmeny{
        private Period startDay;
        private PredlohaSmeny predloha;

        public PredlohaSmenyPreObdobie(PredlohaSmeny predloha, Period startDay) {
            this.predloha = predloha;
            this.startDay = startDay;
        }

        @Override
        public String getNazov() {
            return predloha.getNazov();
        }

        @Override
        public Shift vygenerujOd(LocalDate datum) {

            LocalDate posunutyDatum = datum.plus(startDay);
            return predloha.vygenerujOd(posunutyDatum);
        }

        public void setPredloha(PredlohaSmeny predloha) {
            this.predloha = predloha;
        }

        public PredlohaSmeny getPredloha() {
            return predloha;
        }

        public Period getStartDay() {
            return startDay;
        }
    }
}
