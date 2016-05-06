package com.flowyk.apodys.planovanie;

import com.flowyk.apodys.PlanSmien;
import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

public class ScoreCalculator implements EasyScoreCalculator<PlanSmien> {

    @Override
    public Score calculateScore(PlanSmien solution) {
        return HardSoftScore.valueOf(0, 0);
    }
}
