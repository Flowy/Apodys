package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.TypPolozkyPlanu;

import java.time.Duration;
import java.util.Objects;

public class PravidloVolnoPoDvochSmenach implements PravidloPlanovaniaSmien {
    PredlohaSmeny predlohaPrvejSmeny;
    PredlohaSmeny predlohaDruhejSmeny;
    Duration dlzkaVolna;

    public PravidloVolnoPoDvochSmenach(PredlohaSmeny predlohaPrvejSmeny, PredlohaSmeny predlohaDruhejSmeny, Duration dlzkaVolna) {
        this.predlohaPrvejSmeny = Objects.requireNonNull(predlohaPrvejSmeny);
        this.predlohaDruhejSmeny = Objects.requireNonNull(predlohaDruhejSmeny);
        this.dlzkaVolna = Objects.requireNonNull(dlzkaVolna);
    }

    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel());
        boolean firstMatched = false;
        PolozkaPlanu poslednaZhoda = null;
        for (PolozkaPlanu current: skumanyPlan) {
            //must be last match in plan
            poslednaZhoda = null;
            if (firstMatched && PravidloVolnoPoSmene.rovnakaPredloha(predlohaDruhejSmeny, current)) {
                //this is second iteration - only if first one matched
                poslednaZhoda = current;
            }
            firstMatched = PravidloVolnoPoSmene.rovnakaPredloha(predlohaPrvejSmeny, current);
        }
        if (poslednaZhoda != null && dlzkaVolna.compareTo(Duration.between(poslednaZhoda.koniec(), test.zaciatok())) >= 0) {
            return VysledokKontrolyPravidla.BROKEN;
        } else {
            return VysledokKontrolyPravidla.OK;
        }
    }
}
