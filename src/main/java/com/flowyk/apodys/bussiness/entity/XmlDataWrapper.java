package com.flowyk.apodys.bussiness.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlDataWrapper {

    @XmlElementWrapper(name = "zmeny", required = true)
    @XmlElement(name = "zmena")
    private List<Shift> shifts;

    @XmlElementWrapper(name = "zamestnanci", required = true)
    @XmlElement(name = "zamestnanec")
    private List<Zamestnanec> employees;

    @XmlElementWrapper(name = "predlohy", required = true)
    @XmlElement(name = "predlohaZmeny")
    private List<PredlohaSmeny> predlohy;

    /**
     * JAXB constructor
     */
    public XmlDataWrapper() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public XmlDataWrapper(List<Shift> shifts, List<Zamestnanec> employees, List<PredlohaSmeny> shiftTemplates) {
        this.shifts = Objects.requireNonNull(shifts);
        this.employees = Objects.requireNonNull(employees);
        this.predlohy = Objects.requireNonNull(shiftTemplates);
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public List<Zamestnanec> getEmployees() {
        return employees;
    }

    public List<PredlohaSmeny> getShiftTemplates() {
        return predlohy;
    }
}
