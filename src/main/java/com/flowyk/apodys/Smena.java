package com.flowyk.apodys;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Smena implements PolozkaPlanu {

    ZonedDateTime zaciatok;
    ZonedDateTime koniec;
    TypPolozkyPlanu typ;
    Zamestnanec zamestnanec;

    public Smena(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ) {
        this.zaciatok = zaciatok;
        this.koniec = koniec;
        this.typ = typ;
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