package com.flowyk.apodys.bussiness.entity;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.NONE)
public class Shift implements Serializable {

    private ZonedDateTime zaciatok;
    private ZonedDateTime koniec;
    private TypPolozkyPlanu typ;
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

    @Override
    public String toString() {
        DateTimeFormatter dateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return "Shift{" +
                "zaciatok=" + getZaciatok().format(dateTime) +
                ", koniec=" + getKoniec().format(dateTime) +
                ", typ=" + getTyp() +
                '}';
    }

    public final static Comparator<Shift> START_TIME_COMPARATOR = (o1, o2) -> o1.getZaciatok().compareTo(o2.getZaciatok());
}
