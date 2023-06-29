package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Classe che rappresenta il comando UNSIGNAL
 */
public class UnsignalInstruction implements RobotInstruction {
    private final String labelToUnsignal;

    public UnsignalInstruction(String labelToUnsignal) {
        this.labelToUnsignal = labelToUnsignal;
    }

    @Override
    public void execute(Robot robot) throws IllegalArgumentException, IOException {
        robot.unsignalLabel(this.labelToUnsignal);
        robot.continueMove();
        System.out.println("USIGNAL execution label " + this.labelToUnsignal + " by Robot: " + robot);
        Console.writeLine("USIGNAL execution label " + this.labelToUnsignal + " by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new UnsignalInstruction(this.labelToUnsignal);
    }

}
