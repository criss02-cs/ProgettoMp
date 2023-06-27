package it.unicam.cs.mp.progettoesame.api.models;

/**
 * Classe che definisce una Shape base, infatti possiede solamente
 * le coordinate
 */
public abstract class Shape implements IShape {
    /**
     * Coordinate dell'area
     */
    private final Point coordinates;
    /**
     * Label da mostrare
     */
    private final String label;

    public Shape(Point coordinates, String label) {
        this.coordinates = coordinates;
        this.label = label;
    }

    @Override
    public Point getCoordinates() {
        return this.coordinates;
    }
    @Override
    public String getLabel() {
        return this.label;
    }
}
