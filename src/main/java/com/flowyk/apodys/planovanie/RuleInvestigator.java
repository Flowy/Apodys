package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.bussiness.entity.PlanSmien;

import java.util.Collection;

public interface RuleInvestigator {

    Collection<RuleOffender> findOffenders(PlanSmien plan);
}
