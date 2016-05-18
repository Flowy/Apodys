package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.planovanie.rule.BaseRuleOffenderFinder;

public class OneShiftAtTime extends BaseRuleOffenderFinder {

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        for (Shift test: plan) {
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
