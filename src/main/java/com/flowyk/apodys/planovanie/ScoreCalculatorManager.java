package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.planovanie.scoringrule.ScoreCalculator;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ScoreCalculatorManager implements EasyScoreCalculator<PlanSmien> {

    @Override
    public Score calculateScore(PlanSmien solution) {
        HardSoftScore result = HardSoftScore.valueOf(0, 0);
        for (ScoreCalculator calculator: solution.getScoreCalculators()) {
            result = result.add(calculator.calculateScore(solution));
        }
        return result;
    }
}
