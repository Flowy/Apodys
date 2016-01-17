package com.flowyk.apodys.ui;

import com.flowyk.apodys.ui.guice.GuiceControllerLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.ResourceBundle;

@Singleton
public class ViewManager {

    @Inject
    private GuiceControllerLoader guiceControllerLoader;

    @Inject
    private ResourceBundle resourceBundle;

    @Inject
    private Stage stage;

    public void goToNewEmployee() {
        FXMLLoader loader = getLoaderFor("create_employee.fxml");
        AnchorPane page = load(loader);
        Stage dialogStage = new Stage();
        dialogStage.setTitle(resourceBundle.getString("create_new_employee"));
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(stage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        ((CreateEmployeeController) loader.getController()).setStage(dialogStage);

        dialogStage.showAndWait();
    }

    private FXMLLoader getLoaderFor(String file) {
        return new FXMLLoader(this.getClass().getResource(file), resourceBundle, null, guiceControllerLoader);
    }

    private static <T> T load(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
