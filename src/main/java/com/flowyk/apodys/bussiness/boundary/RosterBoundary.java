package com.flowyk.apodys.bussiness.boundary;

import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.bussiness.entity.XmlDataWrapper;
import com.flowyk.apodys.ui.Context;
import com.flowyk.apodys.bussiness.controller.ExportController;
import com.flowyk.apodys.ui.config.event.ContextUpdated;
import com.flowyk.apodys.ui.config.event.XmlLoaded;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
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

    @Inject
    private Context context;

    @Inject
    private ExportController exportController;
    @Inject
    private EventBus eventBus;

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

    public void createEmployee(String name, String email) {
        context.getEmployees().add(new Zamestnanec(name, email));
    }

    public void newRooster() {
        context = new Context();
        eventBus.post(new ContextUpdated());
    }

    public void override(Shift shift, PredlohaSmeny shiftTemplate) {
        context.getShifts().remove(shift);
        create(shiftTemplate, shift.getZaciatok().toLocalDate(), shift.getEmployee());
    }

    public void create(PredlohaSmeny shiftTemplate, LocalDate startDate, Zamestnanec employee) {
        Shift shift = shiftTemplate.vygenerujOd(startDate);
        shift.setEmployee(employee);
        context.getShifts().add(shift);
        eventBus.post(new ContextUpdated());
    }

    public void remove(Shift shift) {
        context.getShifts().remove(shift);
        eventBus.post(new ContextUpdated());
    }
}