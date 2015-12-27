package com.flowyk.apodys;

import java.time.ZonedDateTime;

public interface Planovac {
    PlanSmien naplanuj(ZonedDateTime zaciatok, ZonedDateTime koniec);
}
