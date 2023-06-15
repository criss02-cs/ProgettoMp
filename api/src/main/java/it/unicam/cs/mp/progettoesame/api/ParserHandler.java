package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.CoordinatesSpeedCalculator;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;
import it.unicam.cs.mp.progettoesame.api.utils.RandomGenerator;

import java.util.Random;


public class ParserHandler implements FollowMeParserHandler {
    private boolean isParsing;
    private IEnvironment environment;
    private RandomGenerator<Double> random;
    private NumericRangeChecker<Double> checker;
    private CoordinatesSpeedCalculator<Double> coordinatesSpeedCalculator = (value, speed, direction)
            -> (speed * direction) + value;

    public ParserHandler() {
        this.isParsing = false;
        this.environment = new Environment();
        this.random = new RandomGenerator<>();
        this.checker = NumericRangeChecker.DEFAULT_CHECKER;
    }

    public ParserHandler(Environment environment) {
        this.isParsing = false;
        this.environment = environment;
        this.checker = NumericRangeChecker.DEFAULT_CHECKER;
        this.random = new RandomGenerator<>();
    }

    public IEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(IEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Flag per mostrare se si sta effettuando il parsing di un programma
     * @return true se il parsing è in corso, false se non lo è
     */
    public boolean isParsing() {
        return isParsing;
    }

    public void setParsing(boolean parsing) {
        isParsing = parsing;
    }

    @Override
    public void parsingStarted() {
        isParsing = true;
    }

    @Override
    public void parsingDone() {
        isParsing = false;
    }

    @Override
    public void moveCommand(double[] args) {
        double x = args[0];
        double y = args[1];
        double speed = args[2];
        if(checker.isBetween(x, -1.0, 1.0)
                && checker.isBetween(y, -1.0, 1.0)){
            moveRobots(x, y, speed);
        }
    }

    @Override
    public void moveRandomCommand(double[] args) {
        for(Robot robot : this.environment.getRobots()){
            double x = new Random().nextDouble(args[1] - args[0] + 1) + args[0];
            double y = new Random().nextDouble(args[3] - args[2] + 1) + args[2];
            Point destination = new Point(x, y);
            System.out.println("Destination " + destination);
            Direction dir = this.calculateDirection(robot.getPosition(), destination);
            while(Math.abs(x - robot.getPosition().getX()) > 1 && Math.abs(y - robot.getPosition().getY()) > 1)
            {
                System.out.println("X" + Math.abs(x - robot.getPosition().getX()) + " Y" + Math.abs(y - robot.getPosition().getY()));
                this.moveRobot(robot, args[4], dir, true);
            }
            System.out.println("X" + Math.abs(x - robot.getPosition().getX()) + " Y" + Math.abs(y - robot.getPosition().getY()));
            robot.setMoving(false);
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

    }

    @Override
    public void stopCommand() {
        for(Robot robot : this.environment.getRobots()) {
            robot.setDirection(null);
            robot.setMoving(false);
        }
    }

    @Override
    public void continueCommand(int s) {

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

    /**
     * Metodo che calcola la differenza fra la x o la y di una coordinata
     * @param source coordinata iniziale
     * @param destination coordinata da raggiungere
     * @return la differenza fra <code>destination</code> e <code>source</code>
     */
    private double findCoordinatesDifference(double source, double destination) {
        return Math.abs(destination - source);
    }

    /**
     * Calcola la distanza effettiva tra i 2 punti
     * @param difX la distanza delle x
     * @param difY la distanza delle y
     * @return la distanza che intercorre tra i 2 punti
     */
    private double calculateDistance(double difX, double difY) {
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    /**
     * Partendo da <code>source</code> calcola la direzione che subirà il robot
     * per arrivare a <code>destination</code>
     * @param source il punto di partenza
     * @param destination il punto di arrivo
     * @return la direzione che il robot dovrà seguire
     */
    private Direction calculateDirection(Point source, Point destination) {
        double difX = findCoordinatesDifference(source.getX(), destination.getX());
        double difY = findCoordinatesDifference(source.getY(), destination.getY());
        double distance = calculateDistance(difX, difY);
        return new Direction(difX / distance, difY / distance);
    }

    /**
     * Metodo di utility per scorrere tutta la lista dei robot e muoverli
     * @param x la direzione x verso cui si deve muovere
     * @param y la direzione y verso cui si deve muovere
     * @param speed la velocità con cui si deve muovere
     */
    private void moveRobots(double x, double y, double speed) {
        for (Robot robot : this.environment.getRobots()) {
            this.moveRobot(robot, speed, new Direction(x, y), true);
        }
    }

    /**
     * Metodo che muove effettivamente il robot
     * @param robot il robot che si deve muovere
     * @param speed la velocità con cui si deve muovere
     * @param coordinates la direzione in cui si deve muovere
     * @param isMoving flag per vedere se il robot si muove oppure
     */
    private void moveRobot(Robot robot, double speed, Direction coordinates, boolean isMoving) {
        System.out.println("Robot move " + robot.getPosition());
        robot.getPosition().setX(coordinatesSpeedCalculator
                .calculateCoordinates(robot.getPosition().getX(), speed, coordinates.getX()));
        robot.getPosition().setY(coordinatesSpeedCalculator
                .calculateCoordinates(robot.getPosition().getY(), speed, coordinates.getY()));
        robot.setMoving(isMoving);
        robot.setDirection(coordinates);
    }

}
