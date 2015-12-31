package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

import java.util.Iterator;
import java.util.Objects;

public class PravidloJednejSmeny implements PravidloPlanovaniaSmien {

    /**
     * Zamestnanec nemoze mat v rovnaky cas dve rozne smeny
     */
    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        for (PolozkaPlanu current: naplanovaneSmeny) {
            if (test.rovnakyVykonavatel(current) && test.prekryva(current)) {
                return VysledokKontrolyPravidla.BROKEN;
            }
        }
        return VysledokKontrolyPravidla.OK;
    }
}
