package com.flowyk.apodys;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Smena implements PolozkaPlanu {

    ZonedDateTime zaciatok;
    ZonedDateTime koniec;
    TypPolozkyPlanu typ;
    Zamestnanec zamestnanec;
    Duration countedDuration;

    public Smena(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ) {
        this.zaciatok = zaciatok;
        this.koniec = koniec;
        this.typ = typ;
        this.countedDuration = Duration.between(zaciatok, koniec);
    }

    public Smena(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ, Duration countedDuration) {
        this.zaciatok = zaciatok;
        this.koniec = koniec;
        this.typ = typ;
        this.countedDuration = countedDuration;
    }

    @Override
    public TypPolozkyPlanu typ() {
        return typ;
    }

    @Override
    public ZonedDateTime zaciatok() {
        return zaciatok;
    }

    @Override
    public ZonedDateTime koniec() {
        return koniec;
    }

    @Override
    public Zamestnanec vykonavatel() {
        return zamestnanec;
    }

    @Override
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

    @Override
    public String toString() {
        DateTimeFormatter dateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "Smena{" +
                "zaciatok=" + zaciatok.format(dateTime) +
                ", koniec=" + koniec.format(dateTime) +
                ", typ=" + typ +
                ", zamestnanec=" + zamestnanec +
                '}';
    }
}