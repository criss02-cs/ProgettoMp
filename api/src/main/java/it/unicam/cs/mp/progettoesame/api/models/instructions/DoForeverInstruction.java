package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;

public class DoForeverInstruction implements RobotInstruction {
    @Override
    public void execute(Robot robot) {
        robot.continueMove();
    }

    @Override
    public int canGoToNextInstruction() {
        return 0;
    }

    @Override
    public RobotInstruction clone() {
        return new DoForeverInstruction();
    }


}
