package com.flowyk.apodys.ui.controllers;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.ui.DragAndDropDataTypes;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ResourceBundle;

public class ShiftTemplatesController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private Context context;

    @FXML
    private ButtonBar shiftTemplatesBar;

    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        context.getShiftTemplates().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                shiftTemplatesBar.getButtons().clear();
                context.getShiftTemplates().forEach(template -> shiftTemplatesBar.getButtons().add(createButton(template)));
            }
        });
    }

    private Button createButton(PredlohaSmeny template) {
        Button result = new Button(template.getNazov());
        result.setOnDragDetected(event -> {
            logger.debug("drag detected, source: " + event.getSource());
            Dragboard db = result.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();
            content.put(DragAndDropDataTypes.SHIFT_TEMPLATE, template);
            db.setContent(content);

            event.consume();
        });
        return result;
    }

}
