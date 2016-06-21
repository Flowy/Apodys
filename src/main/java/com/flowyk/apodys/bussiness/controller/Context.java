package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.*;
import com.flowyk.apodys.planovanie.RuleOffender;
import com.google.common.eventbus.EventBus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

@Singleton
public class Context {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final Export export;
    private final EventBus eventBus;

    private ObservableList<EmployeeShifts> employeeShifts = FXCollections.observableArrayList();
    private ObservableList<PredlohaSmeny> shiftTemplates = FXCollections.observableArrayList();
    private ObservableList<RuleOffender> errors = FXCollections.observableArrayList();

    @Inject
    public Context(EventBus eventBus, Export export) {
        this.eventBus = eventBus;
        this.export = export;
    }

//    public ObservableList<Shift> getShifts() {
//        return shifts;
//    }

//    public ObservableList<Zamestnanec> getEmployees() {
//        return employees;
//    }

    public ObservableList<EmployeeShifts> getEmployeeShifts() {
        return employeeShifts;
    }
    public ObservableList<PredlohaSmeny> getShiftTemplates() {
        return shiftTemplates;
    }

    public ObservableList<RuleOffender> getErrors() {
        return errors;
    }

    public void saveTo(File file) {
//        TODO export
//        export.save(file, new XmlExport(getShifts(), getEmployees(), getShiftTemplates()));
    }

    public void load(File file) {
        logger.info("File loaded: " + file.toString());
        XmlExport newData = export.read(file);

//        TODO export
//        this.shifts.clear();
//        this.shifts.addAll(newData.getShifts());
//
//        this.employees.clear();
//        this.employees.addAll(newData.getEmployees());

        this.shiftTemplates.clear();
        this.shiftTemplates.addAll(newData.getShiftTemplates());

        errors.clear();
    }

    public void resetToDefault() {
        File file = new File(getClass().getClassLoader().getResource("savedRosters/default_roster.xml").getFile());
        load(file);
    }
}
