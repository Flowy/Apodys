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

    @FXML
    private TextField meno;
    @FXML
    private TextField email;

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
