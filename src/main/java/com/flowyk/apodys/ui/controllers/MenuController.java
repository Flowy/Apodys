package com.flowyk.apodys.ui.controllers;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.controller.Messages;
import com.flowyk.apodys.bussiness.controller.RuleInvestigatorManager;
import com.flowyk.apodys.bussiness.entity.PredlohaSmienPreObdobie;
import com.flowyk.apodys.csv.TimeOverview;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private Stage stage;
    @Inject
    private Context context;
    @Inject
    private Messages messages;
    @Inject
    private RuleInvestigatorManager ruleInvestigatorManager;
    @Inject
    private TimeOverview timeOverview;

    private final static FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
    private final static FileChooser.ExtensionFilter csvFileType = new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv");

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

    public void findErrors(ActionEvent event) {
        ruleInvestigatorManager.recalculate();
    }

    public void exportShifts(ActionEvent actionEvent) {
        Dialog<CsvExportTimes> dialog = new Dialog<>();
        dialog.setTitle(messages.getString("export_shift_overview"));

        ButtonType continueButton = new ButtonType(messages.getString("continue"), ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType cancelButton = new ButtonType(messages.getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(continueButton, cancelButton);

        DatePicker startDate = new DatePicker(LocalDate.now());
        startDate.setShowWeekNumbers(true);

        DatePicker endDate = new DatePicker(LocalDate.now());
        endDate.setShowWeekNumbers(true);

        dialog.getDialogPane().setContent(new HBox(10, startDate, endDate));

        dialog.setResultConverter(button -> {
            if (button == continueButton) {
                return new CsvExportTimes(startDate.getValue(), endDate.getValue());
            }
            return null;
        });

        Optional<CsvExportTimes> result = dialog.showAndWait();

        if (result.isPresent()) {
            System.out.println(result);
        } else {
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(csvFileType);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            timeOverview.export(file, context.getEmployeeShifts(), result.get().getStartDate(), result.get().getEndDate());
        }
    }

    public void planShifts(ActionEvent event) {
        Dialog<ButtonType> shiftDialog = new Dialog<>();
        shiftDialog.setTitle(messages.getString("planning_patterns"));

        ButtonType continueButton = new ButtonType(messages.getString("continue"), ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType cancelButton = new ButtonType(messages.getString("cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        shiftDialog.getDialogPane().getButtonTypes().addAll(continueButton, cancelButton);

        HBox shiftsBox = new HBox(10);
//        List<PredlohaSmienPreObdobie> shiftPatterns = new ArrayList<>();
//        List<ObservableObjectValue<PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie>> week = new ArrayList<>();
//
//        for (int i = 0; i<7; i++) {
//            ObservableObjectValue<PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie> predloha = new SimpleObjectProperty<>();
//            tyzden.add(new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(predlohaR2P, Period.ofDays(0)));
//        }
//        VBox weekBox = new VBox(10);
//        weekBox.getChildren().add(new Label("Týždeň"));
//        weekBox.getChildren().add(new ChoiceBox())

        shiftDialog.getDialogPane().setContent(shiftsBox);

        ButtonType pressedButton = shiftDialog.showAndWait().orElse(cancelButton);
        if (pressedButton == cancelButton) {
            return;
        }

        Dialog<ButtonType> timeDialog = new Dialog<>();
        shiftDialog.setTitle(messages.getString("planning_time"));

        ButtonType planButton = new ButtonType(messages.getString("start_planning"), ButtonBar.ButtonData.OK_DONE);
        timeDialog.getDialogPane().getButtonTypes().addAll(planButton, cancelButton);

        DatePicker startDate = new DatePicker(LocalDate.now());
        startDate.setShowWeekNumbers(true);

        DatePicker endDate = new DatePicker(LocalDate.now());
        endDate.setShowWeekNumbers(true);

        timeDialog.getDialogPane().setContent(new HBox(10));

        pressedButton = timeDialog.showAndWait().orElse(cancelButton);
        if (pressedButton == cancelButton) {
            return;
        }
        //TODO: plan shifts with given data

    }

    private static class CsvExportTimes {
        private LocalDate startDate;
        private LocalDate endDate;

        public CsvExportTimes(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }
}
