package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.models.instructions.RobotInstruction;

import java.util.LinkedList;
import java.util.List;

public class Program {
    private List<RobotInstruction> robotInstructions;

    private int programCounter;


    public Program() {
        this.programCounter = 0;
        this.robotInstructions = new LinkedList<>();
    }

    public void addInstruction(RobotInstruction instruction) {
        robotInstructions.add(instruction);
    }

    public void executeInstruction(Robot robot) {
        RobotInstruction instruction = this.robotInstructions.get(this.programCounter);
        instruction.execute(robot);
        if(instruction.canGoToNextInstruction().getItem1()) {
            this.programCounter++;
        }
    }
}
