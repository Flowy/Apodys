package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.*;
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

    private ObservableList<EmployeeShifts> employeeShifts = FXCollections.observableArrayList();
    private ObservableList<PredlohaSmenyImpl> shiftTemplates = FXCollections.observableArrayList();

    @Inject
    public Context(Export export) {
        this.export = export;

    }

    public ObservableList<EmployeeShifts> getEmployeeShifts() {
        return employeeShifts;
    }

    public ObservableList<PredlohaSmenyImpl> getShiftTemplates() {
        return shiftTemplates;
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
    }

    public void resetToDefault() {
        File file = new File(getClass().getClassLoader().getResource("savedRosters/default_roster.xml").getFile());
        load(file);
    }

    public void addEmployee(String name, String email) {
        this.employeeShifts.add(new EmployeeShifts(new Zamestnanec(name, email)));
    }
}
