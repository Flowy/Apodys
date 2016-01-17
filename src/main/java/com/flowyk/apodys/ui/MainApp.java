package com.flowyk.apodys.ui;

import com.flowyk.apodys.ui.guice.ApplicationModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class MainApp extends javafx.application.Application {

    private Parent root;
    @Override
    public void init() throws Exception {

        super.init();
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Injector injector = Guice.createInjector(new ApplicationModule());

        ViewManager viewManager = injector.getInstance(ViewManager.class);
        viewManager.setPrimaryStage(primaryStage);
        viewManager.initRootLayout();
    }

}
