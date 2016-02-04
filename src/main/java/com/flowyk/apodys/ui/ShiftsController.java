package com.flowyk.apodys.ui;

import com.flowyk.apodys.PredlohaSmeny;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

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
        shifts.setCellFactory(list -> new ShiftTemplateCell());
//        stage.sizeToScene();
    }

    static class ShiftTemplateCell extends ListCell<PredlohaSmeny> {
        private Logger logger = Logger.getLogger(getClass().getCanonicalName());

        public ShiftTemplateCell() {
            this.setOnDragDetected(event -> {
                logger.fine("drag detected, source: " + event.getSource());
                Dragboard db = startDragAndDrop(TransferMode.COPY);

                ClipboardContent content = new ClipboardContent();
                content.put(DragAndDropDataTypes.SHIFT_TEMPLATE, getItem());
                db.setContent(content);

                event.consume();
            });
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

}
