package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.util.Set;

public class SameShiftOnWeekend extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(SameShiftOnWeekend.class);

    private LocalizationUnit crime;

    public SameShiftOnWeekend() {
        crime = new LocalizationUnit("crime.SameShiftOnWeekend");
    }

    @Override
    protected boolean isOffender(Shift shift, Set<Shift> shifts) {
        if (!isSaturday(shift) && !isSunday(shift)) {
            LOG.debug("Shift not in weekend, does not need a counterpart: {}", shift);
            return false;
        }

        for (Shift current: shifts) {
            if (isCounterpart(shift, current)) {
                LOG.debug("Found counterpart for the shift {}; counterpart: {}", shift, current);
                return false;
            }
        }
        LOG.debug("No counterpart found for: {}", shift);
        return true;
    }

    private boolean isSaturday(Shift shift) {
        return shift.getZaciatok().getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    private boolean isSunday(Shift shift) {
        return shift.getZaciatok().getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    private boolean isCounterpart(Shift shift, Shift test) {
        if ((isSaturday(shift) && isSunday(test)) ||
                (isSaturday(test) && isSunday(shift))) {
            return sameStartTime(shift, test);
        } else {
            return false;
        }
    }

    private boolean sameStartTime(Shift shift, Shift test) {
        return shift.getZaciatok().toLocalTime().equals(test.getZaciatok().toLocalTime());
    }

    @Override
    public LocalizationUnit getCrime() {
        return crime;
    }
}
