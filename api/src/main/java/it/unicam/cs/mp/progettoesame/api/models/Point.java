package it.unicam.cs.mp.progettoesame.api.models;

/**
 * Classe che va a rappresentare una coordinata nello spazio
 */
public class Point {
    private double x;
    private double y;

    public Point() {
        this(0,0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point)obj;
        return x == point.x && y == point.y;
    }
}
