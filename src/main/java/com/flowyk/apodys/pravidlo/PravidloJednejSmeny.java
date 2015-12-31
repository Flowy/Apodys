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
        for (PolozkaPlanu polozka : naplanovaneSmeny.zoznamPoloziek()) {
            if (test.rovnakyVykonavatel(polozka) && test.prekryva(polozka)) {
                return new VysledokKontrolyPravidla(true);
            }
        }
        return new VysledokKontrolyPravidla(false);
    }
}
