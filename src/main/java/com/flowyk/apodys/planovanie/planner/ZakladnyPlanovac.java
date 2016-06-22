package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.PredlohaSmienPreObdobie;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.planovanie.Planovac;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZakladnyPlanovac implements Planovac {
    private PredlohaSmienPreObdobie predlohaSmienPreObdobie;

    public ZakladnyPlanovac(PredlohaSmienPreObdobie predlohaSmienPreObdobie) {
        this.predlohaSmienPreObdobie = predlohaSmienPreObdobie;
    }

    @Override
    public void naplanuj(List<EmployeeShifts> employeeShifts, LocalDate zaciatok, LocalDate koniec) {
        Objects.requireNonNull(employeeShifts);
        if (employeeShifts.size() == 0) {
            throw new IllegalArgumentException("Zoznam zamestnancov musi obsahovat aspon jedneho zamesnanca");
        }
        List<Shift> shifts = new ArrayList<>();
        LocalDate startTime = zaciatok;
        int i = 0;
        while (startTime.isBefore(koniec)) {
            List<Shift> generatedShifts = predlohaSmienPreObdobie.vygenerujOd(startTime);
            employeeShifts.get(i).getShifts().addAll(generatedShifts);
            generatedShifts.forEach(shifts::add);
            startTime = startTime.plus(predlohaSmienPreObdobie.dlzkaObdobia());
            i += 1;
            if (i == employeeShifts.size()) {
                i = 0;
            }
        }
    }
}
