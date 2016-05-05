package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.PredlohaSmienPreObdobie;
import com.flowyk.apodys.Zamestnanec;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ZakladnyPlanovac implements Planovac {
    private List<Zamestnanec> zamestnanecList;
    private PredlohaSmienPreObdobie predlohaSmienPreObdobie;

    public ZakladnyPlanovac(List<Zamestnanec> zamestnanecList, PredlohaSmienPreObdobie predlohaSmienPreObdobie) {
        Objects.requireNonNull(zamestnanecList);
        if (zamestnanecList.size() == 0) {
            throw new IllegalArgumentException("Zoznam zamestnancov musi obsahovat aspon jedneho zamesnanca");
        }
        this.zamestnanecList = zamestnanecList;
        this.predlohaSmienPreObdobie = predlohaSmienPreObdobie;
    }

    @Override
    public PlanSmien naplanuj(LocalDate zaciatok, LocalDate koniec, ZoneId timezone) {
        PlanSmien planSmien = new PlanSmien(new ArrayList<>(), zamestnanecList);
        LocalDate startTime = zaciatok;
        while (startTime.isBefore(koniec)) {
            spracujSmeny(
                    planSmien,
                    predlohaSmienPreObdobie.vygenerujOd(startTime, timezone)
            );
            startTime = startTime.plus(predlohaSmienPreObdobie.dlzkaObdobia());
        }
        return planSmien;
    }

    void spracujSmeny(PlanSmien planSmien, List<Shift> smeny) {
        Iterator<Zamestnanec> zamestnanci = null;
        for (Shift smena: smeny) {
            if (zamestnanci == null || !zamestnanci.hasNext()) {
                zamestnanci = zamestnanecList.iterator();
            }
            smena.setZamestnanec(zamestnanci.next());
            planSmien.pridatPolozku(smena);
        }
    }
}
