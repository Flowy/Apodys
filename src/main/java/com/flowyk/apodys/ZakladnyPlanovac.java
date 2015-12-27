package com.flowyk.apodys;

import java.time.ZonedDateTime;
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
    public PlanSmien naplanuj(ZonedDateTime zaciatok, ZonedDateTime koniec) {
        PlanSmien planSmien = new PlanSmien();
        ZonedDateTime startTime = zaciatok;
        while (startTime.isBefore(koniec)) {
            spracujSmeny(
                    planSmien,
                    predlohaSmienPreObdobie.vygenerujOd(startTime.toLocalDate(), startTime.getZone())
            );
            startTime = startTime.plus(predlohaSmienPreObdobie.dlzkaObdobia());
        }
        return planSmien;
    }

    void spracujSmeny(PlanSmien planSmien, List<Smena> smeny) {
        Iterator<Zamestnanec> zamestnanci = null;
        for (Smena smena: smeny) {
            if (zamestnanci == null || !zamestnanci.hasNext()) {
                zamestnanci = zamestnanecList.iterator();
            }
            smena.setZamestnanec(zamestnanci.next());
            planSmien.pridatPolozku(smena);
        }
    }
}
