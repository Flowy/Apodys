package com.flowyk.apodys.ui;

import com.flowyk.apodys.ui.guava.event.WorkplanChanged;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;

import javax.inject.Inject;
import java.util.logging.Logger;

public class WorkplanController {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @Inject
    private EventBus eventBus;

    @FXML
    public void initialize() {
        eventBus.register(this);
    }

    @Subscribe
    public void workplanChanged(WorkplanChanged event) {
        logger.info("workplan changed event received");
    }
}
