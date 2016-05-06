package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.TestovacieData;
import org.junit.Before;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.impl.score.buildin.hardsoft.HardSoftScoreDefinition;

import java.time.LocalDate;

public class OptaPlannerTest {

    TestovacieData td;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
    }

    @Test
    public void optaplannerConfig() {
        SolverFactory<PlanSmien> solverFactory = SolverFactory.createFromXmlResource("com/flowyk/apodys/optaplanner/solverConfig.xml");
        Solver<PlanSmien> solver = solverFactory.buildSolver();
        Planovac planovac = new ZakladnyPlanovac(td.zamestnanci, td.tyzdennyPlan);
        PlanSmien planSmien = planovac.naplanuj(
                LocalDate.of(2016, 5, 1),
                LocalDate.of(2016, 5, 7),
                td.testovanaZona
        );
        PlanSmien najlepsiPlan = solver.solve(planSmien);
    }
}
