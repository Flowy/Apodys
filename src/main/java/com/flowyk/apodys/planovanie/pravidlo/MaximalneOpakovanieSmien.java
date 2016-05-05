package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;

import java.util.Objects;

//TODO: naco je toto?
public class MaximalneOpakovanieSmien implements PravidloPlanovaniaSmien {
    Integer maximalneMnozstvo;

    public MaximalneOpakovanieSmien(Integer maximalneMnozstvo) {
        this.maximalneMnozstvo = Objects.requireNonNull(maximalneMnozstvo);
    }

    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, Shift test) {
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel());
        Shift poslednaSmena = null;
        int pocetOpakovani = 0;
        for (Shift smena: skumanyPlan) {
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
