package it.unicam.cs.mp.progettoesame.api.models;

import java.util.Objects;

/**
 * Classe che va a rappresentare una coordinata nello spazio
 */
public class Point {
    /**
     * Ascissa
     */
    private double x;
    /**
     * Ordinata
     */
    private double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point() {
        this(0, 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{ x: " + this.x + "; y: " + this.y + "}";
    }
}
