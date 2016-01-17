package com.flowyk.apodys.ui;


import com.flowyk.apodys.PlanSmien;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import javax.inject.Inject;
import java.io.File;
import java.util.logging.Logger;

public class MenuController {
    Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @Inject
    HomeController homeController;

    @Inject
    Context context;

    @Inject
    private ViewManager viewManager;

    public void createNewPlan(ActionEvent actionEvent) {
        context.setWorkplan(new PlanSmien());
        homeController.planLoaded();
    }

    public void loadPlan(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(viewManager.getPrimaryStage());

        if (file != null) {
            logger.info("File loaded: " + file.toString());
            //TODO: load xml file
        }
    }

    public void createNewEmployee(ActionEvent actionEvent) {
        viewManager.createEmployee();
    }
}
