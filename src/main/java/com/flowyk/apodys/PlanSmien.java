package com.flowyk.apodys;

import javax.xml.bind.annotation.*;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlanSmien implements Iterable<PolozkaPlanu> {

    @XmlElementWrapper(name = "polozky")
    @XmlElements({
            @XmlElement(type = PolozkaPlanu.class, name = "smena")
    })
    List<PolozkaPlanu> polozky;

    public PlanSmien() {
        polozky = new ArrayList<>();
    }

    PlanSmien(List<PolozkaPlanu> polozky) {
        this.polozky = Objects.requireNonNull(polozky);
    }

    public void pridatPolozku(PolozkaPlanu polozka) {
        polozky.add(polozka);
    }

    public Duration trvaniePoloziek(TypPolozkyPlanu typ) {
        Duration result = Duration.ZERO;
        for (PolozkaPlanu polozka: polozky) {
            if (polozka.typ().equals(typ)) {
                result = result.plus(polozka.countedDuration());
            }
        }
        return result;
    }

    public PlanSmien zoradit(Comparator<PolozkaPlanu> zoradenie) {
        List<PolozkaPlanu> zoradenePolozky = new ArrayList<>(polozky.size());
        Collections.copy(zoradenePolozky, polozky);
        Collections.sort(zoradenePolozky, zoradenie);
        return new PlanSmien(zoradenePolozky);
    }

    public PlanSmien preObdobie(ZonedDateTime zaciatok, ZonedDateTime koniec) {
        PlanSmien result = new PlanSmien();
        for (PolozkaPlanu polozka: this.polozky) {
            if (polozka.prekryva(zaciatok, koniec)) {
                result.pridatPolozku(polozka);
            }
        }
        return result;
    }

    public PlanSmien preZamestnanca(Zamestnanec zamestnanec) {
        PlanSmien result = new PlanSmien();
        for (PolozkaPlanu polozka: this.polozky) {
            if (zamestnanec.equals(polozka.vykonavatel())) {
                result.pridatPolozku(polozka);
            }
        }
        return result;
    }

    public PlanSmien preTyp(TypPolozkyPlanu typ) {
        PlanSmien result = new PlanSmien();
        for (PolozkaPlanu polozka: this.polozky) {
            if (polozka.typ().equals(typ)) {
                result.pridatPolozku(polozka);
            }
        }
        return result;
    }

    @XmlTransient
    private static class PoradiePodlaVykonavatela implements Comparator<PolozkaPlanu> {
        @Override
        public int compare(PolozkaPlanu o1, PolozkaPlanu o2) {
            return o1.vykonavatel().compareTo(o2.vykonavatel());
        }
    }

    @XmlTransient
    private static class PoradiePodlaZaciatku implements Comparator<PolozkaPlanu> {
        @Override
        public int compare(PolozkaPlanu o1, PolozkaPlanu o2) {
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
        for (PolozkaPlanu polozka: polozky) {
            sb.append(polozka).append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    @Override
    public Iterator<PolozkaPlanu> iterator() {
        return polozky.iterator();
    }

    @Override
    public void forEach(Consumer<? super PolozkaPlanu> action) {
        polozky.forEach(action);
    }

    @Override
    public Spliterator<PolozkaPlanu> spliterator() {
        return polozky.spliterator();
    }
}
