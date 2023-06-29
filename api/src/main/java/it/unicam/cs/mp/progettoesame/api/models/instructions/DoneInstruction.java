package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Classe che rappresenta il comando DONE
 */
public class DoneInstruction implements RobotInstruction {
    private final int backNumberRow;

    public DoneInstruction(int irn) {
        this.backNumberRow = irn;
    }
    @Override
    public void execute(Robot robot) throws IOException {
        robot.continueMove();
        System.out.println("DONE command executed by Robot: " + robot);
        Console.writeLine("DONE command executed by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return this.backNumberRow;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new DoneInstruction(this.backNumberRow);
    }
}
