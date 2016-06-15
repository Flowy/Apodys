package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class ShiftsController {

    @Inject
    private RosterBoundary rosterBoundary;

    @FXML
    private ListView<PredlohaSmeny> shifts;
    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        shifts.setItems(rosterBoundary.getShiftTemplates());
        shifts.setCellFactory(list -> new ShiftTemplateCell());
    }

    static class ShiftTemplateCell extends ListCell<PredlohaSmeny> {
        private Logger logger = LoggerFactory.getLogger(getClass());

        public ShiftTemplateCell() {
            this.setOnDragDetected(event -> {
                logger.debug("drag detected, source: " + event.getSource());
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
