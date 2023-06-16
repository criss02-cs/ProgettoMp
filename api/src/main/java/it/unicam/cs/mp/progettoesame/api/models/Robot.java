package it.unicam.cs.mp.progettoesame.api.models;

public class Robot {
    private boolean isMoving;
    private Direction direction;
    private Point position;
    private double speed;

    public Robot() {
        this.direction = new Direction();
        this.position = new Point();
    }

    public Robot(Point position) {
        this();
        this.position = position;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
