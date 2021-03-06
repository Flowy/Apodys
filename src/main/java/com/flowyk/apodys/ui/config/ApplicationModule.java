package com.flowyk.apodys.ui.config;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;

public class ApplicationModule extends AbstractModule {

    private Stage stage;

    public ApplicationModule(Stage stage) {
        this.stage = stage;
    }

    @Override
    protected void configure() {
        bind(Stage.class).toInstance(stage);

        EventBus eventBus = new EventBus(new LoggingSubscriberExceptionHandler());
        eventBus.register(new DeadEventHandler());

        bind(EventBus.class).toInstance(eventBus);
    }
}
