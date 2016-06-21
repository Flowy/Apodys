package com.flowyk.apodys.bussiness.entity;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

@XmlAccessorType(XmlAccessType.NONE)
public class EmployeeShifts {
    private SimpleObjectProperty<Zamestnanec> employee;
    private ObservableSet<Shift> shifts;

    public EmployeeShifts() {

    }

    public EmployeeShifts(Zamestnanec employee) {
        this.employee = new SimpleObjectProperty<>(employee);
        this.shifts = FXCollections.observableSet(new TreeSet<>(Shift.START_TIME_COMPARATOR));
    }

    public Shift getShift(LocalDate date) {
        return shifts
                .stream()
                .filter(shift -> shift.getZaciatok().toLocalDate().equals(date))
                .findFirst()
                .orElse(null);
    }

    public ObservableSet<Shift> shiftsProperty() {
        return shifts;
    }

    @XmlElementWrapper(name = "zmeny", required = true)
    @XmlElement(name = "zmena")
    public Set<Shift> getShifts() {
        return shifts;
    }

    @XmlIDREF
    public Zamestnanec getEmployee() {
        return employee.getValue();
    }

    public ObservableObjectValue<Zamestnanec> employeeProperty() {
        return employee;
    }

    public void setEmployee(Zamestnanec employee) {
        this.employee.set(employee);
    }
}