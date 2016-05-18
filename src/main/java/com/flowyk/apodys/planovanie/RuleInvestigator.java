package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;

import java.util.Collection;

public interface RuleInvestigator {

    Collection<RuleOffender> findOffenders(PlanSmien plan);
}
