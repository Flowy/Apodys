package com.flowyk.apodys.ui.config.event;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.google.common.collect.Table;

import java.time.LocalDate;

public class RoosterDataChange {

    private Table<Zamestnanec, LocalDate, Shift> data;
    private LocalDate startDate;
    private LocalDate endDate;

    public RoosterDataChange(Table<Zamestnanec, LocalDate, Shift> data, LocalDate startDate, LocalDate endDate) {
        this.data = data;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Table<Zamestnanec, LocalDate, Shift> getData() {
        return data;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

