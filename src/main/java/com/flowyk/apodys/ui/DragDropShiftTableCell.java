package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.controller.Context;
import com.flowyk.apodys.bussiness.controller.Messages;
import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.sun.javafx.binding.ListExpressionHelper;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class DragDropShiftTableCell extends ShiftTableCell {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    public DragDropShiftTableCell(Context context, Messages messages) {
        super(messages);

        setOnDragDetected(event -> {
            logger.debug("drag detected, source: " + event.getSource());
            if (isEmpty() || getItem() == null) {
                event.consume();
                return;
            }

            Dragboard db;
            db = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(DragAndDropDataTypes.SHIFT_TEMPLATE, getItem().getPredloha());
            db.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (isDroppable(event)) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            } else {
                event.acceptTransferModes(TransferMode.NONE);
            }
            event.consume();
        });

        setOnDragEntered(event -> {
            if (isDroppable(event)) {
                this.getStyleClass().add("dropReady");
            }
        });

        setOnDragExited(event -> {
            this.getStyleClass().remove("dropReady");
        });

        setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasContent(DragAndDropDataTypes.SHIFT_TEMPLATE)) {
                PredlohaSmeny template = (PredlohaSmeny) db.getContent(DragAndDropDataTypes.SHIFT_TEMPLATE);

                logger.debug("Drag dropped with template: " + template);
                EmployeeShifts row = (EmployeeShifts) getTableRow().getItem();

                Shift originShift = row.getShift(columnHeader);
                if (originShift != null) {
                    row.getShifts().remove(originShift);
                }
                Shift newShift = template.vygenerujOd(columnHeader);
                row.getShifts().add(newShift);

                event.setDropCompleted(true);
            } else {
                logger.debug("Drag dropped with unsupported content types: " + db.getContentTypes());
                event.setDropCompleted(false);
            }
            event.consume();
        });

        setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                logger.debug("Removing shift " + getItem());
                EmployeeShifts row = (EmployeeShifts) getTableRow().getItem();
                row.getShifts().remove(getItem());
            }
        });
    }

    private boolean isDroppable(DragEvent event) {
        //TODO: not droppable if row have no employee
        boolean same = event.getGestureSource() == event.getGestureTarget();
        if (same) return false;
        boolean isShift = event.getDragboard().hasContent(DragAndDropDataTypes.SHIFT);
        if (isShift) return true;
        //noinspection UnnecessaryLocalVariable
        boolean isShiftTemplate = event.getDragboard().hasContent(DragAndDropDataTypes.SHIFT_TEMPLATE);
        return isShiftTemplate;
    }


}