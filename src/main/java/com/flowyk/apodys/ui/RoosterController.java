package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.ui.config.event.RoosterDataChange;
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

public class RoosterController {

    @FXML
    private TableView<RoosterTableRow> roosterTable;

    @Inject
    private Injector injector;

    @Subscribe
    public void workplanChanged(RoosterDataChange event) {
        roosterTable.getColumns().clear();
        draw(event.getData(), event.getStartDate(), event.getEndDate());
    }

    private void draw(Table<Zamestnanec, LocalDate, Shift> data, LocalDate startDate, LocalDate endDate) {
        addEmployeeColumn();
        addShiftColumns(startDate, endDate);

        roosterTable.getSelectionModel().setCellSelectionEnabled(true);
//        roosterTable.setEditable(true);

        roosterTable.setItems(FXCollections.observableArrayList());
        for (Zamestnanec key : data.rowKeySet()) {
            roosterTable.getItems().add(new RoosterTableRow(key, data.row(key)));
        }
        roosterTable.getItems().add(new RoosterTableRow());
    }

    private void addEmployeeColumn() {
        TableColumn<RoosterTableRow, Zamestnanec> employeeColumn = new TableColumn<>("Zamestnanci");
        employeeColumn.setCellFactory(column -> injector.getInstance(EmployeeTableCell.class));
        employeeColumn.setCellValueFactory(rowData -> new SimpleObjectProperty<>(
                rowData.getValue().getKey()
        ));
        employeeColumn.setSortable(false);
        employeeColumn.setEditable(false);
        roosterTable.getColumns().add(employeeColumn);
    }

    private void addShiftColumns(LocalDate startDate, LocalDate endDate) {
        LocalDate actualDate = startDate;
        while (!actualDate.isAfter(endDate)) {
            addShiftColumn(actualDate);
            actualDate = actualDate.plusDays(1L);
        }
    }

    private void addShiftColumn(final LocalDate date) {
        TableColumn<RoosterTableRow, Shift> shiftColumn = new TableColumn<>(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        shiftColumn.setCellFactory(column -> injector.getInstance(ShiftTableCell.class));

        shiftColumn.setCellValueFactory(rowData -> {
            Shift value = rowData.getValue().get(date);
            if (value != null && rowData.getValue().getKey() != null) {
                return value;
            } else {
                return null;
            }
        });
        shiftColumn.setSortable(false);
        shiftColumn.setEditable(false);

        shiftColumn.getStyleClass().add("dayOfWeek-" + date.getDayOfWeek().getValue());

        roosterTable.getColumns().add(shiftColumn);
    }
}
