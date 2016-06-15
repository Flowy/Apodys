package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.bussiness.boundary.RosterBoundary;
import javafx.scene.control.TableCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DragDropShiftTableCell extends TableCell<RosterTableRow, Shift> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    private RosterBoundary rosterBoundary;

    public DragDropShiftTableCell() {
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
                Zamestnanec employee = ((RosterTableRow) (((DragDropShiftTableCell) event.getGestureTarget()).getTableRow().getItem())).getKey();
                LocalDate startDate = LocalDate.parse(
                        ((DragDropShiftTableCell) event.getGestureTarget()).getTableColumn().getText(),
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                );
                if (getItem() == null) {
                    rosterBoundary.create(template, startDate, employee);
                } else {
                    rosterBoundary.override(getItem(), template);
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
                logger.debug("Removing shift " + event.getGestureSource());
                rosterBoundary.remove(getItem());
            }
        });
    }

    private boolean isDroppable(DragEvent event) {
        boolean same = event.getGestureSource() == event.getGestureTarget();
        if (same) return false;
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