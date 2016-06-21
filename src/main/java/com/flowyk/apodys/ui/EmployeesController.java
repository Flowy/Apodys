package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class EmployeesController {
    @Inject
    private RosterBoundary rosterBoundary;

    @FXML
    private ListView<Zamestnanec> employees;
    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        employees.setCellFactory(list -> new ZamestnanecCell());
        employees.setItems(rosterBoundary.getEmployees());
    }

    static class ZamestnanecCell extends ListCell<Zamestnanec> {

        @Override
        protected void updateItem(Zamestnanec item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());
            }
        }
    }
}
