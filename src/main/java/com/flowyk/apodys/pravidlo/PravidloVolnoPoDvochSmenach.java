package com.flowyk.apodys.pravidlo;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.PolozkaPlanu;
import com.flowyk.apodys.TypPolozkyPlanu;

import java.time.Duration;
import java.util.Objects;

public class PravidloVolnoPoDvochSmenach implements PravidloPlanovaniaSmien {
    TypPolozkyPlanu typPrvejSmeny;
    TypPolozkyPlanu typDruhejSmeny;
    Duration dlzkaVolna;

    public PravidloVolnoPoDvochSmenach(TypPolozkyPlanu typPrvejSmeny, TypPolozkyPlanu typDruhejSmeny, Duration dlzkaVolna) {
        this.typPrvejSmeny = Objects.requireNonNull(typPrvejSmeny);
        this.typDruhejSmeny = Objects.requireNonNull(typDruhejSmeny);
        this.dlzkaVolna = Objects.requireNonNull(dlzkaVolna);
    }

    @Override
    public VysledokKontrolyPravidla over(PlanSmien naplanovaneSmeny, PolozkaPlanu test) {
        PlanSmien skumanyPlan = naplanovaneSmeny.preZamestnanca(test.vykonavatel());
        boolean firstMatched = false;
        PolozkaPlanu poslednaZhoda = null;
        for (PolozkaPlanu current: skumanyPlan) {
            //must be last match in plan
            poslednaZhoda = null;
            if (firstMatched && current.typ().equals(typDruhejSmeny)) {
                //this is second iteration - only if first one matched
                poslednaZhoda = current;
            }
            firstMatched = current.typ().equals(typPrvejSmeny);
        }
        if (poslednaZhoda != null && dlzkaVolna.compareTo(Duration.between(poslednaZhoda.koniec(), test.zaciatok())) >= 0) {
            return VysledokKontrolyPravidla.BROKEN;
        } else {
            return VysledokKontrolyPravidla.OK;
        }
    }
}
