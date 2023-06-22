package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;

public class UnsignalInstruction implements RobotInstruction {
    private final String labelToUnsignal;

    public UnsignalInstruction(String labelToUnsignal) {
        this.labelToUnsignal = labelToUnsignal;
    }

    @Override
    public void execute(Robot robot) {
        robot.unsignalLabel(this.labelToUnsignal);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction clone() {
        return new UnsignalInstruction(this.labelToUnsignal);
    }

}
