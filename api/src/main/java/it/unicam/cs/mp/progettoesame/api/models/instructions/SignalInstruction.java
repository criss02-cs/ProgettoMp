package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;

public class SignalInstruction implements RobotInstruction {
    private final String labelToSignal;

    public SignalInstruction(String labelToSignal) {
        this.labelToSignal = labelToSignal;
    }

    @Override
    public void execute(Robot robot) {
        robot.signalLabel(this.labelToSignal);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction clone() {
        return new SignalInstruction(this.labelToSignal);
    }
}
