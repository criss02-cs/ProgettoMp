package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.Program;

import java.io.IOException;

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

    /**
     * Metodo che muove il robot in una direzione, ad una determinata velocità
     * @param speed la velocità a cui si deve muovere
     * @param dir la direzione verso cui si deve muovere
     */
    public void move(double speed, Direction dir) {
        this.direction = new Direction(dir.getX(), dir.getY());
        this.speed = speed;
        this.position.setX((speed * dir.getX()) + this.position.getX());
        this.position.setY((speed * dir.getY()) + this.position.getY());
    }

    /**
     * Metodo che continua a muovere il robot
     */
    public void continueMove() {
        this.move(this.speed, this.direction);
    }

    /**
     * Metodo che restituisce la label segnalata dal robot
     * @return label segnalata
     */
    public String getSignaledLabel() { return this.labelToSignal;}

    /**
     * Metodo che permette al robot di segnalare una label
     * @param label
     */
    public void signalLabel(String label) {
        this.labelToSignal = label;
    }
    /**
     * Metodo che permette al robot di smettere di segnalare una label
     * @param label
     * @throws IllegalArgumentException se la label passata è diversa da quella che il robot sta segnalando
     */
    public void unsignalLabel(String label) throws IllegalArgumentException {
        if(!this.labelToSignal.equalsIgnoreCase(label)) {
            throw new IllegalArgumentException("La label passata non è uguale a quella che il robot sta segnalando");
        }
        this.labelToSignal = "";
    }

    /**
     * Metodo che carica il programma da eseguire al robot
     * @param program il programma da eseguire
     */
    public void loadProgramToExecute(Program program) {
        this.program = program;
    }

    /**
     * Metodo che esegue la prossima istruzione da eseguire
     */
    public void executeNextInstruction() throws IllegalArgumentException {
        this.isProgramTerminated = !this.program.executeInstruction(this);
    }
}
