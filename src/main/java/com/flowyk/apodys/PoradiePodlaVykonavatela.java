package com.flowyk.apodys;

import java.util.Comparator;

public class PoradiePodlaVykonavatela implements Comparator<JednotkaPlanu> {
    @Override
    public int compare(JednotkaPlanu o1, JednotkaPlanu o2) {
        return o1.vykonavatel().compareTo(o2.vykonavatel());
    }
}
