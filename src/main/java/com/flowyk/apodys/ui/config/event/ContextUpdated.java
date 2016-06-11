package com.flowyk.apodys.ui.config.event;

import com.flowyk.apodys.ui.export.ApodysData;

public class ContextUpdated {

    private ApodysData apodysData;

    public ContextUpdated() {
    }

    public ContextUpdated(ApodysData apodysData) {
        this.apodysData = apodysData;
    }

    public ApodysData getApodysData() {
        return apodysData;
    }
}
