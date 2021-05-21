package cz.muni.pa165.teamwhite.formula1.service.scoring;

import cz.muni.pa165.teamwhite.formula1.dto.DriverDTO;

public interface DriverScorer {
    int score(DriverDTO driver);
}
