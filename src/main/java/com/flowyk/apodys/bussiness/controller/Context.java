package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.XmlExport;
import com.flowyk.apodys.planovanie.RuleOffender;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.ListBinding;
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
    private final RuleInvestigatorManager manager;

    private ObservableList<EmployeeShifts> employeeShifts = FXCollections.observableArrayList();
    private ObservableList<PredlohaSmeny> shiftTemplates = FXCollections.observableArrayList();
    private ListBinding<RuleOffender> errors;


    private InvalidationListener shiftsInvalidatedListener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            errors.invalidate();
        }
    };
    private InvalidationListener employeeShiftsListener = observable -> {
        ObservableList<EmployeeShifts> employeeShiftsList = (ObservableList<EmployeeShifts>) observable;
        employeeShiftsList.forEach(employeeShifts1 -> {
            employeeShifts1.shiftsProperty().removeListener(shiftsInvalidatedListener);
            employeeShifts1.shiftsProperty().addListener(shiftsInvalidatedListener);
        });
    };

    @Inject
    public Context(Export export, RuleInvestigatorManager ruleInvestigatorManager) {
        this.export = export;
        this.manager = ruleInvestigatorManager;

        this.employeeShifts.addListener(employeeShiftsListener);

        errors = new ListBinding<RuleOffender>() {
            @Override
            protected ObservableList<RuleOffender> computeValue() {
                return FXCollections.observableArrayList(ruleInvestigatorManager.findOffenders(getEmployeeShifts()));
            }
        };
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

//    private void findErrorsInShifts() {
//        if (manager == null) {
//            manager = new RuleInvestigatorManager();
//        }
//
//        getErrors().clear();
//        getErrors().addAll(manager.findOffenders(getEmployeeShifts()));
//        logger.debug("Found {} errors", getErrors().size());
//    }
}
