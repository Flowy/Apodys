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
        this.zaciatok = zaciatok;
        this.koniec = koniec;
        this.typ = typ;
        this.countedDuration = Duration.between(zaciatok, koniec);
    }

    public PolozkaPlanu(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ, Duration countedDuration) {
        this.zaciatok = zaciatok;
        this.koniec = koniec;
        this.typ = typ;
        this.countedDuration = countedDuration;
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
        return startsIn(origin, this) ||
                endsIn(origin, this) ||
                isOver(origin, this);
    }

    public boolean rovnakyVykonavatel(PolozkaPlanu origin) {
        return Objects.equals(origin.vykonavatel(), this.vykonavatel());
    }

    /**
     * starts after/at start and starts before end
     */
    private boolean startsIn(PolozkaPlanu origin, PolozkaPlanu test) {
        return !test.zaciatok().isBefore(origin.zaciatok()) && test.zaciatok().isBefore(origin.koniec());
    }

    /**
     * ends after start and before/at end
     */
    private boolean endsIn(PolozkaPlanu origin, PolozkaPlanu test) {
        return test.koniec().isAfter(origin.zaciatok()) && !test.koniec().isAfter(origin.koniec());
    }

    /**
     * starts before start and after end
     */
    private boolean isOver(PolozkaPlanu origin, PolozkaPlanu test) {
        return test.zaciatok().isBefore(origin.zaciatok()) && test.koniec().isAfter(origin.koniec());
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
