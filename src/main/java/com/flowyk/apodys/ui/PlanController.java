package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.ui.config.event.RosterDataChange;
import com.flowyk.apodys.ui.config.event.ContextUpdated;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;
import java.time.*;
import java.util.Map;
import java.util.TreeMap;

public class PlanController {

    @Inject
    private EventBus eventBus;
    @Inject
    private RosterBoundary rosterBoundary;
    @FXML
    private GridPane planGrid;
    @FXML
    private DatePicker firstDayPicker;
    @FXML
    private DatePicker lastDayPicker;

    private ObservableList<RosterTableRow> rows = FXCollections.observableArrayList();

    public PlanController() {
    }

    @FXML
    public void initialize() {
        firstDayPicker.setValue(LocalDate.now());
        firstDayPicker.valueProperty().addListener(observable -> {
            update();
        });
        lastDayPicker.setValue(LocalDate.now().plusWeeks(1L));
        lastDayPicker.valueProperty().addListener(observable -> {
            update();
        });

        rosterBoundary.getEmployees().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                update();
            }
        });
    }

    @Subscribe
    public void dataChanged(ContextUpdated event) {
        update();
    }

    private void update() {
        rows = parseData();
        eventBus.post(new RosterDataChange(
                rows,
                firstDayPicker.getValue(),
                lastDayPicker.getValue())
        );
    }

    private ObservableList<RosterTableRow> parseData() {
        Map<Zamestnanec, RosterTableRow> map = new TreeMap <>((o1, o2) -> o1.getName().compareTo(o2.getName()));
        ZonedDateTime start = ZonedDateTime.of(firstDayPicker.getValue(), LocalTime.MIDNIGHT, ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(lastDayPicker.getValue(), LocalTime.MAX, ZoneId.systemDefault());

        rosterBoundary.getEmployees().forEach(emp -> map.put(emp, new RosterTableRow(emp)));
        for (Shift shift : rosterBoundary.getShifts()) {
            if (shift.getZaciatok().isAfter(end) || shift.getZaciatok().isBefore(start)) {
                continue;
            }
            Zamestnanec emp = shift.getEmployee();
            map.putIfAbsent(emp, new RosterTableRow(emp));
            map.get(emp).putCell(shift.getZaciatok().toLocalDate(), shift);
        }

        return FXCollections.observableArrayList(map.values());
    }
}
