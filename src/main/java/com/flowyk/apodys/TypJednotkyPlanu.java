package com.flowyk.apodys;

import java.util.Objects;

public class TypJednotkyPlanu {

    private String typ;

    public TypJednotkyPlanu(String typ) {
        this.typ = Objects.requireNonNull(typ);
    }

    public String key() {
        return typ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypJednotkyPlanu that = (TypJednotkyPlanu) o;

        return typ.equals(that.typ);

    }

    @Override
    public int hashCode() {
        return typ.hashCode();
    }

    @Override
    public String toString() {
        return "TypJednotkyPlanu{" +
                "typ='" + typ + '\'' +
                '}';
    }
}
