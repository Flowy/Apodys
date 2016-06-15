package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.PlanSmien;
import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;

public class SameShiftOnWeekend extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(SameShiftOnWeekend.class);

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        if (!isSaturday(shift) && !isSunday(shift)) {
            LOG.debug("Shift not in weekend, does not need a counterpart: {}", shift);
            return false;
        }

        for (Shift current: plan) {
            if (isCounterpart(shift, current)) {
                LOG.debug("Found counterpart for the shift {}; counterpart: {}", shift, current);
                return false;
            }
        }
        LOG.debug("No counterpart found for: {}", shift);
        return true;
    }

    private boolean isSaturday(Shift shift) {
        return shift.zaciatok().getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    private boolean isSunday(Shift shift) {
        return shift.zaciatok().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private boolean isCounterpart(Shift shift, Shift test) {
        if ((isSaturday(shift) && isSunday(test)) ||
                (isSaturday(test) && isSunday(shift))) {
            return shift.rovnakyVykonavatel(test) &&
                    sameStart(shift, test);
        } else {
            return false;
        }
    }

    private boolean sameStart(Shift shift, Shift test) {
        return shift.zaciatok().toLocalTime().equals(test.zaciatok().toLocalTime());
    }

}
