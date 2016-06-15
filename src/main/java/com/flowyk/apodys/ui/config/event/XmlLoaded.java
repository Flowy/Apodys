package com.flowyk.apodys.ui.config.event;

import com.flowyk.apodys.bussiness.entity.ApodysData;

public class XmlLoaded {

    private ApodysData apodysData;

    public XmlLoaded(ApodysData apodysData) {
        this.apodysData = apodysData;
    }

    public ApodysData getApodysData() {
        return apodysData;
    }
}
