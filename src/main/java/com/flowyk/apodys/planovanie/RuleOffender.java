package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.Shift;

public class RuleOffender {

    private RuleInvestigator investigator;
    private Shift shift;

    public RuleOffender(RuleInvestigator investigator, Shift shift) {
        this.investigator = investigator;
        this.shift = shift;
    }

    public Shift getOffender() {
        return shift;
    }

    public LocalizationUnit getCrime() {
        return investigator.getCrime();
    }


    @Override
    public String toString() {
        return "RuleOffender{" +
                "shift=" + shift +
                '}';
    }
}
