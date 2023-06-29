package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Classe che rappresenta il comando SIGNAL
 */
public class SignalInstruction implements RobotInstruction {
    private final String labelToSignal;

    public SignalInstruction(String labelToSignal) {
        this.labelToSignal = labelToSignal;
    }

    @Override
    public void execute(Robot robot) throws IOException {
        robot.signalLabel(this.labelToSignal);
        robot.continueMove();
        System.out.println("SIGNAL execution label " + this.labelToSignal + " by Robot: " + robot);
        Console.writeLine("SIGNAL execution label " + this.labelToSignal + " by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new SignalInstruction(this.labelToSignal);
    }
}
