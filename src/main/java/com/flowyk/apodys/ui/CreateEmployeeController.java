package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import javax.inject.Inject;

public class CreateEmployeeController {

    @Inject
    private RosterBoundary rosterBoundary;

    @FXML
    private TitledPane titledPane;
    @FXML
    private TextField name;
    @FXML
    private TextField email;

    public void handleOk(ActionEvent actionEvent) {
        rosterBoundary.createEmployee(name.getText(), email.getText());
        reset();
    }

    public void handleCancel(ActionEvent actionEvent) {
        reset();
    }

    private void reset() {
        name.setText(null);
        email.setText(null);
    }
}
