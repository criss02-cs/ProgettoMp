package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Classe che rappresenta il comando STOP
 */
public class StopInstruction implements RobotInstruction{
    @Override
    public void execute(Robot robot) throws IOException {
        robot.move(0, new Direction(0,0));
        System.out.println("STOP execution by Robot: " + robot);
        Console.writeLine("STOP execution by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new StopInstruction();
    }


}
