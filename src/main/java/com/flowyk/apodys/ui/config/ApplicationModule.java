package com.flowyk.apodys.ui.config;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;

import javax.inject.Singleton;

public class ApplicationModule extends AbstractModule {

    private Stage stage;

    public ApplicationModule(Stage stage) {
        this.stage = stage;
    }

    @Override
    protected void configure() {
        bind(Stage.class).toInstance(stage);

        EventBus eventBus = new EventBus();
        eventBus.register(new DeadEventHandler());
        bind(EventBus.class).toInstance(eventBus);
    }
}
