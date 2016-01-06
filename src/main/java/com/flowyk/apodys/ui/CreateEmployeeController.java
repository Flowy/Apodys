package com.flowyk.apodys.ui;

import com.flowyk.apodys.Zamestnanec;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.inject.Inject;

public class CreateEmployeeController {

    @Inject
    private Context context;

    private Stage stage;

    @FXML
    private TextField meno;
    @FXML
    private TextField email;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleOk(ActionEvent actionEvent) {
        context.getEmployees().add(new Zamestnanec(meno.getText(), email.getText()));
        stage.close();
    }

    public void handleCancel(ActionEvent actionEvent) {
        stage.close();
    }
}
