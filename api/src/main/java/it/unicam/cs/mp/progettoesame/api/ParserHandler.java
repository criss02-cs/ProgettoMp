package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.CoordinatesSpeedCalculator;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;
import it.unicam.cs.mp.progettoesame.api.utils.RandomGenerator;


public class ParserHandler implements FollowMeParserHandler {
    private boolean isParsing;
    private Environment environment;
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

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
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

    private void moveRobots(double x, double y, double speed){
        for(Robot robot : this.environment.getRobots()) {
            robot.getPosition().setX(coordinatesSpeedCalculator
                    .calculateCoordinates(robot.getPosition().getX(), speed, x));
            robot.getPosition().setY(coordinatesSpeedCalculator
                    .calculateCoordinates(robot.getPosition().getY(), speed, y));
            robot.setMoving(true);
            robot.setDirection(new Direction(x, y));
        }
    }

    @Override
    public void moveRandomCommand(double[] args) {
        double x = random.getRandomNumber(args[0], args[1]);
        double y = random.getRandomNumber(args[2], args[3]);
        this.moveCommand(new double[] { x, y, args[4]});
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
}
