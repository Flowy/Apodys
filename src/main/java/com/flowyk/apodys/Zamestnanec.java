package com.flowyk.apodys;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Zamestnanec implements Comparable<Zamestnanec>, Serializable {

    @XmlID
    @XmlAttribute
    private String name;

    @XmlAttribute
    private String email;

    /**
     * default konstruktor pre JAXB
     */
    public Zamestnanec() {
    }

    public Zamestnanec(String name, String email) {
        this.name = name;
        this.email = email;
    }

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

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
