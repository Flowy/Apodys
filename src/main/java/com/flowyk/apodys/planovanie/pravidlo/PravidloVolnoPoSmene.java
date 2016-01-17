package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.TypPolozkyPlanu;

import java.time.Duration;

public class PravidloVolnoPoSmene implements PravidloPlanovaniaSmien {
    PredlohaSmeny predlohaSmeny;
    Duration dlzkaVolna;

    public PravidloVolnoPoSmene(PredlohaSmeny predlohaSmeny, Duration dlzkaVolna) {
        this.predlohaSmeny = predlohaSmeny;
        this.dlzkaVolna = dlzkaVolna;
    }


    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel()).preObdobie(test.zaciatok().minus(dlzkaVolna), test.zaciatok());
        Duration trvanieSmien = Duration.ZERO;
        for (PolozkaPlanu polozka: skumanyPlan) {
            if (rovnakaPredloha(predlohaSmeny, polozka)) {
                trvanieSmien = trvanieSmien.plus(polozka.countedDuration());
            }
        }
        if (trvanieSmien.isZero()) {
            return VysledokKontrolyPravidla.OK;
        } else {
            return VysledokKontrolyPravidla.BROKEN;
        }
    }

    static boolean rovnakaPredloha(PredlohaSmeny predloha, PolozkaPlanu test) {
        return predloha.equals(test.predloha());
    }
}
