package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;

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
        PlanSmien skumaneSmeny = naplanovaneSmeny.preZamestnanca(test.vykonavatel());
        skumaneSmeny = skumaneSmeny.preObdobie(zaciatokSkumanehoObdobia, koniecSkumanehoObdobia);
        Duration totalDuration = Duration.ZERO;
        for (PolozkaPlanu smena: skumaneSmeny.zoznamPoloziek()) {
            ZonedDateTime startTime = smena.zaciatok().isBefore(zaciatokSkumanehoObdobia) ? zaciatokSkumanehoObdobia : smena.zaciatok();
            ZonedDateTime endTime = smena.koniec().isAfter(koniecSkumanehoObdobia) ? koniecSkumanehoObdobia : smena.koniec();
            totalDuration = totalDuration.plus(Duration.between(startTime, endTime));
        }
        totalDuration = totalDuration.plus(Duration.between(test.zaciatok(), test.koniec()));
        return new VysledokKontrolyPravidla(totalDuration.compareTo(maximalnyCas) > 0);
    }
}
