package com.flowyk.apodys.ui;

import com.flowyk.apodys.ui.config.ApplicationModule;
import com.flowyk.apodys.ui.config.GuiceControllerLoader;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.inject.Singleton;
import java.util.Locale;
import java.util.ResourceBundle;

@Singleton
public class MainApp extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new ApplicationModule(primaryStage));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n.Messages", Locale.getDefault());

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("home.fxml"),
                resourceBundle,
                null,
                injector.getInstance(GuiceControllerLoader.class));
        Parent root = loader.load();

        primaryStage.setTitle(resourceBundle.getString("app_title"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
