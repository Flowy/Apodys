package com.flowyk.apodys.ui.config.event;

import com.flowyk.apodys.ui.RosterTableRow;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class RosterDataChange {

    private ObservableList<RosterTableRow> data;
    private LocalDate startDate;
    private LocalDate endDate;

    public RosterDataChange(ObservableList<RosterTableRow> data, LocalDate startDate, LocalDate endDate) {
        this.data = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ObservableList<RosterTableRow> getData() {
        return data;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

