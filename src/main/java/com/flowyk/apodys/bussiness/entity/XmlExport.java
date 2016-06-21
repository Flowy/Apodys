package com.flowyk.apodys.bussiness.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlExport {


    @XmlElementWrapper(name = "zamestnanci", required = true)
    @XmlElement(name = "zamestnanec")
    private List<EmployeeShifts> employeeShifts;

    @XmlElementWrapper(name = "predlohy", required = true)
    @XmlElement(name = "predlohaZmeny")
    private List<PredlohaSmeny> predlohy;

    /**
     * JAXB constructor
     */
    public XmlExport() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public XmlExport(List<EmployeeShifts> employeeShifts, List<PredlohaSmeny> shiftTemplates) {
        this.employeeShifts = employeeShifts;
        this.predlohy = Objects.requireNonNull(shiftTemplates);
    }

    public List<EmployeeShifts> getEmployeeShifts() {
        return employeeShifts;
    }

    public List<PredlohaSmeny> getShiftTemplates() {
        return predlohy;
    }
}
