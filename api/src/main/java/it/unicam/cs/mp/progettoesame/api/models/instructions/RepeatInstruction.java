package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public class RepeatInstruction implements RobotInstruction {
    private final int iterations;
    private int doneInterations;

    public RepeatInstruction(int iterations) {
        this.iterations = iterations;
        this.doneInterations = 0;
    }

    @Override
    public void execute(Robot robot) {
        this.doneInterations++;
    }

    @Override
    public int canGoToNextInstruction() {
        return this.doneInterations < this.iterations ? 0 : -1;
    }
}
