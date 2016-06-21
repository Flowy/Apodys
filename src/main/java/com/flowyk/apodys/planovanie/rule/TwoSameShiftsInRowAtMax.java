package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * shift can't be repeated more than once in a row for employee
 */
public class TwoSameShiftsInRowAtMax extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(TwoSameShiftsInRowAtMax.class);

    private LocalizationUnit crime;

    public TwoSameShiftsInRowAtMax() {
        crime = new LocalizationUnit("crime.TwoSameShiftsInRowAtMax");
    }

    @Override
    protected boolean isOffender(Shift shift, Set<Shift> shifts) {
        LocalDate firstDate = shift.getZaciatok().toLocalDate().minusDays(2L);
        LocalDate secondDate = shift.getZaciatok().toLocalDate().minusDays(1L);

        boolean firstDay = false;
        boolean secondDay = false;

        for (Shift test : shifts) {
            LocalDate testDate = test.getZaciatok().toLocalDate();
            if (testDate.isAfter(secondDate)) {
                LOG.debug("Breaking the streak, expecting shifts ordered by time");
                break;
            } else if (testDate.isEqual(firstDate) && sameShifts(shift, test)) {
                firstDay = true;
            } else if (testDate.isEqual(secondDate) && sameShifts(shift, test)) {
                secondDay = true;
            }
        }

        return firstDay && secondDay;
    }

    private boolean sameShifts(Shift shift, Shift test) {
        return Objects.equals(shift.getZaciatok().toLocalTime(), test.getZaciatok().toLocalTime());
    }

    @Override
    public LocalizationUnit getCrime() {
        return crime;
    }
}
