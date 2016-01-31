package com.flowyk.apodys.ui;

import com.flowyk.apodys.Zamestnanec;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.*;

import javax.inject.Inject;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EmployeeListController {
    private Logger logger = Logger.getLogger(getClass().getCanonicalName());

    @Inject
    private Context context;

    @FXML
    private TitledPane titledPane;
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
        private Logger logger = Logger.getLogger(getClass().getCanonicalName());

        public ZamestnanecCell() {
//            this.setOnDragDetected(event -> {
//                logger.info("drag detected, source: " + event.getSource());
//                Dragboard db = ((ListCell) event.getSource()).startDragAndDrop(TransferMode.ANY);
//
//                ClipboardContent content = new ClipboardContent();
//                content.put(new DataFormat("textDataFormat"), "text");
//                db.setContent(content);
//
//                event.consume();
//            });
        }

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
    }

}
