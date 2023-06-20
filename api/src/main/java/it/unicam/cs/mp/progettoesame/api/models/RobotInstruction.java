package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public interface RobotInstruction {
    void execute(Robot robot);

    Tuple<Boolean, Integer> canGoToNextInstruction();
}
