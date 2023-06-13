package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.utilities.NumericRangeChecker;

public class Direction extends Point {


    public Direction(double x, double y) {
        super(validateParameters(x), validateParameters(y));
    }

    public Direction() {
        super();
    }

    private static double validateParameters(double param) {
        if(!NumericRangeChecker.DEFAULT_CHECKER.isBetween(param, 1.0,-1.0)) {
            throw new IllegalArgumentException("Il valore deve essere compreso tra -1 e 1");
        }
        return param;
    }

}