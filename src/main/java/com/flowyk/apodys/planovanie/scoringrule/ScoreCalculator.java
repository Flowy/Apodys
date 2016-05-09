package com.flowyk.apodys.planovanie.scoringrule;

import com.flowyk.apodys.PlanSmien;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

public interface ScoreCalculator {
    HardSoftScore calculateScore(PlanSmien solution);
}
