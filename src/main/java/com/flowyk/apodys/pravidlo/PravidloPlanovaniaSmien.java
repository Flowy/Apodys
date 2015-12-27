package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;

public interface PravidloPlanovaniaSmien {
    VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test);
}
