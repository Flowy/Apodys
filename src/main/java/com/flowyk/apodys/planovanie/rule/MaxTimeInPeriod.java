package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;

public class MaxTimeInPeriod extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(MaxTimeInPeriod.class);

    private Duration maxTime;
    private Period period;

    public MaxTimeInPeriod(Duration maxTime, Period period) {
        this.maxTime = maxTime;
        this.period = period;
    }

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        ZonedDateTime periodMin = shift.zaciatok().minus(period);
        Duration workedTimeBeforeShift = Duration.ZERO;
        for (Shift current: plan) {
            if (!current.rovnakyVykonavatel(shift) || current.zaciatok().isBefore(periodMin)) {
                continue;
            }
            if (current.zaciatok().isAfter(shift.zaciatok())) {
                LOG.debug("Breaking the streak, expecting shifts ordered by time");
                break;
            }
            workedTimeBeforeShift = workedTimeBeforeShift.plus(current.countedDuration());
        }
        if (workedTimeBeforeShift.compareTo(maxTime) >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
