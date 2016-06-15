package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;

public class MaxTimeInPeriod extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(MaxTimeInPeriod.class);

    private Duration maxTime;
    private Period period;

    public MaxTimeInPeriod(Duration maxTime, Period period) {
        this.maxTime = maxTime;
        this.period = period;
    }

    @Override
    protected boolean isOffender(Shift shift, List<Shift> shifts) {
        ZonedDateTime periodMin = shift.getZaciatok().minus(period);
        Duration workedTimeBeforeShift = Duration.ZERO;
        for (Shift current: shifts) {
            if (!current.rovnakyVykonavatel(shift) || current.getZaciatok().isBefore(periodMin)) {
                continue;
            }
            if (current.getZaciatok().isAfter(shift.getZaciatok())) {
                LOG.debug("Breaking the streak, expecting shifts ordered by time");
                break;
            }
            workedTimeBeforeShift = workedTimeBeforeShift.plus(current.getCountedDuration());
        }
        if (workedTimeBeforeShift.compareTo(maxTime) >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
