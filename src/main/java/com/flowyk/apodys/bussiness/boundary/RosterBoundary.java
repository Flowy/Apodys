package com.flowyk.apodys.bussiness.boundary;

import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.bussiness.entity.XmlDataWrapper;
import com.flowyk.apodys.planovanie.RuleInvestigatorManager;
import com.flowyk.apodys.planovanie.RuleOffender;
import com.flowyk.apodys.ui.Context;
import com.flowyk.apodys.bussiness.controller.ExportController;
import com.flowyk.apodys.ui.config.event.ContextUpdated;
import com.google.common.eventbus.EventBus;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.time.LocalDate;

@Singleton
public class RosterBoundary {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Context context;

    @Inject
    private ExportController exportController;
    @Inject
    private EventBus eventBus;

    public RosterBoundary() {
        context = new Context();
        bindShiftsInvalidated();
    }

    public void saveTo(File file) {
        exportController.save(file, context);
    }

    public void readFrom(File file) {
        logger.info("File loaded: " + file.toString());
        XmlDataWrapper newData = exportController.read(file);
        context.setContext(newData.getShifts(), newData.getEmployees(), newData.getShiftTemplates());
        eventBus.post(new ContextUpdated());
    }

    public ObservableList<Zamestnanec> getEmployees() {
        return context.getEmployees();
    }

    public ObservableList<PredlohaSmeny> getShiftTemplates() {
        return context.getShiftTemplates();
    }

    public ObservableList<Shift> getShifts() {
        return context.getShifts();
    }

    public ObservableList<RuleOffender> getErrors() {
        return context.getErrors();
    }

    public void createEmployee(String name, String email) {
        context.getEmployees().add(new Zamestnanec(name, email));
    }

    public void newRooster() {
        context = new Context();
        eventBus.post(new ContextUpdated());
    }

    public Shift override(Shift shift, PredlohaSmeny shiftTemplate) {
        context.getShifts().remove(shift);
        return create(shiftTemplate, shift.getZaciatok().toLocalDate(), shift.getEmployee());
    }

    public Shift create(PredlohaSmeny shiftTemplate, LocalDate startDate, Zamestnanec employee) {
        Shift shift = shiftTemplate.vygenerujOd(startDate);
        shift.setEmployee(employee);
        context.getShifts().add(shift);
        return shift;
    }

    public void remove(Shift shift) {
        context.getShifts().remove(shift);
    }

    private void bindShiftsInvalidated() {
        InvalidationListener shiftsInvalidatedListener = observable -> findErrorsInShifts();
        context.getShifts().addListener(shiftsInvalidatedListener);
    }

    private RuleInvestigatorManager manager;

    private void findErrorsInShifts() {
        if (manager == null) {
            manager = new RuleInvestigatorManager();
        }

        getErrors().clear();
        getErrors().addAll(manager.findOffenders(getShifts().sorted((o1, o2) -> {
            int timeCompare = o1.getZaciatok().compareTo(o2.getZaciatok());
            if (timeCompare != 0) {
                return timeCompare;
            }
            int employeeCompare = o1.getEmployee().compareTo(o2.getEmployee());
            return employeeCompare;
        })));
        logger.debug("Found {} errors", getErrors().size());
    }
}