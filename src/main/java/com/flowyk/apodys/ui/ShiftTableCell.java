package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import javafx.scene.control.TableCell;

import javax.inject.Inject;
import java.time.LocalDate;

public class ShiftTableCell extends TableCell<RosterTableRow, Shift> {
    protected LocalDate columnHeader;

    @Inject
    public ShiftTableCell() {
        super();
    }

    public void setColumnHeader(LocalDate columnHeader) {
        this.columnHeader = columnHeader;
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
