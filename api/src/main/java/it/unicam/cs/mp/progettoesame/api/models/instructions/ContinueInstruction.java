package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Classe che rappresenta il comando CONTINUE
 */
public class ContinueInstruction implements RobotInstruction {
    private final int secondsToExecute;
    private int secondsExecuted;
    private final int rowNumber;

    public ContinueInstruction(int s, int rn) {
        this.secondsToExecute = s;
        this.secondsExecuted = 0;
        this.rowNumber = rn;
    }
    @Override
    public void execute(Robot robot) throws IOException {
        robot.continueMove();
        this.secondsExecuted++;
        System.out.println("Continue execution seconds n°" + this.secondsExecuted + " of " + this.secondsToExecute + " by Robot: " + robot);
        Console.writeLine("Continue execution seconds n°" + this.secondsExecuted + " of " + this.secondsToExecute + " by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return this.secondsExecuted < this.secondsToExecute ? this.rowNumber : -1;
    }
    @Override
    public RobotInstruction cloneObject() {
        return new ContinueInstruction(this.secondsToExecute, this.rowNumber);
    }
}
