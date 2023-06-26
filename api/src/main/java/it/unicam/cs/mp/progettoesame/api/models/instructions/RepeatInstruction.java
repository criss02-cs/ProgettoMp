package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public class RepeatInstruction extends IterativeInstruction {
    private final int iterations;
    private int doneInterations;

    public RepeatInstruction(int iterations, int firstRowIteration) {
        super(firstRowIteration);
        this.iterations = iterations;
        this.doneInterations = 0;
    }

    @Override
    public void execute(Robot robot) {
        this.doneInterations += 1;
        robot.continueMove();
        System.out.println("REPEAT execution n°" + this.doneInterations + " of " + this.iterations + " by Robot: " +robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return this.doneInterations < this.iterations ? this.firstRowIteration : this.endOfIteration;
    }

    @Override
    public RobotInstruction cloneObject() {
        RepeatInstruction ri = new RepeatInstruction(this.iterations, this.firstRowIteration);
        ri.endOfIteration = this.endOfIteration;
        return ri;
    }
}
