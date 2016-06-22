package com.flowyk.apodys.planovanie.planner;

import com.flowyk.apodys.bussiness.entity.EmployeeShifts;
import com.flowyk.apodys.planovanie.Planovac;
import com.flowyk.apodys.test.TestovacieData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.flowyk.apodys.test.TestHelper.combine;

public class PatternPlannerTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void testNaplanuj() throws Exception {
        List<EmployeeShifts> employeeShifts = combine(Collections.singletonList(td.zamestnanci.get(0)));
        Planovac planovac = new PatternPlanner(Arrays.asList(td.tyzden40(), td.tyzden32()));
        planovac.naplanuj(
                employeeShifts,
                LocalDate.of(2015, 6, 1),
                LocalDate.of(2015, 6, 14));
    }

}