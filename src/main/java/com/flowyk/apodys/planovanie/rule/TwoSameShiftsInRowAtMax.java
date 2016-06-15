package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

/**
 * shift can't be repeated more than once in a row for employee
 */
public class TwoSameShiftsInRowAtMax extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(TwoSameShiftsInRowAtMax.class);

    @Override
    protected boolean isOffender(Shift shift, List<Shift> shifts) {
        LocalDate firstDate = shift.zaciatok().toLocalDate().minusDays(2L);
        LocalDate secondDate = shift.zaciatok().toLocalDate().minusDays(1L);

        boolean firstDay = false;
        boolean secondDay = false;

        for (Shift test : shifts) {
            LocalDate testDate = test.zaciatok().toLocalDate();
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
        return shift.rovnakyVykonavatel(test) && shift.rovnakaZmena(test);
    }
}
