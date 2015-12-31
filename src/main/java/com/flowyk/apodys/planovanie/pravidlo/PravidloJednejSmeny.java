package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

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
