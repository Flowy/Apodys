package com.flowyk.apodys.bussiness.entity;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

@XmlAccessorType(XmlAccessType.NONE)
public class EmployeeShifts {
    private SimpleObjectProperty<Zamestnanec> employee = new SimpleObjectProperty<>(this, "employee");
    private ObservableSet<Shift> shifts = FXCollections.observableSet(new TreeSet<>(Shift.START_TIME_COMPARATOR));

    public EmployeeShifts() {
    }

    public EmployeeShifts(Zamestnanec employee) {
        setEmployee(employee);
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

    public void setShifts(Collection<Shift> shifts) {
        this.shifts.clear();
        this.shifts.addAll(shifts);
    }

    @XmlElement(required = true)
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