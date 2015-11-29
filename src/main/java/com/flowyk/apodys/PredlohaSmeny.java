package com.flowyk.apodys;

import java.time.*;
import java.util.Objects;

public class PredlohaSmeny {
    TypPolozkyPlanu typ;
    LocalTime startTime;
    LocalTime endTime;
    Period endDay;

    public Smena zacina(LocalDate datum, ZoneId zona) {
        ZonedDateTime zaciatok = ZonedDateTime.of(datum, startTime, zona);
        return new Smena(
                Objects.requireNonNull(zaciatok),
                zaciatok.plus(endDay).with(endTime),
                typ);
    }
}