package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.bussiness.boundary.RoosterBoundary;
import javafx.scene.control.TableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ShiftTableCell extends TableCell<RoosterTableRow, Shift> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private RoosterBoundary roosterBoundary;

    public ShiftTableCell() {
        super();

        setOnDragDetected(event -> {
            logger.debug("drag detected, source: " + event.getSource());
            Dragboard db;
            if (event.isAltDown()) {
                db = startDragAndDrop(TransferMode.COPY);
            } else {
                db = startDragAndDrop(TransferMode.MOVE);
            }
            ClipboardContent content = new ClipboardContent();
            content.put(DragAndDropDataTypes.SHIFT, getItem());
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
            if (db.hasContent(DragAndDropDataTypes.SHIFT)) {
                Shift droppedShift = (Shift) db.getContent(DragAndDropDataTypes.SHIFT);
                logger.debug("Drag dropped with shift: " + droppedShift);
                if (getItem() == null) {
                    logger.debug("Creating shift");
                    Zamestnanec employee = ((RoosterTableRow) (((ShiftTableCell) event.getGestureTarget()).getTableRow().getItem())).getKey();
                    roosterBoundary.create(droppedShift, employee);
                } else {
                    logger.debug("Changing shift " + getItem());
                    Zamestnanec employee = null;
                    roosterBoundary.assign(getItem(), employee);
                }
                event.setDropCompleted(true);
            } else if (db.hasContent(DragAndDropDataTypes.SHIFT_TEMPLATE)) {
                logger.debug("Drag dropped with template: " + db.getContent(DragAndDropDataTypes.SHIFT_TEMPLATE));
                if (getItem() == null) {
                    logger.debug("Creating shift from template");
                } else {
                    logger.debug("Changing shift by template");
                }
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
            }
        });
    }

    private boolean isDroppable(DragEvent event) {
        boolean same = event.getGestureSource() == event.getGestureTarget();
        if (same) return false;
        boolean isShift = event.getDragboard().hasContent(DragAndDropDataTypes.SHIFT);
        if (isShift) return true;
        //noinspection UnnecessaryLocalVariable
        boolean isShiftTemplate = event.getDragboard().hasContent(DragAndDropDataTypes.SHIFT_TEMPLATE);
        return isShiftTemplate;
    }

    @Override
    protected void updateItem(Shift item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle("");
        } else {
            setText(item.getPredloha().getNazov());
        }
    }


}