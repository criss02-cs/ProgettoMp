package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public class MoveInstruction implements RobotInstruction {
    private final Direction direction;
    private final double speed;

    public MoveInstruction(Direction dir, double speed){
        this.direction = dir;
        this.speed = speed;
    }
    @Override
    public void execute(Robot robot) {
        robot.move(this.speed, this.direction);
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
