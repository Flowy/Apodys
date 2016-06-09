package com.flowyk.apodys.test;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleOffender;
import org.junit.Assert;

import java.util.Collection;

public class TestHelper {

    public static Shift combine(Shift shift, Zamestnanec employee) {
        shift.setZamestnanec(employee);
        return shift;
    }

    public static PlanSmien combine(PlanSmien planSmien, Shift... shifts) {
        for (Shift shift: shifts) {
            planSmien.pridatPolozku(shift);
        }
        return planSmien;
    }

    public static void assertValid(Collection<RuleOffender> crimes) {
        Assert.assertTrue("There were problem shifts: " + crimes, crimes.isEmpty());
    }

    public static void assertBroken(Collection<RuleOffender> crimes) {
        Assert.assertTrue("Broken shifts was discovered OK", crimes.size() > 0);
    }
}
