package com.flowyk.apodys.ui;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Zamestnanec;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class Context {

    private PlanSmien workplan;
    private List<Zamestnanec> employees;

    public Context() {
        employees = new ArrayList<>();
    }

    public PlanSmien getWorkplan() {
        return workplan;
    }

    public void setWorkplan(PlanSmien workplan) {
        this.workplan = workplan;
    }

    public List<Zamestnanec> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Zamestnanec> employees) {
        this.employees = employees;
    }
}
