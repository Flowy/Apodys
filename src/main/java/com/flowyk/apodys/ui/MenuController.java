package com.flowyk.apodys.ui;


import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.ui.export.ExportService;
import com.flowyk.apodys.ui.guava.event.WorkplanChanged;
import com.google.common.eventbus.EventBus;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.File;
import java.util.logging.Logger;

public class MenuController {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @Inject
    private Context context;

    @Inject
    private Stage stage;

    @Inject
    private EventBus eventBus;
    @Inject
    private ExportService exportService;

    public void createNewPlan(ActionEvent actionEvent) {
        context.setWorkplan(new PlanSmien());
        logger.info("firing workplan changed event");
        eventBus.post(new WorkplanChanged());
    }

    public void saveActual(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            exportService.save(file, context);
        }
    }

    public void loadPlan(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            logger.info("File loaded: " + file.toString());
            //TODO: load xml file
            Context loaded = exportService.read(file);
            context.setWorkplan(loaded.getWorkplan());
            context.setShiftTemplates(loaded.getShiftTemplates());
            context.setEmployees(loaded.getEmployees());
        }
    }

    public void createNewEmployee(ActionEvent actionEvent) {
//        viewManager.goToNewEmployee();
    }
}
