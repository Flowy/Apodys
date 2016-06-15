package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.bussiness.entity.PlanSmien;

import java.time.LocalDate;
import java.time.ZoneId;

public interface Planovac {
    PlanSmien naplanuj(LocalDate zaciatok, LocalDate koniec, ZoneId timezone);
}
