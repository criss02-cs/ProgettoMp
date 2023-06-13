package it.unicam.cs.mp.progettoesame.api.models;

public class Robot {
    private boolean isMoving;
    private Direction direction;

    public Robot() {
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
}
