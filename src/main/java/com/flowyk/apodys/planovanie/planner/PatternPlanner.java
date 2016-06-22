package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.PredlohaSmienPreObdobie;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.planovanie.Planovac;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PatternPlanner implements Planovac {
    private List<PredlohaSmienPreObdobie> patterns;

    public PatternPlanner(List<PredlohaSmienPreObdobie> patterns) {
        this.patterns = Objects.requireNonNull(patterns);
    }

    @Override
    public void naplanuj(List<EmployeeShifts> employeeShifts, LocalDate zaciatok, LocalDate koniec) {
        if (employeeShifts.isEmpty()) {
            throw new IllegalArgumentException("Zoznam zamestnancov musi obsahovat aspon jedneho zamestnanca");
        }
        if (employeeShifts.size() > patterns.size()) {
            throw new IllegalArgumentException("Pocet zamestnancov nesmie byt vyssi ako pocet vzorov");
        }
        LocalDate startTime = zaciatok;
        int offset = 0;
        while (startTime.isBefore(koniec)) {
            if (offset == patterns.size()) {
                offset = 0;
            }
            for (int i = 0; i < employeeShifts.size(); i++) {
                List<Shift> shifts = patterns.get((i + offset) % patterns.size()).vygenerujOd(startTime);
                employeeShifts.get(i).getShifts().addAll(shifts);
            }

            offset += 1;
            startTime = startTime.plus(patterns.get(0).dlzkaObdobia());
        }
    }

}
