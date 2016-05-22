package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;

import java.time.DayOfWeek;

public class SameShiftOnWeekend extends BaseRuleOffenderFinder {
    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        boolean testInSunday = shift.zaciatok().getDayOfWeek().equals(DayOfWeek.SUNDAY);
        for (Shift current: plan) {
            if (shift.equals(current)) {
                continue;
            }

        }

        return false;
    }
}
