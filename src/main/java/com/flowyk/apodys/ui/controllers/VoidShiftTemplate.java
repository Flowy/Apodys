package com.flowyk.apodys.ui.controllers;

import com.flowyk.apodys.bussiness.entity.PredlohaSmeny;
import com.flowyk.apodys.bussiness.entity.Shift;

import java.time.LocalDate;

public class VoidShiftTemplate implements PredlohaSmeny {

    private String nazov;

    public VoidShiftTemplate(String nazov) {
        this.nazov = nazov;
    }

    @Override
    public String getNazov() {
        return nazov;
    }

    @Override
    public Shift vygenerujOd(LocalDate datum) {
        return null;
    }
}
