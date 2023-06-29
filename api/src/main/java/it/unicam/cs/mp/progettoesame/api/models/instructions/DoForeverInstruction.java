package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Classe che rappresenta il comando DOFOREVER
 */
public class DoForeverInstruction extends IterativeInstruction {
    public DoForeverInstruction(int firstRowIteration) {
        super(firstRowIteration);
    }

    @Override
    public void execute(Robot robot) throws IOException {
        System.out.println("DO FOREVER execution by Robot: " + robot);
        Console.writeLine("DO FOREVER execution by Robot: " + robot);
        robot.continueMove();
    }

    @Override
    public int canGoToNextInstruction() {
        return firstRowIteration;
    }

    @Override
    public RobotInstruction cloneObject() {
        DoForeverInstruction dfi = new DoForeverInstruction(this.firstRowIteration);
        dfi.endOfIteration = this.endOfIteration;
        return dfi;
    }


}
