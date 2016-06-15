package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
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
    private Stage stage;

    @Inject
    private RosterBoundary rosterBoundary;

    private final static FileChooser.ExtensionFilter fileType = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

    public void createNewPlan(ActionEvent actionEvent) {
        rosterBoundary.newRooster();
    }

    public void saveActual(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(fileType);
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            rosterBoundary.saveTo(file);
        }
    }

    public void loadPlan(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(fileType);
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            rosterBoundary.readFrom(file);
        }
    }
}
