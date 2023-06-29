package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.Point;

import java.util.Random;

public interface RandomCoordinatesCalculator {
    /**
     * Metodo che calcola una coordinata randomica a partire dalle 2 posizioni già presenti
     * @return il punto generato randomicamente
     */
    static Point calculate(Point position1, Point position2) {
        Random random = new Random();
        double x = position1.getX() + (position2.getX() - position1.getX()) * random.nextDouble();
        double y = position1.getY() + (position2.getY() - position1.getY()) * random.nextDouble();
        return new Point(x, y);
    }
}
