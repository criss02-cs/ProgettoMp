package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public class StopInstruction implements RobotInstruction{
    @Override
    public void execute(Robot robot) {
        robot.move(0, new Direction(0,0));
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction clone() {
        return new StopInstruction();
    }


}
