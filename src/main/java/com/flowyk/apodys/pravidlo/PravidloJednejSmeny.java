package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

import java.util.Objects;

public class PravidloJednejSmeny implements PravidloPlanovaniaSmien {

    /**
     * Zamestnanec nemoze mat v rovnaky cas dve rozne smeny
     */
    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        for (PolozkaPlanu polozka: naplanovaneSmeny.zoznamPoloziek()) {
            if (test.vykonavatel().equals(polozka.vykonavatel())) {
                //starts after/at start and starts before end
                boolean startsIn = !test.zaciatok().isBefore(polozka.zaciatok()) && test.zaciatok().isBefore(polozka.koniec());
                //ends after start and before/at end
                boolean endsIn = test.koniec().isAfter(polozka.zaciatok()) && !test.koniec().isAfter(polozka.koniec());
                //starts before/at start and after/at end
                boolean isEqualOrLonger = !test.zaciatok().isAfter(polozka.zaciatok()) && !test.koniec().isBefore(polozka.koniec());
                if (startsIn || endsIn || isEqualOrLonger) {
                    return new VysledokKontrolyPravidla(true);
                }
            }
        }
        return new VysledokKontrolyPravidla(false);
    }
}
