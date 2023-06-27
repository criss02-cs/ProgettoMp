package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.DirectionCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.DistanceCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

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
    public void execute(Robot robot) {
        Point randomPoint = this.getRandomCoordinates();
        Direction dir = DirectionCalculator.calculate(robot.getPosition(), randomPoint);
        double distance = DistanceCalculator.calculate(robot.getPosition(), randomPoint);
        while (checkTollerance(randomPoint, robot.getPosition())
                && DistanceCalculator.calculate(robot.getPosition(), randomPoint) <= distance)
            robot.move(speed, dir);
        robot.setPosition(randomPoint);
        System.out.println("MOVE RANDOM execution in position " + randomPoint + " at speed " + this.speed + " with direction " + dir + " by Robot: " + robot);
    }

    /**
     * Metodo che controlla eventuale errore della posizione, in quanto
     * non sarà mai preciso
     * @param point1 il punto di partenza
     * @param point2 il punto di arrivo
     * @return true se sono in errore non tollerabile,
     *  false altrimenti
     */
    private boolean checkTollerance(Point point1, Point point2) {
        return (Math.abs(point1.getX() - point2.getX()) > 1
                && Math.abs(point1.getY() - point2.getY()) > 1);
    }

    /**
     * Metodo che calcola una coordinata randomica a partire dalle 2 posizioni già presenti
     * @return il punto generato randomicamente
     */
    private Point getRandomCoordinates() {
        Random random = new Random();
        double x = position1.getX() + (position2.getX() - position1.getX()) * random.nextDouble();
        double y = position1.getY() + (position2.getY() - position1.getY()) * random.nextDouble();
        return new Point(x, y);
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
