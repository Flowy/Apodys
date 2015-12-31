package com.flowyk.apodys;

import java.time.*;
import java.util.Objects;

public class PredlohaSmeny {
    TypPolozkyPlanu typ;
    LocalTime startTime;
    LocalTime endTime;
    Period timeSpan;

    Duration countedDuration;

    /**
     * startTime and endTime are in same day
     * @param countedDuration stable duration for this when making statistics
     */
    public PredlohaSmeny(TypPolozkyPlanu typ, LocalTime startTime, LocalTime endTime, Duration countedDuration) {
        this.typ = Objects.requireNonNull(typ);
        this.startTime = startTime;
        this.endTime = endTime;
        timeSpan = Period.ZERO;
        this.countedDuration = countedDuration;
    }

    public PredlohaSmeny(TypPolozkyPlanu typ, LocalTime startTime, LocalTime endTime, Period timeSpan, Duration countedDuration) {
        this.typ = typ;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSpan = timeSpan;
        this.countedDuration = countedDuration;
    }

    public PolozkaPlanu vygenerujOd(LocalDate datum, ZoneId zona) {
        ZonedDateTime zaciatok = ZonedDateTime.of(datum, startTime, zona);
        return new PolozkaPlanu(
                Objects.requireNonNull(zaciatok),
                zaciatok.plus(timeSpan).with(endTime),
                typ,
                countedDuration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PredlohaSmeny that = (PredlohaSmeny) o;

        return typ.equals(that.typ);

    }

    @Override
    public int hashCode() {
        return typ.hashCode();
    }
}