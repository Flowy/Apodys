package com.flowyk.apodys.ui;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;

import javax.inject.Inject;
import java.util.logging.Logger;

public class HomeController {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @Inject
    private Context context;

    @FXML
    public void initialize() {
    }


    public void planLoaded() {
        logger.info("Plan loaded");
    }
}