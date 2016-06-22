package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.XmlExport;
import com.flowyk.apodys.planovanie.RuleInvestigatorManager;
import com.flowyk.apodys.planovanie.RuleOffender;
import com.google.common.eventbus.EventBus;
import javafx.beans.InvalidationListener;
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

    private RuleInvestigatorManager manager;

    @Inject
    public Context(EventBus eventBus, Export export) {
        this.eventBus = eventBus;
        this.export = export;

        InvalidationListener shiftsInvalidatedListener = observable -> findErrorsInShifts();
        this.employeeShifts.addListener(shiftsInvalidatedListener);
    }

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
        export.save(file, new XmlExport(this.employeeShifts, this.shiftTemplates));
    }

    public void load(File file) {
        logger.info("File loaded: " + file.toString());
        XmlExport newData = export.read(file);

        this.employeeShifts.clear();
        this.employeeShifts.addAll(newData.getEmployeeShifts());

        this.shiftTemplates.clear();
        this.shiftTemplates.addAll(newData.getShiftTemplates());

        errors.clear();
    }

    public void resetToDefault() {
        File file = new File(getClass().getClassLoader().getResource("savedRosters/default_roster.xml").getFile());
        load(file);
    }

    private void findErrorsInShifts() {
        if (manager == null) {
            manager = new RuleInvestigatorManager();
        }

        getErrors().clear();
        getErrors().addAll(manager.findOffenders(getEmployeeShifts()));
        logger.debug("Found {} errors", getErrors().size());
    }
}
