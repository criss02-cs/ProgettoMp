package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.DirectionCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.DistanceCalculator;

import java.io.IOException;
import java.util.List;

/**
 * Classe che rappresenta il comando FOLLOW
 */
public class FollowInstruction implements RobotInstruction {
    private final String labelToFollow;
    private final double distance;
    private final double speed;
    private final List<Robot> robots;

    public FollowInstruction(String labelToFollow, double distance, double speed, List<Robot> robots) {
        this.labelToFollow = labelToFollow;
        this.distance = distance;
        this.speed = speed;
        this.robots = robots;
    }

    @Override
    public void execute(Robot robot) throws IllegalArgumentException, IOException {
        List<Robot> robotWithLabel = this.getFilteredRobot(robot);
        if(robotWithLabel.size() > 0) {
            Point averagePoint = this.calculateAveragePoint(robotWithLabel);
            Direction dir = DirectionCalculator.calculate(robot.getPosition(), averagePoint);
            robot.move(this.speed, dir);
            System.out.println("FOLLOW execution by Robot: " + robot);
            Console.writeLine("FOLLOW execution by Robot: " + robot);
        } else {
            moveToRandom(robot);
        }
    }

    /**
     * Metodo che ritorna la lista di robot filtrata, in base alla label da seguire,
     * e alla distanza massima
     * @param robot il robot che deve essere controllato
     * @return la lista di robot filtrata
     */
    private List<Robot> getFilteredRobot(Robot robot) {
        return this.robots.stream()
                .filter(x -> x.getSignaledLabel().equalsIgnoreCase(this.labelToFollow))
                .filter(x -> DistanceCalculator.calculate(robot.getPosition(), x.getPosition()) <= this.distance)
                .toList();
    }

    /**
     * Metodo che muove il robot verso un punto randomico
     * @param robot il robot da muovere
     * @throws IllegalArgumentException se c'è qualche problema nei parametri passati
     */
    private void moveToRandom(Robot robot) throws IllegalArgumentException, IOException {
        Point point1 = new Point(-this.distance, -this.distance);
        Point point2 = new Point(this.distance, this.distance);
        new MoveRandomInstruction(point1, point2, this.speed).execute(robot);
    }

    /**
     * Metodo che calcola il punto medio verso il quale il robot si deve muovere
     * @param robotWithLabel la lista di robot su cui calcolare il punto medio
     * @return il punto medio verso cui il robot si deve muovere
     */
    private Point calculateAveragePoint(List<Robot> robotWithLabel) {
        double averageX = robotWithLabel.stream().mapToDouble(x -> x.getPosition().getX()).sum()
                / robotWithLabel.stream().mapToDouble(x -> x.getPosition().getX()).count();
        double averageY = robotWithLabel.stream().mapToDouble(x -> x.getPosition().getY()).sum()
                / robotWithLabel.stream().mapToDouble(x -> x.getPosition().getY()).count();
        return new Point(averageX, averageY);
    }

    @Override
    public int canGoToNextInstruction() {
        return -1;
    }

    @Override
    public RobotInstruction cloneObject() {
        return new FollowInstruction(this.labelToFollow, this.distance, this.speed, this.robots);
    }
}
