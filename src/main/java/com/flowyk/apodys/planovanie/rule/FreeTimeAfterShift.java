package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;

public class FreeTimeAfterShift extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(FreeTimeAfterShift.class);

    private LocalTime shiftStarts;
    private Duration expectedFreeTime;

    public FreeTimeAfterShift(LocalTime shiftStarts, Duration expectedFreeTime) {
        this.shiftStarts = shiftStarts;
        this.expectedFreeTime = expectedFreeTime;
    }

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        for (Shift current: plan) {
            if (!current.rovnakyVykonavatel(shift) || !current.zaciatok().toLocalTime().equals(shiftStarts)) {
                continue;
            }
            if (shift.rovnakaZmena(current)) {
                break;
            }
            if (shift.zaciatok().isBefore(current.koniec().plus(expectedFreeTime))) {
                return true;
            }
        }
        return false;
    }
}
