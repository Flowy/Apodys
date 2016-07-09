package com.flowyk.apodys.bussiness.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;

public interface PredlohaSmeny extends Serializable {
    String getNazov();

    Shift vygenerujOd(LocalDate datum);
}
