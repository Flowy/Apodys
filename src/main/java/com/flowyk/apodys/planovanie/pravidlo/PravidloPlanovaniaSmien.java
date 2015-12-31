package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

public interface PravidloPlanovaniaSmien {
    VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test);
}
