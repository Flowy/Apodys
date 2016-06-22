package com.flowyk.apodys.planovanie;


import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.LocalizationUnit;

import java.util.Collection;
import java.util.List;

public interface RuleInvestigator {
    Collection<RuleOffender> findOffenders(EmployeeShifts shifts);
    Collection<RuleOffender> findOffenders(List<EmployeeShifts> shifts);

    LocalizationUnit getCrime();
}
