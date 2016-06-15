package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.ui.config.event.RosterDataChange;
import com.google.common.collect.Table;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Injector;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    @FXML
    public void initialize() {
        rosterTable.getSelectionModel().setCellSelectionEnabled(true);
    }

    @Subscribe
    public void workplanChanged(RosterDataChange event) {
        rosterTable.getColumns().clear();
        draw(event.getData(), event.getStartDate(), event.getEndDate());
    }

    private void draw(ObservableList<RosterTableRow> rows, LocalDate startDate, LocalDate endDate) {
        addEmployeeColumn();
        addShiftColumns(startDate, endDate);

        rosterTable.setItems(rows);
    }

    private void addEmployeeColumn() {
        TableColumn<RosterTableRow, Zamestnanec> employeeColumn = new TableColumn<>("Zamestnanci");
        employeeColumn.setCellFactory(column -> injector.getInstance(EmployeeTableCell.class));
        employeeColumn.setCellValueFactory(rowData -> new SimpleObjectProperty<>(
                rowData.getValue().getKey()
        ));
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
        TableColumn<RosterTableRow, Shift> shiftColumn = new TableColumn<>(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
        shiftColumn.setCellFactory(column -> {
            DragDropShiftTableCell cell = injector.getInstance(DragDropShiftTableCell.class);
            cell.setColumnHeader(date);
            return cell;
        });

        shiftColumn.setCellValueFactory(rowData -> {
            Shift value = rowData.getValue().get(date);
            return new ReadOnlyObjectWrapper<>(value);
        });
        shiftColumn.setSortable(false);
        shiftColumn.setEditable(false);

        shiftColumn.getStyleClass().add("dayOfWeek-" + date.getDayOfWeek().getValue());

        rosterTable.getColumns().add(shiftColumn);
    }
}
