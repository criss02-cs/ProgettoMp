package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.utilities.NumericRangeChecker;
import it.unicam.cs.mp.progettoesame.utilities.RandomGenerator;

import java.util.Map;

public class ParserHandler implements FollowMeParserHandler {
    private boolean isParsing;
    private Ambiente environment;
    private RandomGenerator<Double> random;
    private NumericRangeChecker<Double> checker;

    public ParserHandler() {
        this.isParsing = false;
        this.environment = new Ambiente();
        this.random = new RandomGenerator<>();
        this.checker = NumericRangeChecker.DEFAULT_CHECKER;
    }

    public ParserHandler(Ambiente environment) {
        this.isParsing = false;
        this.environment = environment;
        this.checker = NumericRangeChecker.DEFAULT_CHECKER;
        this.random = new RandomGenerator<>();
    }

    public Ambiente getEnvironment() {
        return environment;
    }

    public void setEnvironment(Ambiente environment) {
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
        if(checker.isBetween(x, 1.0, -1.0)
                && checker.isBetween(x, 1.0, -1.0)){
            moveRobots(x, y, speed);
        }
    }

    private void moveRobots(double x, double y, double speed){
        for(Map.Entry<Robot, Point> entry : this.environment.getRobots().entrySet()) {
            entry.getValue().setX(entry.getValue().getX() + x);
            entry.getValue().setY(entry.getValue().getY() + y);
            entry.getKey().setMoving(true);
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
        for(Map.Entry<Robot, Point> entry : this.environment.getRobots().entrySet()) {
            entry.getValue().setX(entry.getValue().getX());
            entry.getValue().setY(entry.getValue().getY());
            entry.getKey().setMoving(false);
        }
    }

    @Override
    public void waitCommand(int s) {

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
