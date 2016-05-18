package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;

import java.time.LocalDate;

/**
 * shift can't be repeated more than once in a row for employee
 */
public class RepeatingShift extends BaseRuleOffenderFinder {

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        LocalDate firstDate = shift.zaciatok().toLocalDate().minusDays(2L);
        LocalDate secondDate = shift.zaciatok().toLocalDate().minusDays(1L);

        boolean firstDay = false;
        boolean secondDay = false;

        for (Shift test : plan) {
            LocalDate testDate = test.zaciatok().toLocalDate();
            if (testDate.isAfter(secondDate)) {
                //break if we are after shift (expects that loop is ordered by time)
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
