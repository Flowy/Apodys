package com.flowyk.apodys.planovanie.pravidlo;

public class VysledokKontrolyPravidla {

    boolean broken;

    public final static VysledokKontrolyPravidla BROKEN = new VysledokKontrolyPravidla(true);
    public final static VysledokKontrolyPravidla OK = new VysledokKontrolyPravidla(false);

    private VysledokKontrolyPravidla(boolean broken) {
        this.broken = broken;
    }

    public boolean isBroken() {
        return broken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VysledokKontrolyPravidla that = (VysledokKontrolyPravidla) o;

        return broken == that.broken;

    }

    @Override
    public int hashCode() {
        return (broken ? 1 : 0);
    }

    @Override
    public String toString() {
        return "VysledokKontrolyPravidla{" +
                "broken=" + broken +
                '}';
    }
}
