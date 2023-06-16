package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;

import java.util.List;
import java.util.Random;


public class ParserHandler implements FollowMeParserHandler {
    private IEnvironment environment;
    private NumericRangeChecker<Double> checker;

    public ParserHandler(IEnvironment environment) {
        this.environment = environment;
    }

    public ParserHandler() {
        this(new Environment());
    }

    public void setEnvironment(IEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public void parsingStarted() {
        this.checker = NumericRangeChecker.DEFAULT_CHECKER;
    }

    @Override
    public void parsingDone() {
        this.checker = null;
    }

    @Override
    public void moveCommand(double[] args) {
        if (isValidDirection(args[0], args[1])) {
            this.environment.getRobots()
                    .forEach(robot -> robot.move(args[2], new Direction(args[0], args[1])));
        }
    }

    private boolean isValidDirection(double x, double y) {
        return checker.isBetween(x, -1.0, 1.0)
                && checker.isBetween(y, -1.0, 1.0);
    }

    @Override
    public void moveRandomCommand(double[] args) {
        for (Robot robot : this.environment.getRobots()) {
            double x = new Random().nextDouble(args[1] - args[0] + 1) + args[0];
            double y = new Random().nextDouble(args[3] - args[2] + 1) + args[2];
            Point destination = new Point(x, y);
            System.out.println("Destination: " + destination);
            Direction dir = Direction.calculateDirection(robot.getPosition(), destination);
            while (Math.abs(x - robot.getPosition().getX()) > 1 && Math.abs(y - robot.getPosition().getY()) > 1)
                robot.move(args[4], dir);
            robot.move(0, dir);
            //robot.setPosition(destination);
        }
    }

    @Override
    public void signalCommand(String label) {

    }

    @Override
    public void unsignalCommand(String label) {

    }

    @Override
    public void followCommand(String label, double[] args) {
        /*List<Robot> robotsWithLabel = this.environment.getRobots().stream().filter(x -> x.getLabels().contains(label)).toList();
        double dist = args[1];
        if (robotsWithLabel.size() > 0) {
            double averageX = robotsWithLabel.stream().mapToDouble(x -> x.getPosition().getX()).sum()
                    / robotsWithLabel.stream().mapToDouble(x -> x.getPosition().getX()).count();
            double averageY = robotsWithLabel.stream().mapToDouble(x -> x.getPosition().getY()).sum()
                    / robotsWithLabel.stream().mapToDouble(x -> x.getPosition().getY()).count();
        } else {
            moveRandomCommand(new double[]{-dist, dist, -dist, dist, args[2]});
        }*/
    }

    @Override
    public void stopCommand() {
        this.environment.getRobots().forEach(robot -> robot.move(0, new Direction(0,0)));
    }

    @Override
    public void continueCommand(int s) {
        this.environment.getRobots().forEach(Robot::continueMove);
    }

    @Override
    public void repeatCommandStart(int n) {

    }

    @Override
    public void untilCommandStart(String label) {

    }

    @Override
    public void doForeverStart() {

    }

    @Override
    public void doneCommand() {

    }

}
