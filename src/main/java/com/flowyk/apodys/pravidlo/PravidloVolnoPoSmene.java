package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.TypPolozkyPlanu;

import java.time.Duration;

public class PravidloVolnoPoSmene implements PravidloPlanovaniaSmien {
    TypPolozkyPlanu typSmeny;
    Duration dlzkaVolna;

    public PravidloVolnoPoSmene(TypPolozkyPlanu typSmeny, Duration dlzkaVolna) {
        this.typSmeny = typSmeny;
        this.dlzkaVolna = dlzkaVolna;
    }


    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel()).preObdobie(test.zaciatok().minus(dlzkaVolna), test.zaciatok());
        if (skumanyPlan.trvaniePoloziek(typSmeny).isZero()) {
            return new VysledokKontrolyPravidla(false);
        } else {
            return new VysledokKontrolyPravidla(true);
        }
    }
}
