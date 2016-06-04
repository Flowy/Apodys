package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.Shift;

public class RuleOffender {

    private Shift shift;

    public RuleOffender(Shift shift) {
        this.shift = shift;
    }

    public Shift getOffender() {
        return shift;
    }

    //TODO: get crime


    @Override
    public String toString() {
        return "RuleOffender{" +
                "shift=" + shift +
                '}';
    }
}
