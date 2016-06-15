package com.flowyk.apodys.bussiness.entity;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.NONE)
public class Shift extends ObjectBinding<Shift> implements Serializable {

    private SimpleObjectProperty<ZonedDateTime> zaciatok;
    private SimpleObjectProperty<ZonedDateTime> koniec;
    private TypPolozkyPlanu typ;
    private SimpleObjectProperty<Zamestnanec> employee;
    private Duration countedDuration;
    private SimpleObjectProperty<PredlohaSmeny> predloha;

    /**
     * default konstruktor pre JAXB
     */
    public Shift() {
        this.zaciatok = new SimpleObjectProperty<>(this, "zaciatok");
        this.koniec = new SimpleObjectProperty<>(this, "koniec");
        this.employee = new SimpleObjectProperty<>(this, "employee");
        this.predloha = new SimpleObjectProperty<>(this, "predloha");

        bind(this.zaciatok, this.koniec, this.employee, this.predloha);
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
        return zaciatok.getValue();
    }
    public void setZaciatok(ZonedDateTime zaciatok) {
        this.zaciatok.setValue(zaciatok);
    }

    @XmlElement(required = true)
    public ZonedDateTime getKoniec() {
        return koniec.getValue();
    }
    public void setKoniec(ZonedDateTime koniec) {
        this.koniec.set(koniec);
    }

    @XmlIDREF
    public Zamestnanec getEmployee() {
        return employee.getValue();
    }
    public void setEmployee(Zamestnanec employee) {
        this.employee.set(employee);
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
        return predloha.get();
    }
    public void setPredloha(PredlohaSmeny predloha) {
        this.predloha.set(predloha);
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

    @Override
    protected Shift computeValue() {
        return this;
    }
}
