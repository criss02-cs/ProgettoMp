package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;

public class DoneInstruction implements RobotInstruction {
    private final int backNumberRow;
    private final RobotInstruction iterationInstruction;

    public DoneInstruction(int irn, RobotInstruction instruction) {
        this.backNumberRow = irn;
        this.iterationInstruction = instruction;
    }
    @Override
    public void execute(Robot robot) {
        // TODO muovi il robot se si sta muovendo
        this.iterationInstruction.execute(robot);
    }

    @Override
    public int canGoToNextInstruction() {
        int canGoForward = this.iterationInstruction.canGoToNextInstruction();
        return canGoForward == -1 ? -1 : this.backNumberRow;
    }

    @Override
    public RobotInstruction clone() {
        return new DoneInstruction(this.backNumberRow, this.iterationInstruction.clone());
    }
}
