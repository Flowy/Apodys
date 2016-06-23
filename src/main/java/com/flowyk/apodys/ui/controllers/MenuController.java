package com.flowyk.apodys.ui.controllers;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.controller.Messages;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

public class MenuController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private Stage stage;

    @Inject
    private Context context;
    @Inject
    private Messages messages;

    private final static FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

    public void initializeDefaultRoster(ActionEvent actionEvent) {
        context.resetToDefault();
    }

    public void saveActual(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(fileType);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            context.saveTo(file);
        }
    }

    public void loadPlan(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(fileType);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            context.load(file);
        }
    }

    public void createNewEmployee(ActionEvent actionEvent) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(messages.getString("create_new_employee"));

        ButtonType createButton = new ButtonType(messages.getString("create"), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType(messages.getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(createButton, cancelButton);

        TextField employeeName = new TextField();
        employeeName.setPromptText(messages.getString("name"));

        dialog.getDialogPane().setContent(employeeName);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButton) {
                return employeeName.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> context.addEmployee(result, null));
    }
}
