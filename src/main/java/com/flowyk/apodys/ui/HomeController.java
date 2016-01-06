package com.flowyk.apodys.ui;

import com.flowyk.apodys.PlanSmien;

import javax.inject.Inject;
import java.util.logging.Logger;

public class HomeController {
    private Logger logger = Logger.getLogger(this.getClass().getCanonicalName());

    @Inject
    private Context context;


    public void planLoaded() {
        logger.info("Plan loaded");
    }
}