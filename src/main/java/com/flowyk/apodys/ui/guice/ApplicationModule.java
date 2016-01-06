package com.flowyk.apodys.ui.guice;

import com.flowyk.apodys.PlanSmien;
import com.google.inject.AbstractModule;

import javax.inject.Singleton;
import java.util.Locale;
import java.util.ResourceBundle;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ResourceBundle.class)
                .toInstance(ResourceBundle.getBundle("i18n.Messages", Locale.getDefault()));

    }
}
