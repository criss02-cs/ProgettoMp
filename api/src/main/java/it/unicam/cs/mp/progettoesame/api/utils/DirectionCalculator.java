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
        double difX = DistanceCalculator.findCoordinatesDifference(source.getX(), destination.getX());
        double difY = DistanceCalculator.findCoordinatesDifference(source.getY(), destination.getY());
        double distance = DistanceCalculator.calculate(source, destination);
        return new Direction(difX / distance, difY / distance);
    }
}
