package com.flowyk.apodys;

import java.util.Objects;

public class TypPolozkyPlanu {

    private String typ;

    public TypPolozkyPlanu(String typ) {
        this.typ = Objects.requireNonNull(typ);
    }

    public String key() {
        return typ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypPolozkyPlanu that = (TypPolozkyPlanu) o;

        return typ.equals(that.typ);

    }

    @Override
    public int hashCode() {
        return typ.hashCode();
    }

    @Override
    public String toString() {
        return "TypPolozkyPlanu{" +
                "typ='" + typ + '\'' +
                '}';
    }
}
