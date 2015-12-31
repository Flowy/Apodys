package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Iterator;

public class PravidloMaximalnehoCasu implements PravidloPlanovaniaSmien {

    Duration maximalnyCas;
    Period skumaneObdobie;

    public PravidloMaximalnehoCasu(Duration maximalnyCas, Period skumaneObdobie) {
        this.maximalnyCas = maximalnyCas;
        this.skumaneObdobie = skumaneObdobie;
    }

    /**
     * Odpracovany cas za dane obdobie nesmie byt vyssi ako maximalny cas
     */
    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        ZonedDateTime zaciatokSkumanehoObdobia = test.zaciatok().minus(skumaneObdobie);
        ZonedDateTime koniecSkumanehoObdobia = test.zaciatok();
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel()).preObdobie(zaciatokSkumanehoObdobia, koniecSkumanehoObdobia);
        Duration totalDuration = Duration.ZERO;
        for (PolozkaPlanu smena: skumanyPlan) {
            ZonedDateTime startTime = smena.zaciatok().isBefore(zaciatokSkumanehoObdobia) ? zaciatokSkumanehoObdobia : smena.zaciatok();
            ZonedDateTime endTime = smena.koniec().isAfter(koniecSkumanehoObdobia) ? koniecSkumanehoObdobia : smena.koniec();
            totalDuration = totalDuration.plus(Duration.between(startTime, endTime));
        }
        totalDuration = totalDuration.plus(test.countedDuration());
        if (totalDuration.compareTo(maximalnyCas) > 0) {
            return VysledokKontrolyPravidla.BROKEN;
        } else {
            return VysledokKontrolyPravidla.OK;
        }
    }
}
