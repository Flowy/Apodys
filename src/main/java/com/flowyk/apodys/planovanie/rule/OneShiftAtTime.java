package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;

import java.util.List;

public class OneShiftAtTime extends BaseRuleOffenderFinder {

    @Override
    protected boolean isOffender(Shift shift, List<Shift> shifts) {
        for (Shift test: shifts) {
            if (shift.equals(test)) {
                continue;
            }
            if (shift.rovnakyVykonavatel(test) && shift.prekryva(test)) {
                return true;
            }
        }
        return false;
    }
}
