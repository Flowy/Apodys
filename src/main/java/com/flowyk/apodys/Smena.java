package com.flowyk.apodys;

import java.time.ZonedDateTime;

public class Smena implements JednotkaPlanu {

    private ZonedDateTime zaciatok;
    private ZonedDateTime koniec;
    private TypJednotkyPlanu typ;
    private Zamestnanec zamestnanec;

    public Smena(ZonedDateTime zaciatok, ZonedDateTime koniec, TypJednotkyPlanu typ) {
        this.zaciatok = zaciatok;
        this.koniec = koniec;
        this.typ = typ;
    }

    @Override
    public TypJednotkyPlanu typ() {
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
}