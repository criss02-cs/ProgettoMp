package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

/**
 * Classe che rappresenta il comando REPEAT
 */
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
        Console.writeLine("REPEAT execution n°" + this.doneInterations + " of " + this.iterations + " by Robot: " +robot);
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
