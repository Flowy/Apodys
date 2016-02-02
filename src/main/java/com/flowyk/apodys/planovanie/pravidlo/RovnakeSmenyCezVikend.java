package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;

import java.time.DayOfWeek;

public class RovnakeSmenyCezVikend implements PravidloPlanovaniaSmien {

    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, Shift test) {
        boolean testInSunday = test.zaciatok().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean saturdaySameAsSunday = false;
        if (testInSunday) {
            PlanSmien skumanyPlan = naplanovaneSmeny
                    .preObdobie(test.zaciatok().minusDays(1L), test.koniec().minusDays(1L));
            for (Shift polozka: skumanyPlan) {
                if (polozka.typ().equals(test.typ()) && polozka.vykonavatel().equals(test.vykonavatel())) {
                    saturdaySameAsSunday = true;
                }
            }
        }
        boolean isOk = !testInSunday || saturdaySameAsSunday;
        if (isOk) {
            return VysledokKontrolyPravidla.OK;
        } else {
            return VysledokKontrolyPravidla.BROKEN;
        }
    }
}
