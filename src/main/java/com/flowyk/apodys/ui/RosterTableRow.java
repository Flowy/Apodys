package com.flowyk.apodys.ui;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RosterTableRow extends TableRow<Zamestnanec, LocalDate, Shift> {
    public RosterTableRow(Zamestnanec zamestnanec, Map<LocalDate, Shift> cells) {
        super(zamestnanec, cells);
    }
}
