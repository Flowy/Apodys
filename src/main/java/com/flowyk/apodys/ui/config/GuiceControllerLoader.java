package com.flowyk.apodys.ui.config;

import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.util.Callback;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

@Singleton
public class GuiceControllerLoader implements Callback<Class<?>, Object> {
    private final Injector injector;

    @Inject
    public GuiceControllerLoader(Injector injector) {
        this.injector = injector;
    }

    @Inject
    private EventBus eventBus;

    @Override
    public Object call(Class<?> controller) {
        Object object = injector.getInstance(controller);
        eventBus.register(object);
        return object;
    }
}
