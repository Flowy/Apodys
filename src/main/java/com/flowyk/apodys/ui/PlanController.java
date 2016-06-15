package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.ui.config.event.RosterDataChange;
import com.flowyk.apodys.ui.config.event.ContextUpdated;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;
import java.time.*;

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

    public PlanController() {
    }

    @FXML
    public void initialize() {
        firstDayPicker.setValue(LocalDate.now());
        firstDayPicker.valueProperty().addListener(observable -> {
            eventBus.post(new RosterDataChange(parseData(), firstDayPicker.getValue(), lastDayPicker.getValue()));
        });
        lastDayPicker.setValue(LocalDate.now().plusWeeks(1L));
        lastDayPicker.valueProperty().addListener(observable -> {
            eventBus.post(new RosterDataChange(parseData(), firstDayPicker.getValue(), lastDayPicker.getValue()));
        });
//        context.addListener(observable -> redraw());
    }

    @Subscribe
    public void dataChanged(ContextUpdated event) {
        eventBus.post(new RosterDataChange(
                parseData(),
                firstDayPicker.getValue(),
                lastDayPicker.getValue())
        );
    }


    private Table<Zamestnanec, LocalDate, Shift> parseData() {
        HashBasedTable<Zamestnanec, LocalDate, Shift> data = HashBasedTable.create();
        ZonedDateTime start = ZonedDateTime.of(firstDayPicker.getValue(), LocalTime.MIDNIGHT, ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(lastDayPicker.getValue(), LocalTime.MAX, ZoneId.systemDefault());
        for (Shift shift : rosterBoundary.getShifts()) {
            if (shift.getZaciatok().isAfter(end) || shift.getZaciatok().isBefore(start)) {
                continue;
            }
            data.put(shift.getEmployee(), shift.getZaciatok().toLocalDate(), shift);
        }
        return data;
    }



//    private void redraw() {
//        planGrid.getChildren().clear();
//        List<LocalDate> days = initializeDays(firstDayPicker.getValue(), lastDayPicker.getValue());
//        List<Zamestnanec> employees = context.getEmployees();
//        for (Zamestnanec employee: employees) {
//            planGrid.add(
//                    new Text(employee.getName()),
//                    0,
//                    employees.indexOf(employee) + 1
//            );
//        }
//
//        for (LocalDate day: days) {
//            planGrid.add(
//                    new Text(day.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))),
//                    days.indexOf(day) + 1,
//                    0
//            );
//        }
//
//        for (Zamestnanec zamestnanec: employees) {
//            for (LocalDate day: days) {
//                int row = employees.indexOf(zamestnanec) + 1;
//                int column = days.indexOf(day) + 1;
//                ShiftCell shiftCell = new ShiftCell(day, zamestnanec, context.getWorkplan());
//                shiftCell.getStyleClass().addAll(
//                        row % 2 == 0 ? "even" : "odd",
//                        dayOfWeek(day)
//                );
//                planGrid.add(shiftCell, column, row);
//            }
//        }
//
//        for (Shift shift : context.getWorkplan()) {
//            if (shouldShow(shift)) {
//                planGrid.add(
//                        new ShiftCell(shift, context.getWorkplan()),
//                        days.indexOf(shift.zaciatok().toLocalDate()) + 1,
//                        employees.indexOf(shift.vykonavatel()) + 1);
//            }
//        }
//        stage.sizeToScene();
//    }


//    private List<LocalDate> initializeDays(LocalDate first, LocalDate last) {
//        List<LocalDate> result = new ArrayList<>();
//        LocalDate actual = first;
//        while (!actual.isAfter(last)) {
//            result.add(actual);
//            actual = actual.plusDays(1L);
//        }
//        return result;
//    }

//    private boolean shouldShow(Shift shift) {
//        ZonedDateTime start = ZonedDateTime.of(firstDayPicker.getValue(), LocalTime.MIDNIGHT, ZoneId.systemDefault());
//        ZonedDateTime end = ZonedDateTime.of(lastDayPicker.getValue(), LocalTime.MAX, ZoneId.systemDefault());
//        return !shift.zaciatok().isBefore(start) && !shift.zaciatok().isAfter(end);
//    }
}
