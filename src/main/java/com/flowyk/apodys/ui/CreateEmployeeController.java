package com.flowyk.apodys.ui;

import com.flowyk.apodys.Zamestnanec;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.util.logging.Logger;

public class CreateEmployeeController {
    private Logger logger = Logger.getLogger(getClass().getCanonicalName());

    @Inject
    private Context context;
    @Inject
    private Stage stage;

    @FXML
    private TitledPane titledPane;
    @FXML
    private TextField meno;
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
        context.getEmployees().add(new Zamestnanec(meno.getText(), email.getText()));
        reset();
    }

    public void handleCancel(ActionEvent actionEvent) {
        reset();
    }

    private void reset() {
        meno.setText(null);
        email.setText(null);
    }
}
