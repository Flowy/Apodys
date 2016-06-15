package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RoosterTableRow extends TableRow<Zamestnanec, LocalDate, Shift> {
    public RoosterTableRow() {
        super(null, new HashMap<>());
    }
    public RoosterTableRow(Zamestnanec zamestnanec, Map<LocalDate, Shift> cells) {
        super(zamestnanec, cells);
    }
}
