package com.flowyk.apodys;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PolozkaPlanu {

    ZonedDateTime zaciatok;
    ZonedDateTime koniec;
    TypPolozkyPlanu typ;
    Zamestnanec zamestnanec;
    Duration countedDuration;


    public PolozkaPlanu(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ) {
        this(zaciatok, koniec, typ, Duration.between(zaciatok, koniec));
    }

    public PolozkaPlanu(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ, Duration countedDuration) {
        this.zaciatok = Objects.requireNonNull(zaciatok);
        this.koniec = Objects.requireNonNull(koniec);
        if (Duration.between(this.zaciatok, this.koniec).isNegative()) {
            throw new IllegalArgumentException("End must be after start");
        }
        this.typ = Objects.requireNonNull(typ);
        this.countedDuration = Objects.requireNonNull(countedDuration);
    }

    public PolozkaPlanu(PolozkaPlanu origin) {
        this(origin.zaciatok, origin.koniec, origin.typ, origin.countedDuration);
    }

    public TypPolozkyPlanu typ() {
        return typ;
    }

    public ZonedDateTime zaciatok() {
        return zaciatok;
    }

    public ZonedDateTime koniec() {
        return koniec;
    }

    public Zamestnanec vykonavatel() {
        return zamestnanec;
    }

    public Duration countedDuration() {
        return countedDuration;
    }

    public void setZaciatok(ZonedDateTime zaciatok) {
        this.zaciatok = zaciatok;
    }

    public void setKoniec(ZonedDateTime koniec) {
        this.koniec = koniec;
    }

    public void setZamestnanec(Zamestnanec zamestnanec) {
        this.zamestnanec = zamestnanec;
    }


    public boolean prekryva(PolozkaPlanu origin) {
        return prekryva(origin.zaciatok(), origin.koniec());
    }

    public boolean prekryva(ZonedDateTime zaciatok, ZonedDateTime koniec) {
        return startsIn(zaciatok, koniec) ||
                endsIn(zaciatok, koniec) ||
                isOver(zaciatok, koniec);
    }

    public boolean rovnakyVykonavatel(PolozkaPlanu origin) {
        return Objects.equals(origin.vykonavatel(), this.vykonavatel());
    }

    public boolean rovnakyTyp(PolozkaPlanu origin) {
        return Objects.equals(origin != null ? origin.typ() : null, this.typ());
    }

    /**
     * starts after/at start and starts before end
     */
    private boolean startsIn(ZonedDateTime start, ZonedDateTime end) {
        return !this.zaciatok().isBefore(start) && this.zaciatok().isBefore(end);
    }

    /**
     * ends after start and before/at end
     */
    private boolean endsIn(ZonedDateTime start, ZonedDateTime end) {
        return this.koniec().isAfter(start) && !this.koniec().isAfter(end);
    }

    /**
     * starts before start and after end
     */
    private boolean isOver(ZonedDateTime start, ZonedDateTime end) {
        return this.zaciatok().isBefore(start) && this.koniec().isAfter(end);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "PolozkaPlanu{" +
                "zaciatok=" + zaciatok.format(dateTime) +
                ", koniec=" + koniec.format(dateTime) +
                ", typ=" + typ +
                ", zamestnanec=" + zamestnanec +
                '}';
    }
}
