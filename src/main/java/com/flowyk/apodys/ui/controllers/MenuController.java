package com.flowyk.apodys.ui.controllers;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.controller.Messages;
import com.flowyk.apodys.bussiness.controller.RuleInvestigatorManager;
import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.PredlohaSmienPreObdobie;
import com.flowyk.apodys.csv.TimeOverview;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.planovanie.planner.PatternPlanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
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

        Pane shiftsBox = new VBox(10);
        List<PredlohaSmienPreObdobie> shiftPatterns = new ArrayList<>();

        ObservableList<PredlohaSmeny> choices = FXCollections.observableArrayList();
        choices.add(new VoidShiftTemplate(messages.getString("unplanned_template")));
        choices.addAll(context.getShiftTemplates());

        addTitleRow(shiftsBox);

        for (int week = 0; week < 13; week++) {
            addNewWeekRow(choices, shiftsBox, shiftPatterns);
        }

//        Button addNewWeekButton = new Button(messages.getString("add_new_week"));
//        addNewWeekButton.setOnAction(addNewWeekEvent -> addNewWeekRow(choices, shiftsBox, shiftPatterns));
//        shiftsBox.getChildren().add(addNewWeekButton);

        shiftDialog.getDialogPane().setContent(shiftsBox);

        ButtonType pressedButton = shiftDialog.showAndWait().orElse(cancelButton);
        if (pressedButton == cancelButton) {
            return;
        }

        Dialog<ButtonType> timeDialog = new Dialog<>();
        shiftDialog.setTitle(messages.getString("planning_time"));

        ButtonType planButton = new ButtonType(messages.getString("start_planning"), ButtonBar.ButtonData.OK_DONE);
        timeDialog.getDialogPane().getButtonTypes().addAll(planButton, cancelButton);

        Label startDateLabel = new Label(messages.getString("start_day"));
        DatePicker startDate = new DatePicker(LocalDate.now());
        startDate.setShowWeekNumbers(true);

        Label endDateLabel = new Label(messages.getString("end_day"));
        DatePicker endDate = new DatePicker(LocalDate.now());
        endDate.setShowWeekNumbers(true);

        timeDialog.getDialogPane().setContent(new HBox(10, startDateLabel, startDate, endDateLabel, endDate));

        pressedButton = timeDialog.showAndWait().orElse(cancelButton);
        if (pressedButton == cancelButton) {
            return;
        }

        Planovac planovac = new PatternPlanner(shiftPatterns);
        planovac.naplanuj(
                context.getEmployeeShifts(),
                startDate.getValue(),
                endDate.getValue()
        );
    }

    private void addTitleRow(Pane shiftsBox) {
        Pane row = new HBox(10);
        Label weekTitle = new Label();
        weekTitle.setPrefWidth(70);

        Label monday = new Label(messages.getString("monday"));
        monday.setPrefWidth(100);
        Label tuesday = new Label(messages.getString("tuesday"));
        tuesday.setPrefWidth(100);
        Label wednesday = new Label(messages.getString("wednesday"));
        wednesday.setPrefWidth(100);
        Label thursday = new Label(messages.getString("thursday"));
        thursday.setPrefWidth(100);
        Label friday = new Label(messages.getString("friday"));
        friday.setPrefWidth(100);
        Label saturday = new Label(messages.getString("saturday"));
        saturday.setPrefWidth(100);
        Label sunday = new Label(messages.getString("sunday"));
        sunday.setPrefWidth(100);
        row.getChildren().addAll(weekTitle, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        shiftsBox.getChildren().add(row);
    }

    private void addNewWeekRow(ObservableList<PredlohaSmeny> choices, Pane shiftsBox, List<PredlohaSmienPreObdobie> shiftPatterns) {
        List<PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie> weekValues = new ArrayList<>();
        Pane weekBox = new HBox(10);

        Label label = new Label(messages.parse(new LocalizationUnit("week_number", shiftPatterns.size() + 1)));
        label.setPrefWidth(70);
        weekBox.getChildren().add(label);
        for (int weekDay = 0; weekDay < 7; weekDay++) {
            PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie predloha = new PredlohaSmienPreObdobie.PredlohaSmenyPreObdobie(null, Period.ofDays(weekDay));
            weekValues.add(predloha);
            ChoiceBox<PredlohaSmeny> choice = new ChoiceBox<>(choices);
            choice.valueProperty().addListener((observable, oldValue, newValue) -> {
                predloha.setPredloha(newValue);
            });
            choice.setConverter(new StringConverter<PredlohaSmeny>() {
                @Override
                public String toString(PredlohaSmeny object) {
                    return object.getNazov();
                }

                @Override
                public PredlohaSmeny fromString(String string) {
                    //not editable -> does not need to convert from string
                    return null;
                }
            });
            choice.setPrefWidth(100);
            weekBox.getChildren().add(choice);
        }
        shiftsBox.getChildren().add(weekBox);
        shiftPatterns.add(new PredlohaSmienPreObdobie(weekValues, Period.ofDays(7)));

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
