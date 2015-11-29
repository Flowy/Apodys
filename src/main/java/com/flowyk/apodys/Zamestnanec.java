package com.flowyk.apodys;

public class Zamestnanec implements Comparable<Zamestnanec> {
    private String name;
    private String email;

    @Override
    public int compareTo(Zamestnanec o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zamestnanec that = (Zamestnanec) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
