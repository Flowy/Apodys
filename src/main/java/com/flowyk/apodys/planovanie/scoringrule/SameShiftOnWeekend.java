package com.flowyk.apodys.planovanie.scoringrule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.planovanie.RuleOffender;

import java.util.ArrayList;
import java.util.Collection;

public class SameShiftOnWeekend implements RuleInvestigator {

    @Override
    public Collection<RuleOffender> findOffenders(PlanSmien plan) {
        //TODO
        return new ArrayList<>();
    }
}
