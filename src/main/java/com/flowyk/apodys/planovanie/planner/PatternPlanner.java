package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PredlohaSmienPreObdobie;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.planovanie.Planovac;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

public class PatternPlanner implements Planovac {
    private List<Zamestnanec> zamestnanecList;
    private List<PredlohaSmienPreObdobie> patterns;

    public PatternPlanner(List<Zamestnanec> zamestnanecList, List<PredlohaSmienPreObdobie> patterns) {
        this.zamestnanecList = Objects.requireNonNull(zamestnanecList);
        this.patterns = Objects.requireNonNull(patterns);
        if (zamestnanecList.isEmpty()) {
            throw new IllegalArgumentException("Zoznam zamestnancov musi obsahovat aspon jedneho zamestnanca");
        }
        if (zamestnanecList.size() > patterns.size()) {
            throw new IllegalArgumentException("Pocet zamestnancov nesmie byt vyssi ako pocet vzorov");
        }
    }

    @Override
    public PlanSmien naplanuj(LocalDate zaciatok, LocalDate koniec, ZoneId timezone) {
        PlanSmien planSmien = new PlanSmien(zamestnanecList);
        LocalDate startTime = zaciatok;
        int offset = 0;
        while (startTime.isBefore(koniec)) {
            if (offset == patterns.size()) {
                offset = 0;
            }
            for (int i = 0; i < zamestnanecList.size(); i++) {
                List<Shift> shifts = patterns.get((i + offset) % patterns.size()).vygenerujOd(startTime, timezone);
                assignEmployee(shifts, zamestnanecList.get(i));
                shifts.forEach(planSmien::pridatPolozku);
            }

            offset += 1;
            startTime = startTime.plus(patterns.get(0).dlzkaObdobia());
        }
        return planSmien;
    }

    private void assignEmployee(List<Shift> shifts, Zamestnanec employee) {
        for (Shift shift: shifts) {
            shift.setZamestnanec(employee);
        }
    }

}
