package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;

public class DoneInstruction implements RobotInstruction {
    private final int backNumberRow;

    public DoneInstruction(int irn) {
        this.backNumberRow = irn;
    }
    @Override
    public void execute(Robot robot) {
        // TODO muovi il robot se si sta muovendo
        System.out.println("DONE command executed by Robot: " + robot);
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
