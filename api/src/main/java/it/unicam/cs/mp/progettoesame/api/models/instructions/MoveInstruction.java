package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

import java.io.IOException;

/**
 * Classe che rappresenta il comando MOVE
 */
public class MoveInstruction implements RobotInstruction {
    private final Direction direction;
    private final double speed;

    public MoveInstruction(Direction dir, double speed){
        this.direction = dir;
        this.speed = speed;
    }
    @Override
    public void execute(Robot robot) throws IOException {
        robot.move(this.speed, this.direction);
        System.out.println("MOVE execution in direction " + this.direction + " at speed " + this.speed + " by Robot: " + robot);
        Console.writeLine("MOVE execution in direction " + this.direction + " at speed " + this.speed + " by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new MoveInstruction(new Direction(this.direction.getX(), this.direction.getY()), this.speed);
    }

}
