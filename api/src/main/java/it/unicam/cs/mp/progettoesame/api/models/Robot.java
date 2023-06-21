package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.CoordinatesSpeedCalculator;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    private Direction direction;
    private Point position;
    private double speed;
    private final List<String> labels;
    private String labelToSignal;
    private final CoordinatesSpeedCalculator<Double> coordinatesSpeedCalculator = (value, speed, direction)
            -> (speed * direction) + value;
    public Robot(Point position) {
        this.position = position;
        this.direction = new Direction();
        this.labels = new ArrayList<>();
        this.labelToSignal = "";
    }
    public Robot() {
        this(new Point());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public List<String> getLabels() {
        return labels;
    }
    public void addNewLabel(String label) {
        this.labels.add(label);
    }

    public void move(double speed, Direction dir) {
        this.direction = dir;
        this.speed = speed;
        this.position.setX(this.coordinatesSpeedCalculator
                .calculateCoordinates(this.position.getX(), speed, dir.getX()));
        this.position.setY(this.coordinatesSpeedCalculator
                .calculateCoordinates(this.position.getY(), speed, dir.getY()));
    }

    public void continueMove() {
        this.move(this.speed, this.direction);
    }

    public String getSignaledLabel() { return this.labelToSignal;}

    public void signalLabel(String label) {
        this.labelToSignal = label;
    }

    public void unsignalLabel(String label) {
        if(this.labelToSignal.equalsIgnoreCase(label)) {
            this.labelToSignal = "";
        }
    }
}
