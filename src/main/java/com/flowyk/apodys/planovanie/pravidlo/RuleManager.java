package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

import java.util.ArrayList;
import java.util.List;

public class RuleManager implements PravidloPlanovaniaSmien {

    private List<PravidloPlanovaniaSmien> pravidla;

    public RuleManager() {
        pravidla = new ArrayList<>();
    }

    public void pridat(PravidloPlanovaniaSmien pravidlo) {
        pravidla.add(pravidlo);
    }

    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        for (PravidloPlanovaniaSmien pravidlo: pravidla) {
            if (VysledokKontrolyPravidla.BROKEN.equals(pravidlo.over(naplanovaneSmeny, test))) {
                return VysledokKontrolyPravidla.BROKEN;
            }
        }
        return VysledokKontrolyPravidla.OK;
    }
}
