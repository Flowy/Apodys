package com.flowyk.apodys.test;

import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleOffender;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestHelper {

    public static Shift combine(Shift shift, Zamestnanec employee) {
        shift.setEmployee(employee);
        return shift;
    }

    public static List<Shift> combine(Shift... shifts) {
        List<Shift> list = new ArrayList<>();
        for (Shift shift: shifts) {
            list.add(shift);
        }
        return list;
    }

    public static void assertValid(Collection<RuleOffender> crimes) {
        Assert.assertTrue("There were problem shifts: " + crimes, crimes.isEmpty());
    }

    public static void assertBroken(Collection<RuleOffender> crimes) {
        Assert.assertTrue("Broken shifts was discovered OK", crimes.size() > 0);
    }
}
