package com.flowyk.apodys.bussiness.boundary;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleOffender;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;

@Singleton
public class RosterBoundary {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Context context;

    @Inject
    public RosterBoundary(Context context) {
        this.context = context;
        context.resetToDefault();
    }

//    public ObservableList<Zamestnanec> getEmployees() {
//        return context.getEmployees();
//    }

    public ObservableList<PredlohaSmeny> getShiftTemplates() {
        return context.getShiftTemplates();
    }

    public ObservableList<RuleOffender> getErrors() {
        return context.getErrors();
    }

    public void createEmployee(String name, String email) {
//        TODO
//        context.getEmployees().add(new Zamestnanec(name, email));
    }

    public Shift override(Shift shift, PredlohaSmeny shiftTemplate) {
//        TODO
//        context.getShifts().remove(shift);
//        return create(shiftTemplate, shift.getZaciatok().toLocalDate(), shift.getEmployee());
        return null;
    }

    public Shift create(PredlohaSmeny shiftTemplate, LocalDate startDate, Zamestnanec employee) {
//        Shift shift = shiftTemplate.vygenerujOd(startDate);
//        shift.setEmployee(employee);
//        context.getShifts().add(shift);
//        return shift;
//        TODO
        return null;
    }

    public void remove(Shift shift) {
//        TODO
//        context.getShifts().remove(shift);
    }
}