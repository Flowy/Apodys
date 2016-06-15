package com.flowyk.apodys.bussiness.entity;

import com.flowyk.apodys.planovanie.RuleInvestigator;
import javafx.beans.*;

import javax.xml.bind.annotation.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlanSmien {

    @XmlElementWrapper(name = "polozky")
    @XmlElements({
            @XmlElement(type = Shift.class, name = "smena")
    })
    private List<Shift> polozky;


    @XmlElementWrapper(name = "zamestnanci")
    @XmlElement(name = "zamestnanec")
    private List<Zamestnanec> zamestnanci;

    public PlanSmien() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public PlanSmien(List<Zamestnanec> zamestnanci) {
        this(new ArrayList<>(), zamestnanci);
    }

    public PlanSmien(List<Shift> polozky, List<Zamestnanec> zamestnanci) {
        this.polozky = Objects.requireNonNull(polozky);
        this.zamestnanci = zamestnanci;
    }

    public List<Zamestnanec> getZamestnanci() {
        return zamestnanci;
    }

    public void odstranitPolozku(Shift polozka) {
        polozky.remove(polozka);
        invalidate();
    }
    public void pridatPolozku(Shift polozka) {
        polozky.add(polozka);
        invalidate();
    }

    public Duration trvaniePoloziek(TypPolozkyPlanu typ) {
        Duration result = Duration.ZERO;
        for (Shift polozka: polozky) {
            if (polozka.typ().equals(typ)) {
                result = result.plus(polozka.countedDuration());
            }
        }
        return result;
    }

    public PlanSmien zoradit(Comparator<Shift> zoradenie) {
        List<Shift> zoradenePolozky = new ArrayList<>(polozky.size());
        Collections.copy(zoradenePolozky, polozky);
        Collections.sort(zoradenePolozky, zoradenie);
        return new PlanSmien(zoradenePolozky, zamestnanci);
    }

    public PlanSmien preObdobie(ZonedDateTime zaciatok, ZonedDateTime koniec) {
        PlanSmien result = new PlanSmien();
        for (Shift polozka: this.polozky) {
            if (polozka.prekryva(zaciatok, koniec)) {
                result.pridatPolozku(polozka);
            }
        }
        return result;
    }

    public PlanSmien preZamestnanca(Zamestnanec zamestnanec) {
        PlanSmien result = new PlanSmien();
        for (Shift polozka: this.polozky) {
            if (zamestnanec.equals(polozka.vykonavatel())) {
                result.pridatPolozku(polozka);
            }
        }
        return result;
    }

    public PlanSmien preTyp(TypPolozkyPlanu typ) {
        PlanSmien result = new PlanSmien();
        for (Shift polozka: this.polozky) {
            if (polozka.typ().equals(typ)) {
                result.pridatPolozku(polozka);
            }
        }
        return result;
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    @XmlTransient
    private static class PoradiePodlaZaciatku implements Comparator<Shift> {
        @Override
        public int compare(Shift o1, Shift o2) {
            int result = o1.zaciatok().compareTo(o2.zaciatok());
            if (result == 0) {
                result = o1.koniec().compareTo(o2.koniec());
            }
            return result;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plan smien:").append(System.getProperty("line.separator"));
        for (Shift polozka: polozky) {
            sb.append(polozka).append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

}
