package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.DirectionCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.DistanceCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.RandomCoordinatesCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

import java.io.IOException;
import java.util.Random;

/**
 * Classe che rappresenta il comando MOVE RANDOM
 */
public class MoveRandomInstruction implements RobotInstruction {
    private final Point position1;
    private final Point position2;
    private final double speed;

    public MoveRandomInstruction(Point position1, Point position2, double speed) {
        this.position1 = position1;
        this.position2 = position2;
        this.speed = speed;
    }

    @Override
    public void execute(Robot robot) throws IOException {
        Point randomPoint = RandomCoordinatesCalculator.calculate(position1, position2);
        Direction dir = DirectionCalculator.calculate(robot.getPosition(), randomPoint);
        robot.move(speed, dir);
        System.out.println("MOVE RANDOM execution in position " + randomPoint + " at speed " + this.speed + " with direction " + dir + " by Robot: " + robot);
        Console.writeLine("MOVE RANDOM execution in position " + randomPoint + " at speed " + this.speed + " with direction " + dir + " by Robot: " + robot);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new MoveRandomInstruction(new Point(this.position1.getX(), this.position1.getY()),
                new Point(this.position2.getX(), this.position2.getY()), this.speed);
    }

}
