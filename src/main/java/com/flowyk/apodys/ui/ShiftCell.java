package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.PlanSmien;
import com.flowyk.apodys.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.time.LocalDate;
import java.time.ZoneId;

public class ShiftCell extends Label {

    private Shift shift;
    private LocalDate startDay;
    private Zamestnanec zamestnanec;
    private PlanSmien planSmien;

    public ShiftCell(Shift shift, PlanSmien planSmien) {
        this(shift.zaciatok().toLocalDate(), shift.vykonavatel(), planSmien);
        setShift(shift);
    }

    public ShiftCell(LocalDate startDay, Zamestnanec zamestnanec, PlanSmien planSmien) {
        super();
//        if (shift == null) {
//            setText("           ");
//        }
        this.startDay = startDay;
        this.zamestnanec = zamestnanec;
        this.planSmien = planSmien;
        this.setPrefWidth(60d);

        setOnDragOver(event -> {
            if (dropable(event)) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.acceptTransferModes(TransferMode.NONE);
            }
            event.consume();
        });

        setOnDragDropped(dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            if (db.hasContent(DragAndDropDataTypes.SHIFT_TEMPLATE)) {
                PredlohaSmeny predlohaSmeny = (PredlohaSmeny) db.getContent(DragAndDropDataTypes.SHIFT_TEMPLATE);
                Shift newShift = predlohaSmeny.vygenerujOd(this.startDay, ZoneId.systemDefault());
                newShift.setZamestnanec(this.zamestnanec);
                if (this.shift != null) {
                    planSmien.odstranitPolozku(this.shift);
                }
                planSmien.pridatPolozku(newShift);
                setShift(newShift);
                dragEvent.setDropCompleted(true);
            } else if (db.hasContent(DragAndDropDataTypes.EMPLOYEE)) {

                dragEvent.setDropCompleted(true);
            } else {
                dragEvent.setDropCompleted(false);
            }
            dragEvent.consume();
        });

        setOnDragEntered(dragEvent -> {
            if (dropable(dragEvent)) {
                this.getStyleClass().add("dropReady");
            }
        });

        setOnDragExited(dragEvent -> {
            this.getStyleClass().remove("dropReady");
        });
    }

    private boolean dropable(DragEvent event) {
        boolean notSame = event.getGestureSource() != event.getGestureTarget();
        boolean shiftTemplate = event.getDragboard().hasContent(DragAndDropDataTypes.SHIFT_TEMPLATE);
        boolean employee = event.getDragboard().hasContent(DragAndDropDataTypes.EMPLOYEE) && this.shift != null;
        return notSame && (shiftTemplate || employee);
    }

    private void setShift(Shift shift) {
        this.shift = shift;
        setText(shift.predloha().getNazov());
    }
}
