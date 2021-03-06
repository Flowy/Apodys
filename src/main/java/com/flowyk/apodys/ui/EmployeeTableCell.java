package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import javafx.scene.control.TableCell;

public class EmployeeTableCell extends TableCell<EmployeeShifts, Zamestnanec> {

    @Override
    protected void updateItem(Zamestnanec item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setText(null);
            setStyle("");
        } else {
            setText(item.getName());
        }
    }
}