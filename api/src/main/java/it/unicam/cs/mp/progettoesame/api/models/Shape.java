package it.unicam.cs.mp.progettoesame.api.models;

/**
 * Classe che definisce una Shape base, infatti possiede solamente
 * le coordinate
 */
public abstract class Shape implements IShape {
    private final Point coordinates;
    private final String label;

    public Shape(Point coordinates, String label) {
        this.coordinates = coordinates;
        this.label = label;
    }

    public Shape(Point coordinates) {
        this(coordinates, "");
    }
    public Shape(String label) {
        this(new Point(), label);
    }

    public Shape() {
        this(new Point(), "");
    }

    @Override
    public Point getCoordinates() {
        return this.coordinates;
    }
}
