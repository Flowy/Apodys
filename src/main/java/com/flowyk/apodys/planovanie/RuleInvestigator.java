package com.flowyk.apodys.planovanie;


import com.flowyk.apodys.bussiness.entity.Shift;

import java.util.Collection;
import java.util.List;

public interface RuleInvestigator {

    Collection<RuleOffender> findOffenders(List<Shift> shifts);
}
