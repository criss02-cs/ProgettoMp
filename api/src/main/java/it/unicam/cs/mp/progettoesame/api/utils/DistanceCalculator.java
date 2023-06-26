package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.Point;

/**
 * Interfaccia che si occupa di calcolare la distanza fra 2 punti
 * e offre anche un metodo per calcolare la differenza tra coordinate
 */
public interface DistanceCalculator {
    /**
     * Calcola la distanza effettiva tra i 2 punti
     * @param source il punto di partenza
     * @param destination il punto di arrivo
     * @return la distanza che intercorre tra i 2 punti
     */
    static double calculate(Point source, Point destination) {
        double difX = findCoordinatesDifference(source.getX(), destination.getX(), false);
        double difY = findCoordinatesDifference(source.getY(), destination.getY(), true);
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    static double findCoordinatesDifference(double source, double destination, boolean isInverted) {
        return isInverted ? source - destination : Math.abs(source - destination);
    }
}
