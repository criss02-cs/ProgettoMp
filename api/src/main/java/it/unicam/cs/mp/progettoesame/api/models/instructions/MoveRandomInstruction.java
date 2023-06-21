package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.DirectionCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

import java.util.Random;

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
    public void execute(Robot robot) {
        Point randomPoint = this.getRandomCoordinates();
        Direction dir = DirectionCalculator.calculate(robot.getPosition(), randomPoint);
        while (checkTollerance(randomPoint, robot.getPosition()))
            robot.move(speed, dir);
        robot.move(0, dir);
    }

    private boolean checkTollerance(Point point1, Point point2) {
        return (Math.abs(point1.getX() - point2.getX()) > 1
                && Math.abs(point1.getY() - point2.getY()) > 1);
    }

    private Point getRandomCoordinates() {
        double x = new Random().nextDouble(position2.getX() - position1.getX() + 1) + position1.getX();
        double y = new Random().nextDouble(position2.getY() - position1.getY() + 1) + position1.getY();
        return new Point(x, y);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }
}
