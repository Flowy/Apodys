package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.PlanSmien;
import com.flowyk.apodys.bussiness.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * shift can't be repeated more than once in a row for employee
 */
public class TwoSameShiftsInRowAtMax extends BaseRuleOffenderFinder {
    private static final Logger LOG = LoggerFactory.getLogger(TwoSameShiftsInRowAtMax.class);

    @Override
    protected boolean isOffender(Shift shift, PlanSmien plan) {
        LocalDate firstDate = shift.zaciatok().toLocalDate().minusDays(2L);
        LocalDate secondDate = shift.zaciatok().toLocalDate().minusDays(1L);

        boolean firstDay = false;
        boolean secondDay = false;

        for (Shift test : plan) {
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
