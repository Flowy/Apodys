package com.flowyk.apodys.test;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.bussiness.entity.Shift;
import com.flowyk.apodys.bussiness.entity.Zamestnanec;
import com.flowyk.apodys.planovanie.RuleOffender;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestHelper {

    public static EmployeeShifts combine(Zamestnanec employee, Shift... shifts) {
        EmployeeShifts result = new EmployeeShifts(employee);
        for (Shift shift : shifts) {
            result.getShifts().add(shift);
        }
        return result;
    }

    public static List<EmployeeShifts> combine(Collection<Zamestnanec> employees) {
        List<EmployeeShifts> result = new ArrayList<>();
        for (Zamestnanec employee : employees) {
            result.add(new EmployeeShifts(employee));
        }
        return result;
    }

    public static void assertValid(Collection<RuleOffender> crimes) {
        Assert.assertTrue("There were problem shifts: " + crimes, crimes.isEmpty());
    }

    public static void assertBroken(Collection<RuleOffender> crimes) {
        Assert.assertTrue("Broken shifts was discovered OK", crimes.size() > 0);
    }
}
