package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.controller.RuleInvestigatorManager;
import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.google.inject.Injector;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ErrorsChangedListener errorsChangedListener;
    @Inject
    private Context context;
    @Inject
    private RuleInvestigatorManager ruleInvestigatorManager;

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

        ruleInvestigatorManager.getErrors().addListener(errorsChangedListener);
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

        shiftColumn.setCellValueFactory(rowData -> new ObjectBinding<Shift>() {
            //the cell will refresh if shifts are changed
            {
                bind(rowData.getValue().shiftsProperty());
            }
            @Override
            protected Shift computeValue() {
                return rowData.getValue().getShift(date);
            }
        });
        shiftColumn.setSortable(false);
        shiftColumn.setEditable(false);

        shiftColumn.getStyleClass().add("dayOfWeek-" + date.getDayOfWeek().getValue());

        rosterTable.getColumns().add(shiftColumn);
    }
}
