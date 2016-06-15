package com.flowyk.apodys.bussiness.entity;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.io.Serializable;
import java.time.*;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class PredlohaSmeny implements Serializable {

    @XmlID
    @XmlAttribute
    String nazov;

    @XmlAttribute
    LocalTime startTime;

    @XmlAttribute
    LocalTime endTime;

    @XmlAttribute
    Period timeSpan;

    @XmlAttribute
    Duration countedDuration;

    /**
     * default constructor for JAXB
     */
    public PredlohaSmeny() {
    }

    /**
     * startTime and endTime are in same day
     *
     * @param countedDuration stable duration for this when making statistics
     */
    public PredlohaSmeny(String nazov, LocalTime startTime, LocalTime endTime, Duration countedDuration) {
        this(nazov, startTime, endTime, Period.ZERO, countedDuration);
    }

    public PredlohaSmeny(String nazov, LocalTime startTime, LocalTime endTime, Period timeSpan, Duration countedDuration) {
        this.nazov = Objects.requireNonNull(nazov);
        this.startTime = Objects.requireNonNull(startTime);
        this.endTime = Objects.requireNonNull(endTime);
        this.timeSpan = Objects.requireNonNull(timeSpan);
        this.countedDuration = Objects.requireNonNull(countedDuration);
        if (endTime.isBefore(startTime) && Period.ZERO.equals(timeSpan)) {
            throw new IllegalArgumentException("Shift can not end before start");
        }
    }

    public String getNazov() {
        return nazov;
    }

    public Shift vygenerujOd(LocalDate datum, ZoneId zona) {
        ZonedDateTime zaciatok = ZonedDateTime.of(datum, startTime, zona);
        return new Shift(
                Objects.requireNonNull(zaciatok),
                zaciatok.plus(timeSpan).with(endTime),
                TypPolozkyPlanu.SMENA,
                countedDuration,
                this);
    }

    public Shift vygenerujOd(LocalDate datum) {
        return vygenerujOd(datum, ZoneId.systemDefault());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PredlohaSmeny that = (PredlohaSmeny) o;

        return nazov.equals(that.nazov);

    }

    @Override
    public int hashCode() {
        return nazov.hashCode();
    }
}