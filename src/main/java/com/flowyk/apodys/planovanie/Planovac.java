package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public interface Planovac {
    List<Shift> naplanuj(List<Zamestnanec> employees, LocalDate zaciatok, LocalDate koniec, ZoneId timezone);
}
