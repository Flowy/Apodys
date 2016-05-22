package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;

import java.time.Duration;
import java.time.Period;

public class MaxTimeInPeriod extends BaseRuleOffenderFinder {

    private Duration maxTime;
    private Period period;

    public MaxTimeInPeriod(Duration maxTime, Period period) {
        this.maxTime = maxTime;
        this.period = period;
    }

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {

        return false;
    }
}
