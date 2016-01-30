package com.flowyk.apodys.ui;

import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.ui.guice.GuiceControllerLoader;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EmployeeListController {
    Logger logger = Logger.getLogger("list");

    @Inject
    private Context context;
    @Inject
    private Stage stage;

    @FXML
    private ListView<Zamestnanec> zamestnanci;

    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        zamestnanci.setItems(context.getEmployees());
        zamestnanci.setCellFactory(list -> new ZamestnanecCell());
//        stage.sizeToScene();
    }

    static class ZamestnanecCell extends ListCell<Zamestnanec> {
        @Override
        protected void updateItem(Zamestnanec item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());
            }
        }
    }

    public void onDragDetected(Event event) {
        logger.info("drag detected, source: " + event.getSource());
        ListView source = (ListView) event.getSource();
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
//        ObservableList<String> items = getListView().getItems();
        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.put(new DataFormat("textDataFormat"), "text");
        db.setContent(content);

        event.consume();
    }
}
