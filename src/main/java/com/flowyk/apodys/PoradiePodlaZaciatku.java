package com.flowyk.apodys;

import java.util.Comparator;

public class PoradiePodlaZaciatku implements Comparator<JednotkaPlanu> {
    @Override
    public int compare(JednotkaPlanu o1, JednotkaPlanu o2) {
        int result = o1.zaciatok().compareTo(o2.zaciatok());
        if (result == 0) {
            result = o1.koniec().compareTo(o2.koniec());
        }
        return result;
    }
}
