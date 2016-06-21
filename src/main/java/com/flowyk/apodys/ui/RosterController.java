package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.google.inject.Injector;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class RosterController {

    @FXML
    private TableView<EmployeeShifts> rosterTable;
    @FXML
    private DatePicker firstDayPicker;
    @FXML
    private DatePicker lastDayPicker;

    @Inject
    private Injector injector;

    @Inject
    private RosterBoundary rosterBoundary;

    @Inject
    private ErrorsChangedListener errorsChangedListener;
    @Inject
    private Context context;

    @FXML
    public void initialize() {
        rosterTable.getSelectionModel().setCellSelectionEnabled(true);

        firstDayPicker.setValue(LocalDate.now());
        firstDayPicker.valueProperty().addListener(observable -> {
            refreshColumns();
        });
        lastDayPicker.setValue(LocalDate.now().plusWeeks(1L));
        lastDayPicker.valueProperty().addListener(observable -> {
            refreshColumns();
        });

        rosterBoundary.getErrors().addListener(errorsChangedListener);
        refreshColumns();
        rosterTable.setItems(context.getEmployeeShifts());
    }

    private void refreshColumns() {
        rosterTable.getColumns().clear();

        addEmployeeColumn();
        addShiftColumns(firstDayPicker.getValue(), lastDayPicker.getValue());

    }

    private void addEmployeeColumn() {
        TableColumn<EmployeeShifts, Zamestnanec> employeeColumn = new TableColumn<>("Zamestnanci");
        employeeColumn.setCellFactory(column -> injector.getInstance(EmployeeTableCell.class));
        employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
        employeeColumn.setSortable(false);
        employeeColumn.setEditable(false);

        employeeColumn.getStyleClass().add("employee");

        rosterTable.getColumns().add(employeeColumn);
    }

    private void addShiftColumns(LocalDate startDate, LocalDate endDate) {
        LocalDate actualDate = startDate;
        while (!actualDate.isAfter(endDate)) {
            addShiftColumn(actualDate);
            actualDate = actualDate.plusDays(1L);
        }
    }

    private void addShiftColumn(final LocalDate date) {
        TableColumn<EmployeeShifts, Shift> shiftColumn = new TableColumn<>(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        shiftColumn.setCellFactory(column -> {
            ShiftTableCell cell = injector.getInstance(DragDropShiftTableCell.class);
            cell.setColumnHeader(date);
            errorsChangedListener.addListeningCell(cell);
            return cell;
        });

        shiftColumn.setCellValueFactory(rowData -> {
            Shift value = rowData.getValue().getShift(date);
            return new ReadOnlyObjectWrapper<>(value);
        });
        shiftColumn.setSortable(false);
        shiftColumn.setEditable(false);

        shiftColumn.getStyleClass().add("dayOfWeek-" + date.getDayOfWeek().getValue());

        rosterTable.getColumns().add(shiftColumn);
    }
}
