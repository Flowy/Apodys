package com.flowyk.apodys.ui;

import com.flowyk.apodys.Shift;
import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.ui.config.event.RoosterDataChange;
import com.google.common.collect.Table;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;
import java.util.logging.Logger;

public class RoosterController {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @FXML
    private TableView<Map<LocalDate, Shift>> roosterTable;

    @Subscribe
    public void workplanChanged(RoosterDataChange event) {
        roosterTable.getColumns().clear();
        draw(event.getData(), event.getStartDate(), event.getEndDate());
    }

    private void draw(Table<Zamestnanec, LocalDate, Shift> data, LocalDate startDate, LocalDate endDate) {
        TableColumn<Map<LocalDate, Shift>, String> dateColumn = new TableColumn<>("Zamestnanci");
        dateColumn.setCellValueFactory(rowData -> new SimpleStringProperty(
                //gets the employee name in first shift in row
                rowData.getValue().entrySet().iterator().next().getValue().vykonavatel().getName()
        ));
        roosterTable.getColumns().add(dateColumn);

        LocalDate actualDate = startDate;
        while (!actualDate.isAfter(endDate)) {
            final LocalDate finalActualDate = actualDate;
            TableColumn<Map<LocalDate, Shift>, String> shiftColumn = new TableColumn<>(finalActualDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            shiftColumn.setCellValueFactory(rowData -> {
                if (rowData.getValue().get(finalActualDate) != null) {
                    return new SimpleStringProperty(
                            rowData.getValue().get(finalActualDate).predloha().getNazov()
                    );
                } else {
                    return null;
                }
            });
            roosterTable.getColumns().add(shiftColumn);
            actualDate = actualDate.plusDays(1L);
        }

        roosterTable.setItems(FXCollections.observableArrayList(data.rowMap().values()));
    }
}
