package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.LocalizationUnit;
import com.flowyk.apodys.bussiness.entity.Shift;

import java.util.Set;

public class OneShiftAtTime extends BaseRuleOffenderFinder {

    private LocalizationUnit crime;

    public OneShiftAtTime() {
        crime = new LocalizationUnit("crime.OneShiftAtTime");
    }

    @Override
    protected boolean isOffender(Shift shift, Set<Shift> shifts) {
        for (Shift test : shifts) {
            if (shift.equals(test)) {
                break;
            }
            if (prekryva(shift, test)) {
                return true;
            }
        }
        return false;
    }

    private static boolean prekryva(Shift origin, Shift test) {
        return startsIn(origin, test) ||
                endsIn(origin, test) ||
                isOver(origin, test);

    }

    private static boolean startsIn(Shift origin, Shift test) {
        return !origin.getZaciatok().isBefore(test.getZaciatok()) && origin.getZaciatok().isBefore(test.getKoniec());
    }

    /**
     * ends after start and before/at end
     */
    private static boolean endsIn(Shift origin, Shift test) {
        return origin.getKoniec().isAfter(test.getZaciatok()) && !origin.getKoniec().isAfter(test.getKoniec());
    }

    /**
     * starts before start and after end
     */
    private static boolean isOver(Shift origin, Shift test) {
        return origin.getZaciatok().isBefore(test.getZaciatok()) && origin.getKoniec().isAfter(test.getKoniec());
    }

    @Override
    public LocalizationUnit getCrime() {
        return crime;
    }
}
