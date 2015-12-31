package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;

import java.time.ZonedDateTime;

public interface Planovac {
    PlanSmien naplanuj(ZonedDateTime zaciatok, ZonedDateTime koniec);
}
