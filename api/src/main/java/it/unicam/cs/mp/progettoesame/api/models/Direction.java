package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;

/**
 * Classe che rappresenta la Direzione, estende la classe <code>Point</code>
 * ma verifica che ci siano solo valori compresi tra -1 e 1
 */
public class Direction extends Point {
    public Direction(double x, double y) {
        super(validateParameters(x), validateParameters(y));
    }

    public Direction() {
        super();
    }

    /**
     * Metodo che controlla che i parametri siano compresi tra -1 e 1, dichiarato come statico
     * così da poterlo richiamare all'interno del costruttore
     * @param param il parametro da validare
     * @return il parametro passato se non ci sono problemi
     */
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
}
