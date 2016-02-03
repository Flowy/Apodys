package com.flowyk.apodys.ui;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.Zamestnanec;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Context {

    private PlanSmien workplan;
    private ObservableList<Zamestnanec> employees;
    private ObservableList<PredlohaSmeny> shiftTemplates;

    public Context() {
        this(null, new ArrayList<>(), new ArrayList<>());
    }

    public Context(PlanSmien workplan, List<Zamestnanec> employees, List<PredlohaSmeny> shiftTemplates) {
        setWorkplan(workplan);
        setEmployees(employees);
        setShiftTemplates(shiftTemplates);
    }

    public PlanSmien getWorkplan() {
        return workplan;
    }

    public void setWorkplan(PlanSmien workplan) {
        this.workplan = workplan == null ? new PlanSmien() : workplan;
    }

    public ObservableList<Zamestnanec> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Zamestnanec> employees) {
        this.employees = FXCollections.observableList(employees);
    }

    public ObservableList<PredlohaSmeny> getShiftTemplates() {
        return shiftTemplates;
    }

    public void setShiftTemplates(List<PredlohaSmeny> shiftTemplates) {
        this.shiftTemplates = FXCollections.observableList(shiftTemplates);
    }
}
