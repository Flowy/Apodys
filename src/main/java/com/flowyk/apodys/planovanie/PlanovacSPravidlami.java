package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
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
        List<Shift> shifts = generateShifts(zaciatok, koniec, timezone);

        for (Shift smena : shifts) {
            List<Zamestnanec> moznyZamestnanci = moznyVykonavatelia(planSmien, smena);
            if (moznyZamestnanci.size() > 0) {
                smena.setZamestnanec(moznyZamestnanci.get(0));
                planSmien.pridatPolozku(smena);
            } else {
                logger.debug("Vytvoreny plan: {}", planSmien);
                throw new IllegalStateException("No possible employee for " + smena);
            }
        }

        return planSmien;
    }

    private List<Shift> generateShifts(LocalDate start, LocalDate end, ZoneId timezone) {
        LocalDate current = start;
        List<Shift> shifts = new ArrayList<>();
        while (!current.isAfter(end)) {
            shifts.addAll(predlohaSmienPreObdobie.vygenerujOdDo(start, end, timezone));
            current = current.plus(predlohaSmienPreObdobie.dlzkaObdobia());
        }
        return shifts;
    }

    private List<Zamestnanec> moznyVykonavatelia(PlanSmien planSmien, Shift smena) {
        List<Zamestnanec> result = new ArrayList<>();
        Shift kopiaSmeny = new Shift(smena);
        for (Zamestnanec zamestnanec : zamestnanci) {
            boolean ok = true;
            kopiaSmeny.setZamestnanec(zamestnanec);
            for (PravidloPlanovaniaSmien pravidlo : pravidla) {
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
