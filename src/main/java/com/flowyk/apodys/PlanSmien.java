package com.flowyk.apodys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlanSmien {
    List<PolozkaPlanu> polozky;

    public enum Zoradenie {
        VYKONAVATEL(new PoradiePodlaVykonavatela()),
        CAS(new PoradiePodlaZaciatku());

        private Comparator<PolozkaPlanu> comparator;

        Zoradenie(Comparator<PolozkaPlanu> comparator) {
            this.comparator = comparator;
        }
    }

    public PlanSmien() {
        polozky = new ArrayList<>();
    }

    PlanSmien(List<PolozkaPlanu> polozky) {
        this.polozky = polozky;
    }

    public void pridatPolozku(PolozkaPlanu polozka) {
        polozky.add(polozka);
    }

    public PlanSmien zoraditPodla(Zoradenie zoradenie) {
        List<PolozkaPlanu> zoradenePolozky = new ArrayList<>(polozky.size());
        Collections.copy(zoradenePolozky, polozky);
        Collections.sort(zoradenePolozky, zoradenie.comparator);
        return new PlanSmien(zoradenePolozky);
    }

    private static class PoradiePodlaVykonavatela implements Comparator<PolozkaPlanu> {
        @Override
        public int compare(PolozkaPlanu o1, PolozkaPlanu o2) {
            return o1.vykonavatel().compareTo(o2.vykonavatel());
        }
    }

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
}
