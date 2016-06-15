package com.flowyk.apodys.bussiness.entity;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
//TODO: implement observable
public class Shift implements Serializable {

    @XmlElement(required = true)
    private ZonedDateTime zaciatok;

    @XmlElement(required = true)
    private ZonedDateTime koniec;

    @XmlElement(required = true)
    private TypPolozkyPlanu typ;

    @XmlIDREF
    private Zamestnanec zamestnanec;

    @XmlElement(required = true)
    private Duration countedDuration;

    @XmlIDREF
    @XmlAttribute(required = true)
    private PredlohaSmeny predloha;

    /**
     * default konstruktor pre JAXB
     */
    public Shift() {
    }

    public Shift(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ) {
        this(zaciatok, koniec, typ, Duration.between(zaciatok, koniec));
    }

    public Shift(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ, Duration countedDuration) {
        this.zaciatok = Objects.requireNonNull(zaciatok);
        this.koniec = Objects.requireNonNull(koniec);
        if (Duration.between(this.zaciatok, this.koniec).isNegative()) {
            throw new IllegalArgumentException("End must be after start");
        }
        this.typ = Objects.requireNonNull(typ);
        this.countedDuration = Objects.requireNonNull(countedDuration);
    }

    /**
     * konstruktor pre predlohy
     */
    public Shift(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ, Duration countedDuration, PredlohaSmeny predloha) {
        this(zaciatok, koniec, typ, countedDuration);
        this.predloha = Objects.requireNonNull(predloha);
    }

    public Shift(Shift origin) {
        this(origin.zaciatok, origin.koniec, origin.typ, origin.countedDuration, origin.predloha);
        this.zamestnanec = origin.zamestnanec;

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

    public PredlohaSmeny predloha() {
        return predloha;
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


    public boolean prekryva(Shift origin) {
        return prekryva(origin.zaciatok(), origin.koniec());
    }

    public boolean prekryva(ZonedDateTime zaciatok, ZonedDateTime koniec) {
        return startsIn(zaciatok, koniec) ||
                endsIn(zaciatok, koniec) ||
                isOver(zaciatok, koniec);
    }

    public boolean rovnakyVykonavatel(Shift origin) {
        return Objects.equals(origin.vykonavatel(), this.vykonavatel());
    }

    public boolean rovnakaZmena(Shift origin) {
        return Objects.equals(origin.zaciatok().toLocalTime(), this.zaciatok().toLocalTime());
    }

    public boolean rovnakyTyp(Shift origin) {
        return origin != null && Objects.equals(origin.typ(), this.typ());
    }

    public boolean rovnakyCas(Shift origin) {
        return origin != null &&
                Objects.equals(origin.zaciatok().toLocalTime(), zaciatok().toLocalTime()) &&
                Objects.equals(origin.koniec().toLocalTime(), koniec().toLocalTime());
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
        return "Shift{" +
                "zaciatok=" + zaciatok.format(dateTime) +
                ", koniec=" + koniec.format(dateTime) +
                ", typ=" + typ +
                ", zamestnanec=" + zamestnanec +
                '}';
    }
}
