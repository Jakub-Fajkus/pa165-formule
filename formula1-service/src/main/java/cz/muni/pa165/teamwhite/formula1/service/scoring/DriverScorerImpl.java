package cz.muni.pa165.teamwhite.formula1.service.scoring;

import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;
import org.springframework.stereotype.Component;

@Component
public class DriverScorerImpl implements DriverScorer {
    @Override
    public int score(DriverDTO driver) {
        int score = driver.getWetDriving() * 5 + driver.getReactions() * 10;

        return driver.isAggressive() ? score / 2 : score;
    }
}
