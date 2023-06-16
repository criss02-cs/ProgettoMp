package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;

public class Direction extends Point {


    public Direction(double x, double y) {
        super(validateParameters(x), validateParameters(y));
    }

    public Direction() {
        super();
    }

    private static double validateParameters(double param) {
        if(!NumericRangeChecker.DEFAULT_CHECKER.isBetween(param, -1.0,1.0)) {
            throw new IllegalArgumentException("Il valore deve essere compreso tra -1 e 1");
        }
        return param;
    }

    @Override
    public void setX(double x) {
        super.setX(validateParameters(x));
    }

    @Override
    public void setY(double y) {
        super.setY(validateParameters(y));
    }

    /**
     * Metodo che calcola la differenza fra la x o la y di una coordinata
     * @param source coordinata iniziale
     * @param destination coordinata da raggiungere
     * @return la differenza fra <code>destination</code> e <code>source</code>
     */
    private static double findCoordinatesDifference(double source, double destination) {
        return Math.abs(destination - source);
    }

    /**
     * Calcola la distanza effettiva tra i 2 punti
     * @param difX la distanza delle x
     * @param difY la distanza delle y
     * @return la distanza che intercorre tra i 2 punti
     */
    private static double calculateDistance(double difX, double difY) {
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    /**
     * Partendo da <code>source</code> calcola la direzione che subirà il robot
     * per arrivare a <code>destination</code>
     * @param source il punto di partenza
     * @param destination il punto di arrivo
     * @return la direzione che il robot dovrà seguire
     */
    public static Direction calculateDirection(Point source, Point destination) {
        double difX = findCoordinatesDifference(source.getX(), destination.getX());
        double difY = findCoordinatesDifference(source.getY(), destination.getY());
        double distance = calculateDistance(difX, difY);
        return new Direction(difX / distance, difY / distance);
    }

}
