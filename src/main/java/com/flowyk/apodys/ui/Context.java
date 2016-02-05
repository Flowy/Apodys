package com.flowyk.apodys.ui;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.Zamestnanec;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Context implements Observable {

    private PlanSmien workplan;
    private ObservableList<Zamestnanec> employees;
    private ObservableList<PredlohaSmeny> shiftTemplates;

    private List<InvalidationListener> listeners = new ArrayList<>();

    public Context() {
        this(new PlanSmien(), new ArrayList<>(), new ArrayList<>());
    }

    public Context(PlanSmien workplan, List<Zamestnanec> employees, List<PredlohaSmeny> shiftTemplates) {
        this.setContext(workplan, employees, shiftTemplates);
    }

    public void setContext(Context newContext) {
        setContext(newContext.getWorkplan(), newContext.getEmployees(), newContext.getShiftTemplates());
    }

    public void setContext(PlanSmien workplan, List<Zamestnanec> employees, List<PredlohaSmeny> shiftTemplate) {
        this.workplan = workplan;
        setEmployees(employees);
        setShiftTemplates(shiftTemplate);
        invalidate();
    }

    public PlanSmien getWorkplan() {
        return workplan;
    }

    public ObservableList<Zamestnanec> getEmployees() {
        return employees;
    }

    public ObservableList<PredlohaSmeny> getShiftTemplates() {
        return shiftTemplates;
    }

    private void setEmployees(List<Zamestnanec> employees) {
        if (this.employees == null) {
            this.employees = FXCollections.observableList(employees);
            this.employees.addListener((InvalidationListener) c -> invalidate());
        } else {
            this.employees.clear();
            this.employees.addAll(employees);
        }
    }


    private void setShiftTemplates(List<PredlohaSmeny> shiftTemplates) {
        if (this.shiftTemplates == null) {
            this.shiftTemplates = FXCollections.observableList(shiftTemplates);
            this.shiftTemplates.addListener((InvalidationListener) listener -> invalidate());
        } else {
            this.shiftTemplates.clear();
            this.shiftTemplates.addAll(shiftTemplates);
        }
    }

    private void invalidate() {
        for (InvalidationListener listener : listeners) {
            listener.invalidated(this);
        }
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }
}
