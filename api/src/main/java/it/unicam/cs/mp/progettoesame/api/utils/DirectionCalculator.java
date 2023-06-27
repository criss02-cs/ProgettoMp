package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;

/**
 * Interfaccia che si occupa di calcolare la Direzione fra 2 punti
 */
public interface DirectionCalculator {
    /**
     * Partendo da <code>source</code> calcola la direzione che subirà il robot
     * per arrivare a <code>destination</code>
     * @param source il punto di partenza
     * @param destination il punto di arrivo
     * @return la direzione che il robot dovrà seguire
     */
    static Direction calculate(Point source, Point destination) {
        double difX = DistanceCalculator.findCoordinatesDifference(source.getX(), destination.getX()) * checkSignOf(source.getX(), destination.getX());
        double difY = DistanceCalculator.findCoordinatesDifference(source.getY(), destination.getY()) * checkSignOf(source.getY(), destination.getY());
        double distance = DistanceCalculator.calculate(source, destination);
        if(distance == 0) { distance = 1; }
        return new Direction(difX / distance, difY / distance);
    }

    /**
     * Metodo che calcola il segno di ogni valore
     * @param source il punto di partenza
     * @param destination il punto di arrivo
     * @return 1 se {@code source} è minore di {@code destination}, altrimenti -1
     */
    private static double checkSignOf(double source, double destination) {
        if(source < destination) {
            return 1;
        } else {
            return -1;
        }
    }
}
