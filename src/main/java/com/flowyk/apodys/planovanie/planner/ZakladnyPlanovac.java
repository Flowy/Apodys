package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.PredlohaSmienPreObdobie;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.Planovac;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ZakladnyPlanovac implements Planovac {
    private PredlohaSmienPreObdobie predlohaSmienPreObdobie;

    public ZakladnyPlanovac(PredlohaSmienPreObdobie predlohaSmienPreObdobie) {
        this.predlohaSmienPreObdobie = predlohaSmienPreObdobie;
    }

    @Override
    public List<Shift> naplanuj(List<Zamestnanec> employees, LocalDate zaciatok, LocalDate koniec, ZoneId timezone) {
        Objects.requireNonNull(employees);
        if (employees.size() == 0) {
            throw new IllegalArgumentException("Zoznam zamestnancov musi obsahovat aspon jedneho zamesnanca");
        }
        List<Shift> shifts = new ArrayList<>();
        LocalDate startTime = zaciatok;
        while (startTime.isBefore(koniec)) {
            List<Shift> generatedShifts = predlohaSmienPreObdobie.vygenerujOd(startTime, timezone);
            assignEmployees(generatedShifts, employees);
            generatedShifts.forEach(shifts::add);
            startTime = startTime.plus(predlohaSmienPreObdobie.dlzkaObdobia());
        }
        return shifts;
    }

    private void assignEmployees(List<Shift> shifts, List<Zamestnanec> employees) {
        Iterator<Zamestnanec> zamestnanci = null;
        for (Shift smena: shifts) {
            if (zamestnanci == null || !zamestnanci.hasNext()) {
                zamestnanci = employees.iterator();
            }
            smena.setEmployee(zamestnanci.next());
        }
    }
}
