package com.flowyk.apodys.ui;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.logging.Logger;

public class EmployeeListController {
    Logger logger = Logger.getLogger("list");

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
