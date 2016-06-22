package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static com.flowyk.apodys.test.TestHelper.combine;
import static org.junit.Assert.assertNotNull;

public class ZakladnyPlanovacTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testNaplanuj() throws Exception {
        Planovac planovac = new ZakladnyPlanovac(td.tyzdennyPlan);
        List<EmployeeShifts> employeeShifts = combine(td.zamestnanci);
        planovac.naplanuj(
                employeeShifts,
                LocalDate.of(2015, 11, 30),
                LocalDate.of(2015, 12, 27));
        for (EmployeeShifts polozka : employeeShifts) {
            assertNotNull(polozka.getEmployee());
        }
    }
}