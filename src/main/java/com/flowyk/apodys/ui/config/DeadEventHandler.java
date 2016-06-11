package com.flowyk.apodys.ui.config;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeadEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DeadEventHandler.class);

    @Subscribe
    public void handle(DeadEvent event) {
        LOG.warn("Dead event occured: {}", event.getEvent());
    }
}
