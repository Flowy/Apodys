package com.flowyk.apodys.planovanie.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;

public interface PravidloPlanovaniaSmien {
    VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, Shift test);
}
