package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.planovanie.RuleOffender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class BaseRuleOffenderFinder implements RuleInvestigator {

    @Override
    public Collection<RuleOffender> findOffenders(List<EmployeeShifts> employeeShiftsList) {
        List<RuleOffender> offenders = new ArrayList<>();
        for (EmployeeShifts employeeShifts : employeeShiftsList) {
            for (Shift shift : employeeShifts.getShifts()) {
                if (isOffender(shift, employeeShifts.getShifts())) {
                    offenders.add(new RuleOffender(this, shift));
                }
            }
        }
        return offenders;
    }

    protected abstract boolean isOffender(Shift shift, Set<Shift> shifts);
}
