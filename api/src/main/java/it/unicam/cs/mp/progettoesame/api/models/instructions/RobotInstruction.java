package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public interface RobotInstruction {
    void execute(Robot robot);

    Tuple<Boolean, Integer> canGoToNextInstruction();
}
