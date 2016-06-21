package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.Shift;

import java.util.List;

public class OneShiftAtTime extends BaseRuleOffenderFinder {

    private LocalizationUnit crime;

    public OneShiftAtTime() {
        crime = new LocalizationUnit("crime.OneShiftAtTime");
    }

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

    @Override
    public LocalizationUnit getCrime() {
        return crime;
    }
}
