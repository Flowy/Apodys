package com.flowyk.apodys.pravidlo;

public class VysledokKontrolyPravidla {

    boolean broken;

    public VysledokKontrolyPravidla(boolean broken) {
        this.broken = broken;
    }

    public boolean isBroken() {
        return broken;
    }
}
