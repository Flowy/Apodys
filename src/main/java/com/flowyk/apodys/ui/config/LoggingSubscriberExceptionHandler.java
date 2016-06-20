package com.flowyk.apodys.ui.config;

import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingSubscriberExceptionHandler implements SubscriberExceptionHandler{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handleException(Throwable exception, SubscriberExceptionContext context) {
        logger.error("Could not dispatch event: " + context.getEvent() + " to " + context.getSubscriberMethod(), exception);
    }
}
