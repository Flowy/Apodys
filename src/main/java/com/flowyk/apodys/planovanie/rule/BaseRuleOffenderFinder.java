package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.planovanie.RuleOffender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRuleOffenderFinder implements RuleInvestigator {

    @Override
    public Collection<RuleOffender> findOffenders(List<Shift> shifts) {
        List<RuleOffender> offenders = new ArrayList<>();
        for (Shift shift: shifts) {
            if (isOffender(shift, shifts)) {
                offenders.add(new RuleOffender(shift));
            }
        }
        return offenders;
    }

    protected abstract boolean isOffender(Shift shift, List<Shift> shifts);
}
