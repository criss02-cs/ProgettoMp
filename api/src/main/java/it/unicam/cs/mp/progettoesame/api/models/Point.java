package it.unicam.cs.mp.progettoesame.api.models;

public record Point(double x, double y) {
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point)obj;
        return x == point.x && y == point.y;
    }
}
