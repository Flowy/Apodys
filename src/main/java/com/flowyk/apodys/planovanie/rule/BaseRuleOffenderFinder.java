package com.flowyk.apodys.planovanie.rule;

import com.flowyk.apodys.bussiness.entity.PlanSmien;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.planovanie.RuleOffender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseRuleOffenderFinder implements RuleInvestigator {

    @Override
    public Collection<RuleOffender> findOffenders(PlanSmien plan) {
        List<RuleOffender> offenders = new ArrayList<>();
        for (Shift shift: plan) {
            if (isOffender(shift, plan)) {
                offenders.add(new RuleOffender(shift));
            }
        }
        return offenders;
    }

    /**
     *
     * @param shift
     * @param plan
     * @return
     */
    protected abstract boolean isOffender(Shift shift, PlanSmien plan);
}
