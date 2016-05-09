package com.flowyk.apodys.planovanie.scoringrule;

import com.flowyk.apodys.PlanSmien;
import com.flowyk.apodys.Shift;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

public class OneShiftAtTime implements ScoreCalculator {

    @Override
    public HardSoftScore calculateScore(PlanSmien solution) {
        return parseBrokes(countBrokes(solution));
    }

    private int countBrokes(PlanSmien solution) {
        int result = 0;
        for (Shift test: solution) {
            boolean foundSelf = false;
            for (Shift current: solution) {
                if (test.rovnakyVykonavatel(current) && test.prekryva(current)) {
                    if (foundSelf) {
                        result++;
                    } else {
                        foundSelf = true;
                    }
                }
            }
        }
        return result;
    }

    private HardSoftScore parseBrokes(int brokes) {
        return HardSoftScore.valueOf(-brokes, 0);
    }
}
