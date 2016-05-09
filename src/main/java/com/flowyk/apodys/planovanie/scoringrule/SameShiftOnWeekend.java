package com.flowyk.apodys.planovanie.scoringrule;

import com.flowyk.apodys.planovanie.pravidlo.RovnakeSmenyCezVikend;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

public class SameShiftOnWeekend extends RuleBasedScoreCalculator implements ScoreCalculator {

    public SameShiftOnWeekend() {
        super(new RovnakeSmenyCezVikend());
    }

    @Override
    protected HardSoftScore parseBrokes(int brokes) {
        return HardSoftScore.valueOf(-brokes, 0);
    }
}
