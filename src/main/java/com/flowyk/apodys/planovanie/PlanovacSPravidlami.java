package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.PredlohaSmienPreObdobie;
import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.planovanie.pravidlo.PravidloPlanovaniaSmien;
import com.flowyk.apodys.planovanie.pravidlo.VysledokKontrolyPravidla;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlanovacSPravidlami implements Planovac {

    final Logger logger = LoggerFactory.getLogger(PlanovacSPravidlami.class);

    private List<Zamestnanec> zamestnanci;
    private PredlohaSmienPreObdobie predlohaSmienPreObdobie;
    private List<PravidloPlanovaniaSmien> pravidla;

    public PlanovacSPravidlami(List<Zamestnanec> zamestnanci, PredlohaSmienPreObdobie predlohaSmienPreObdobie) {
        this.zamestnanci = Objects.requireNonNull(zamestnanci);
        this.predlohaSmienPreObdobie = Objects.requireNonNull(predlohaSmienPreObdobie);
        this.pravidla = new ArrayList<>();
    }

    public void pridat(PravidloPlanovaniaSmien pravidlo) {
        pravidla.add(pravidlo);
    }


    @Override
    public PlanSmien naplanuj(LocalDate zaciatok, LocalDate koniec, ZoneId timezone) {
        PlanSmien planSmien = new PlanSmien();
        LocalDate startTime = zaciatok;
        //iteration over templates
        while (startTime.isBefore(koniec)) {

            List<PolozkaPlanu> generatedSamples = predlohaSmienPreObdobie.vygenerujOd(startTime, timezone);
            for (PolozkaPlanu smena: generatedSamples) {

                List<Zamestnanec> moznyZamestnanci = moznyVykonavatelia(planSmien, smena);
                if (moznyZamestnanci.size() > 0) {
                    smena.setZamestnanec(moznyZamestnanci.get(0));
                    planSmien.pridatPolozku(smena);
                } else {
                    logger.warn("Vytvoreny plan: {}", planSmien);
                    throw new IllegalStateException("No possible employee for " + smena);
                }
            }

            startTime = startTime.plus(predlohaSmienPreObdobie.dlzkaObdobia());
        }
        return planSmien;
    }

    private List<Zamestnanec> moznyVykonavatelia(PlanSmien planSmien, PolozkaPlanu smena) {
        List<Zamestnanec> result = new ArrayList<>();
        PolozkaPlanu kopiaSmeny = new PolozkaPlanu(smena);
        for (Zamestnanec zamestnanec: zamestnanci) {
            boolean ok = true;
            kopiaSmeny.setZamestnanec(zamestnanec);
            for (PravidloPlanovaniaSmien pravidlo: pravidla) {
                VysledokKontrolyPravidla vysledok = pravidlo.over(planSmien, kopiaSmeny);
                if (VysledokKontrolyPravidla.BROKEN.equals(vysledok)) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                result.add(zamestnanec);
            }
        }
        return result;
    }
}
