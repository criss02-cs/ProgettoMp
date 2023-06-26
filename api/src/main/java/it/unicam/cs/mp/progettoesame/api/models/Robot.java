package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.Program;

public class Robot {
    private Direction direction;
    private Point position;
    private double speed;
    private String labelToSignal;
    private Program program;
    private boolean isProgramTerminated;
    public Robot(Point position) {
        this.position = position;
        this.direction = new Direction();
        this.labelToSignal = "";
        this.speed = 0;
    }
    public Robot() {
        this(new Point());
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point point) { this.position = point; }

    public boolean isProgramTerminated() {
        return isProgramTerminated;
    }

    public void move(double speed, Direction dir) {
        this.direction = new Direction(dir.getX(), dir.getY());
        this.speed = speed;
        this.position.setX((speed * dir.getX()) + this.position.getX());
        this.position.setY((speed * dir.getY()) + this.position.getY());
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
    public void loadProgramToExecute(Program program) {
        this.program = program;
    }

    public void executeNextInstruction(){
        this.isProgramTerminated = !this.program.executeInstruction(this);
    }
}
