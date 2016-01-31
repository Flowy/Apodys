package com.flowyk.apodys.ui;

import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.Zamestnanec;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.inject.Inject;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ShiftsController {
    private Logger logger = Logger.getLogger(getClass().getCanonicalName());

    @Inject
    private Context context;

    @FXML
    private ListView<PredlohaSmeny> shifts;
    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        shifts.setItems(context.getShiftTemplates());
        shifts.setCellFactory(list -> new ShiftCell());
//        stage.sizeToScene();
    }

    static class ShiftCell extends ListCell<PredlohaSmeny> {
        private Logger logger = Logger.getLogger(getClass().getCanonicalName());

        public ShiftCell() {
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
        protected void updateItem(PredlohaSmeny item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getNazov());
            }
        }
    }

    public void onDragDetected(Event event) {
    }

}
