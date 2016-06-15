package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.PredlohaSmienPreObdobie;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.Planovac;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatternPlanner implements Planovac {
    private List<PredlohaSmienPreObdobie> patterns;

    public PatternPlanner(List<PredlohaSmienPreObdobie> patterns) {
        this.patterns = Objects.requireNonNull(patterns);
    }

    @Override
    public List<Shift> naplanuj(List<Zamestnanec> employees, LocalDate zaciatok, LocalDate koniec, ZoneId timezone) {
        if (employees.isEmpty()) {
            throw new IllegalArgumentException("Zoznam zamestnancov musi obsahovat aspon jedneho zamestnanca");
        }
        if (employees.size() > patterns.size()) {
            throw new IllegalArgumentException("Pocet zamestnancov nesmie byt vyssi ako pocet vzorov");
        }

        List<Shift> shifts = new ArrayList<>();
        LocalDate startTime = zaciatok;
        int offset = 0;
        while (startTime.isBefore(koniec)) {
            if (offset == patterns.size()) {
                offset = 0;
            }
            for (int i = 0; i < employees.size(); i++) {
                List<Shift> employeeShifts = patterns.get((i + offset) % patterns.size()).vygenerujOd(startTime, timezone);
                assignEmployee(employeeShifts, employees.get(i));
                employeeShifts.forEach(shifts::add);
            }

            offset += 1;
            startTime = startTime.plus(patterns.get(0).dlzkaObdobia());
        }
        return shifts;
    }

    private void assignEmployee(List<Shift> shifts, Zamestnanec employee) {
        for (Shift shift: shifts) {
            shift.setZamestnanec(employee);
        }
    }

}
