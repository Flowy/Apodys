package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;

public class FreeTimeAfterShift extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(FreeTimeAfterShift.class);

    private int freeAfterRepeating;
    private LocalTime shiftStarts;
    private Duration expectedFreeTime;
    private LocalizationUnit crime;

    public FreeTimeAfterShift(LocalTime shiftStarts, Duration expectedFreeTime) {
        this(1, shiftStarts, expectedFreeTime);
    }

    public FreeTimeAfterShift(int freeAfterRepeating, LocalTime shiftStarts, Duration expectedFreeTime) {
        this.freeAfterRepeating = freeAfterRepeating;
        this.shiftStarts = shiftStarts;
        this.expectedFreeTime = expectedFreeTime;
        this.crime = new LocalizationUnit("crime.FreeTimeAfterShift", shiftStarts.getHour(), expectedFreeTime.toHours(), freeAfterRepeating);
    }

    @Override
    protected boolean isOffender(Shift shift, Set<Shift> shifts) {
        ZonedDateTime lastMatchedTime = null;
        int matched = 0;
        for (Shift current : shifts) {
            if (!current.getZaciatok().toLocalTime().equals(shiftStarts)) {
                continue;
            }
            if (shift.equals(current)) {
                break;
            }
            if (matched > 0 && lastMatchedTime.plusDays(1L).isEqual(current.getZaciatok())) {
                lastMatchedTime = current.getZaciatok();
                matched += 1;
            } else {
                lastMatchedTime = current.getZaciatok();
                matched = 1;
            }
            if (matched == freeAfterRepeating && shift.getZaciatok().isBefore(current.getKoniec().plus(expectedFreeTime))) {
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
