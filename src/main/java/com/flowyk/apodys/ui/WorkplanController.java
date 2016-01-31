package com.flowyk.apodys.ui;

import com.flowyk.apodys.ui.guava.event.WorkplanChanged;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.util.logging.Logger;

public class WorkplanController {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @Inject
    private EventBus eventBus;

    @Inject
    private Stage stage;

    @FXML
    public GridPane grid;

    @FXML
    public void initialize() {
        eventBus.register(this);
    }

    @Subscribe
    public void workplanChanged(WorkplanChanged event) {
        draw();
        stage.sizeToScene();
    }

    private void draw() {
        grid.add(new Text("Sales"), 0, 0);
        logger.info("grid: " + grid);
    }

    private void drawTitles() {

    }
}
