package com.flowyk.apodys.bussiness.entity;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.NONE)
public class Shift implements Serializable {

    private ZonedDateTime zaciatok;
    private ZonedDateTime koniec;
    private TypPolozkyPlanu typ;
    private Zamestnanec employee;
    private Duration countedDuration;
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
        this();
        this.setZaciatok(Objects.requireNonNull(zaciatok));
        this.setKoniec(Objects.requireNonNull(koniec));
        if (Duration.between(this.getZaciatok(), this.getKoniec()).isNegative()) {
            throw new IllegalArgumentException("End must be after start");
        }
        this.setTyp(Objects.requireNonNull(typ));
        this.setCountedDuration(Objects.requireNonNull(countedDuration));
    }

    /**
     * konstruktor pre predlohy
     */
    public Shift(ZonedDateTime zaciatok, ZonedDateTime koniec, TypPolozkyPlanu typ, Duration countedDuration, PredlohaSmeny predloha) {
        this(zaciatok, koniec, typ, countedDuration);
        this.setPredloha(Objects.requireNonNull(predloha));
    }

    public Shift(Shift origin) {
        this(origin.getZaciatok(), origin.getKoniec(), origin.getTyp(), origin.getCountedDuration(), origin.getPredloha());
        this.setEmployee(origin.getEmployee());

    }

    @XmlElement(required = true)
    public TypPolozkyPlanu getTyp() {
        return typ;
    }
    public void setTyp(TypPolozkyPlanu typ) {
        this.typ = typ;
    }

    @XmlElement(required = true)
    public ZonedDateTime getZaciatok() {
        return zaciatok;
    }
    public void setZaciatok(ZonedDateTime zaciatok) {
        this.zaciatok = zaciatok;
    }

    @XmlElement(required = true)
    public ZonedDateTime getKoniec() {
        return koniec;
    }
    public void setKoniec(ZonedDateTime koniec) {
        this.koniec = koniec;
    }

    @XmlIDREF
    public Zamestnanec getEmployee() {
        return employee;
    }
    public void setEmployee(Zamestnanec employee) {
        this.employee = employee;
    }

    @XmlElement(required = true)
    public Duration getCountedDuration() {
        return countedDuration;
    }
    public void setCountedDuration(Duration countedDuration) {
        this.countedDuration = countedDuration;
    }

    @XmlIDREF
    @XmlAttribute(required = true)
    public PredlohaSmeny getPredloha() {
        return predloha;
    }
    public void setPredloha(PredlohaSmeny predloha) {
        this.predloha = predloha;
    }




    public boolean prekryva(Shift origin) {
        return prekryva(origin.getZaciatok(), origin.getKoniec());
    }

    public boolean prekryva(ZonedDateTime zaciatok, ZonedDateTime koniec) {
        return startsIn(zaciatok, koniec) ||
                endsIn(zaciatok, koniec) ||
                isOver(zaciatok, koniec);
    }

    public boolean rovnakyVykonavatel(Shift origin) {
        return Objects.equals(origin.getEmployee(), this.getEmployee());
    }

    public boolean rovnakaZmena(Shift origin) {
        return Objects.equals(origin.getZaciatok().toLocalTime(), this.getZaciatok().toLocalTime());
    }

    public boolean rovnakyTyp(Shift origin) {
        return origin != null && Objects.equals(origin.getTyp(), this.getTyp());
    }

    public boolean rovnakyCas(Shift origin) {
        return origin != null &&
                Objects.equals(origin.getZaciatok().toLocalTime(), getZaciatok().toLocalTime()) &&
                Objects.equals(origin.getKoniec().toLocalTime(), getKoniec().toLocalTime());
    }

    /**
     * starts after/at start and starts before end
     */
    private boolean startsIn(ZonedDateTime start, ZonedDateTime end) {
        return !this.getZaciatok().isBefore(start) && this.getZaciatok().isBefore(end);
    }

    /**
     * ends after start and before/at end
     */
    private boolean endsIn(ZonedDateTime start, ZonedDateTime end) {
        return this.getKoniec().isAfter(start) && !this.getKoniec().isAfter(end);
    }

    /**
     * starts before start and after end
     */
    private boolean isOver(ZonedDateTime start, ZonedDateTime end) {
        return this.getZaciatok().isBefore(start) && this.getKoniec().isAfter(end);
    }

    @Override
    public String toString() {
        DateTimeFormatter dateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "Shift{" +
                "zaciatok=" + getZaciatok().format(dateTime) +
                ", koniec=" + getKoniec().format(dateTime) +
                ", typ=" + getTyp() +
                ", zamestnanec=" + getEmployee() +
                '}';
    }
}
