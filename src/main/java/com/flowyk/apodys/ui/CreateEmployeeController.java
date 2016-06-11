package com.flowyk.apodys.ui;

import com.flowyk.apodys.Zamestnanec;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

import javax.inject.Inject;

public class CreateEmployeeController {

    @Inject
    private Context context;

    @FXML
    private TitledPane titledPane;
    @FXML
    private TextField name;
    @FXML
    private TextField email;

    @FXML
    public void initialize() {
//        titledPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
//            logger.info("collapsible changed to: " + newValue);
//            if (newValue) {
//                titledPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
//            } else {
//                titledPane.setMaxSize(20d, 20d);
//            }
////            stage.getScene().getRoot().requestLayout();
//        });
    }

    public void handleOk(ActionEvent actionEvent) {
        context.getEmployees().add(new Zamestnanec(name.getText(), email.getText()));
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
