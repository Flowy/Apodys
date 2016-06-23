package com.flowyk.apodys.bussiness.controller;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.RuleInvestigator;
import com.flowyk.apodys.planovanie.RuleOffender;
import com.flowyk.apodys.planovanie.rule.FreeTimeAfterShift;
import com.flowyk.apodys.planovanie.rule.MaxTimeInPeriod;
import com.flowyk.apodys.planovanie.rule.SameShiftOnWeekend;
import com.flowyk.apodys.planovanie.rule.TwoSameShiftsInRowAtMax;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Singleton
public class RuleInvestigatorManager {

    private List<RuleInvestigator> investigators;

    private ListBinding<RuleOffender> errors;

    @Inject
    private Context context;

    public RuleInvestigatorManager() {
        this.investigators = Arrays.asList(
                new MaxTimeInPeriod(Duration.ofHours(33L), Period.ofDays(4)),
                new TwoSameShiftsInRowAtMax(),
                new FreeTimeAfterShift(LocalTime.of(18, 0), Duration.ofHours(12L)),
                new FreeTimeAfterShift(2, LocalTime.of(18, 0), Duration.ofHours(48L)),
                new SameShiftOnWeekend(),
                new MaxTimeInPeriod(Duration.ofHours(55L), Period.ofWeeks(1))
        );

        errors = new ListBinding<RuleOffender>() {
            @Override
            protected ObservableList<RuleOffender> computeValue() {
                return FXCollections.observableList(findOffenders(context.getEmployeeShifts()));
            }
        };
    }

    public ObservableList<RuleOffender> getErrors() {
        return errors;
    }

    public void recalculate() {
        errors.invalidate();
    }

    private List<RuleOffender> findOffenders(List<EmployeeShifts> employeeShifts) {
        List<RuleOffender> offenders = new ArrayList<>();
        for (RuleInvestigator investigator : investigators) {
            offenders.addAll(investigator.findOffenders(employeeShifts));
        }
        return offenders;
    }

//    private Collection<RuleOffender> findOffenders(EmployeeShifts employeeShifts) {
//        List<RuleOffender> offenders = new ArrayList<>();
//        for (RuleInvestigator investigator : investigators) {
//            offenders.addAll(investigator.findOffenders(employeeShifts));
//        }
//        return offenders;
//    }
}
