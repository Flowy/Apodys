package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

import java.util.Objects;

public class MaximalneOpakovanieSmien implements PravidloPlanovaniaSmien {
    Integer maximalneMnozstvo;

    public MaximalneOpakovanieSmien(Integer maximalneMnozstvo) {
        this.maximalneMnozstvo = Objects.requireNonNull(maximalneMnozstvo);
    }

    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel());
        PolozkaPlanu poslednaSmena = null;
        int pocetOpakovani = 0;
        for (PolozkaPlanu smena: skumanyPlan) {
            if (smena.rovnakyTyp(poslednaSmena)) {
                pocetOpakovani += 1;
            } else {
                poslednaSmena = smena;
                pocetOpakovani = 1;
            }
        }
        if (test.rovnakyTyp(poslednaSmena) && (pocetOpakovani + 1) > maximalneMnozstvo) {
            return VysledokKontrolyPravidla.BROKEN;
        } else {
            return VysledokKontrolyPravidla.OK;
        }
    }
}