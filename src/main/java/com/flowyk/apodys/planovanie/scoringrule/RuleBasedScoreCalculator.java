package com.flowyk.apodys.planovanie.scoringrule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import com.flowyk.apodys.planovanie.pravidlo.PravidloPlanovaniaSmien;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

public abstract class RuleBasedScoreCalculator implements ScoreCalculator {
    private PravidloPlanovaniaSmien rule;

    protected RuleBasedScoreCalculator(PravidloPlanovaniaSmien rule) {
        this.rule = rule;
    }

    @Override
    public HardSoftScore calculateScore(PlanSmien solution) {
        int brokes = 0;
        for (Shift shift: solution) {
            if (rule.over(solution, shift).isBroken()) {
                brokes++;
            }
        }
        return parseBrokes(brokes);
    }

    protected abstract HardSoftScore parseBrokes(int brokes);

}
