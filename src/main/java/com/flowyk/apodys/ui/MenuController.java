package com.flowyk.apodys.ui;

import com.flowyk.apodys.ui.export.ExportService;
import com.flowyk.apodys.ui.config.event.ContextUpdated;
import com.google.common.eventbus.EventBus;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

public class MenuController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private Context context;

    @Inject
    private Stage stage;

    @Inject
    private EventBus eventBus;
    @Inject
    private ExportService exportService;

    private final static FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

    public void createNewPlan(ActionEvent actionEvent) {
        context.setContext(new Context());
        logger.info("firing workplan changed event");
        eventBus.post(new ContextUpdated());
    }

    public void saveActual(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(fileType);

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            exportService.save(file, context);
        }
    }

    public void loadPlan(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(fileType);

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            logger.info("File loaded: " + file.toString());
            //TODO: load xml file
            Context loaded = exportService.read(file);
            context.setContext(loaded);
        }
    }

    public void createNewEmployee(ActionEvent actionEvent) {
//        viewManager.goToNewEmployee();
    }
}
