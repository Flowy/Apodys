package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleOffender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Context {

    private ObservableList<Shift> shifts;
    private ObservableList<Zamestnanec> employees;
    private ObservableList<PredlohaSmeny> shiftTemplates;
    private ObservableList<RuleOffender> errors = FXCollections.observableArrayList();

    public Context() {
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Context(List<Shift> shifts, List<Zamestnanec> employees, List<PredlohaSmeny> shiftTemplates) {
        this.setContext(shifts, employees, shiftTemplates);
    }

    public void setContext(List<Shift> shifts, List<Zamestnanec> employees, List<PredlohaSmeny> shiftTemplate) {
        setShifts(shifts);
        setEmployees(employees);
        setShiftTemplates(shiftTemplate);
        errors.clear();
    }

    public ObservableList<Shift> getShifts() {
        return shifts;
    }

    public ObservableList<Zamestnanec> getEmployees() {
        return employees;
    }

    public ObservableList<PredlohaSmeny> getShiftTemplates() {
        return shiftTemplates;
    }

    public ObservableList<RuleOffender> getErrors() {
        return errors;
    }

    private void setEmployees(List<Zamestnanec> employees) {
        if (this.employees == null) {
            this.employees = FXCollections.observableList(employees);
//            this.employees.addListener((InvalidationListener) c -> invalidate());
        } else {
            this.employees.clear();
            this.employees.addAll(employees);
        }
    }


    private void setShiftTemplates(List<PredlohaSmeny> shiftTemplates) {
        if (this.shiftTemplates == null) {
            this.shiftTemplates = FXCollections.observableList(shiftTemplates);
//            this.shiftTemplates.addListener((InvalidationListener) listener -> invalidate());
        } else {
            this.shiftTemplates.clear();
            this.shiftTemplates.addAll(shiftTemplates);
        }
    }

    private void setShifts(List<Shift> shifts) {
        if (this.shifts == null) {
            this.shifts = FXCollections.observableList(shifts);
        } else {
            this.shifts.clear();
            this.shifts.addAll(shifts);
        }
    }
}
