package com.flowyk.apodys;

import java.time.*;
import java.util.Objects;

public class PredlohaSmeny {
    TypPolozkyPlanu typ;
    LocalTime startTime;
    LocalTime endTime;
    Period timeSpan;

    /**
     * startTime and endTime are in same day
     * @param typ
     * @param startTime
     * @param endTime
     */
    public PredlohaSmeny(TypPolozkyPlanu typ, LocalTime startTime, LocalTime endTime) {
        this.typ = typ;
        this.startTime = startTime;
        this.endTime = endTime;
        timeSpan = Period.ZERO;
    }

    public PredlohaSmeny(TypPolozkyPlanu typ, LocalTime startTime, LocalTime endTime, Period timeSpan) {
        this.typ = typ;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSpan = timeSpan;
    }

    public Smena vygenerujOd(LocalDate datum, ZoneId zona) {
        ZonedDateTime zaciatok = ZonedDateTime.of(datum, startTime, zona);
        return new Smena(
                Objects.requireNonNull(zaciatok),
                zaciatok.plus(timeSpan).with(endTime),
                typ);
    }
}