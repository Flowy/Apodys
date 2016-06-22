package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.rule.FreeTimeAfterShift;
import com.flowyk.apodys.planovanie.rule.MaxTimeInPeriod;
import com.flowyk.apodys.planovanie.rule.SameShiftOnWeekend;
import com.flowyk.apodys.planovanie.rule.TwoSameShiftsInRowAtMax;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RuleInvestigatorManager {

    private List<RuleInvestigator> investigators;

    public RuleInvestigatorManager() {
        this.investigators = Arrays.asList(
                new MaxTimeInPeriod(Duration.ofHours(33L), Period.ofDays(4)),
                new TwoSameShiftsInRowAtMax(),
                new FreeTimeAfterShift(LocalTime.of(18, 0), Duration.ofHours(12L)),
                new FreeTimeAfterShift(2, LocalTime.of(18, 0), Duration.ofHours(48L)),
                new SameShiftOnWeekend(),
                new MaxTimeInPeriod(Duration.ofHours(55L), Period.ofWeeks(1))
        );
    }

    public Collection<RuleOffender> findOffenders(List<EmployeeShifts> employeeShifts) {
        List<RuleOffender> offenders = new ArrayList<>();
        for (RuleInvestigator investigator : investigators) {
            offenders.addAll(investigator.findOffenders(employeeShifts));
        }
        return offenders;
    }
}
