package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.ui.config.event.RosterDataChange;
import com.google.common.collect.Table;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Injector;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class RosterController {

    @FXML
    private TableView<RosterTableRow> rosterTable;

    @Inject
    private Injector injector;

    @Subscribe
    public void workplanChanged(RosterDataChange event) {
        rosterTable.getColumns().clear();
        draw(event.getData(), event.getStartDate(), event.getEndDate());
    }

    private void draw(Table<Zamestnanec, LocalDate, Shift> data, LocalDate startDate, LocalDate endDate) {
        addEmployeeColumn();
        addShiftColumns(startDate, endDate);

        rosterTable.getSelectionModel().setCellSelectionEnabled(true);
//        rosterTable.setEditable(true);

        rosterTable.setItems(FXCollections.observableArrayList());
        for (Zamestnanec key : data.rowKeySet()) {
            rosterTable.getItems().add(new RosterTableRow(key, data.row(key)));
        }
        rosterTable.getItems().add(new RosterTableRow());
    }

    private void addEmployeeColumn() {
        TableColumn<RosterTableRow, Zamestnanec> employeeColumn = new TableColumn<>("Zamestnanci");
        employeeColumn.setCellFactory(column -> injector.getInstance(EmployeeTableCell.class));
        employeeColumn.setCellValueFactory(rowData -> new SimpleObjectProperty<>(
                rowData.getValue().getKey()
        ));
        employeeColumn.setSortable(false);
        employeeColumn.setEditable(false);
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
        TableColumn<RosterTableRow, Shift> shiftColumn = new TableColumn<>(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        shiftColumn.setCellFactory(column -> injector.getInstance(DragDropShiftTableCell.class));

        shiftColumn.setCellValueFactory(rowData -> {
            Shift value = rowData.getValue().get(date);
            if (value != null && rowData.getValue().getKey() != null) {
                return new SimpleObjectProperty<>(value);
            } else {
                return null;
            }
        });
        shiftColumn.setSortable(false);
        shiftColumn.setEditable(false);

        shiftColumn.getStyleClass().add("dayOfWeek-" + date.getDayOfWeek().getValue());

        rosterTable.getColumns().add(shiftColumn);
    }
}
