package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.TestovacieData;
import com.flowyk.apodys.planovanie.scoringrule.OneShiftAtTime;
import com.flowyk.apodys.planovanie.scoringrule.SameShiftOnWeekend;
import org.junit.Before;
import org.junit.Test;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;

public class OptaPlannerTest {
    private static final Logger LOG = LoggerFactory.getLogger("test");

    TestovacieData td;
    private SolverFactory<PlanSmien> solverFactory;

    @Before
    public void setUp() throws Exception {
        td = new TestovacieData();
        solverFactory = SolverFactory.createFromXmlResource("com/flowyk/apodys/optaplanner/solverConfig.xml");
    }

    @Test
    public void optaplannerConfig() {
        solve(generatePlan());
    }

    @Test
    public void sameShiftOnWeekend() {
        PlanSmien planSmien = generatePlan();
        planSmien.addScoreCalculator(new SameShiftOnWeekend());
        solve(planSmien);
    }

    @Test
    public void oneShiftAtTime() {
        PlanSmien planSmien = generatePlan();
        planSmien.addScoreCalculator(new OneShiftAtTime());
        solve(planSmien);
    }

    @Test
    public void all() {
        PlanSmien planSmien = generatePlan();
        planSmien.addScoreCalculator(new SameShiftOnWeekend());
        planSmien.addScoreCalculator(new OneShiftAtTime());

        solve(planSmien);
    }

    private PlanSmien generatePlan() {
        return generateMonthPlan();
    }

    private PlanSmien generateSmallPlan() {
        LocalDate date = LocalDate.of(2016, 5, 1);
        PlanSmien planSmien = new PlanSmien(new ArrayList<>(), td.zamestnanci);
        planSmien.pridatPolozku(td.predlohaO75.vygenerujOd(date, td.testovanaZona));
        planSmien.pridatPolozku(td.predlohaO75.vygenerujOd(date, td.testovanaZona));
        return planSmien;
    }

    private PlanSmien generateMonthPlan() {
        return new PlanSmien(new ArrayList<>(
                td.tyzdennyPlan.vygenerujOdDo(
                        LocalDate.of(2016, 5, 1),
                        LocalDate.of(2016, 5, 30),
                        td.testovanaZona
                )), td.zamestnanci);
    }

    private PlanSmien solve(PlanSmien planSmien) {
        Solver<PlanSmien> solver = solverFactory.buildSolver();
        PlanSmien najlepsiPlan = solver.solve(planSmien);
        LOG.info("Najlepsi plan: {}", najlepsiPlan);
        return najlepsiPlan;
    }
}
