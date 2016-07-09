package com.flowyk.apodys.bussiness.entity;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.time.*;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class PredlohaSmenyImpl implements PredlohaSmeny {

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
    public PredlohaSmenyImpl() {
    }

    /**
     * startTime and endTime are in same day
     *
     * @param countedDuration stable duration for this when making statistics
     */
    public PredlohaSmenyImpl(String nazov, LocalTime startTime, LocalTime endTime, Duration countedDuration) {
        this(nazov, startTime, endTime, Period.ZERO, countedDuration);
    }

    public PredlohaSmenyImpl(String nazov, LocalTime startTime, LocalTime endTime, Period timeSpan, Duration countedDuration) {
        this.nazov = Objects.requireNonNull(nazov);
        this.startTime = Objects.requireNonNull(startTime);
        this.endTime = Objects.requireNonNull(endTime);
        this.timeSpan = Objects.requireNonNull(timeSpan);
        this.countedDuration = Objects.requireNonNull(countedDuration);
        if (endTime.isBefore(startTime) && Period.ZERO.equals(timeSpan)) {
            throw new IllegalArgumentException("Shift can not end before start");
        }
    }

    @Override
    public String getNazov() {
        return nazov;
    }

    @Override
    public Shift vygenerujOd(LocalDate datum) {
        ZonedDateTime zaciatok = ZonedDateTime.of(datum, startTime, ZoneId.systemDefault());
        return new Shift(
                Objects.requireNonNull(zaciatok),
                zaciatok.plus(timeSpan).with(endTime),
                TypPolozkyPlanu.SMENA,
                countedDuration,
                this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PredlohaSmenyImpl that = (PredlohaSmenyImpl) o;

        return nazov.equals(that.nazov);

    }

    @Override
    public int hashCode() {
        return nazov.hashCode();
    }
}