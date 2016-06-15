package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class EmployeesController {
    @Inject
    private RosterBoundary rosterBoundary;

    @FXML
    private ListView<Zamestnanec> employees;
    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        employees.setItems(rosterBoundary.getEmployees());
        employees.setCellFactory(list -> new ZamestnanecCell());
//        stage.sizeToScene();
    }

    static class ZamestnanecCell extends ListCell<Zamestnanec> {

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
