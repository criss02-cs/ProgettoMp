package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;

/**
 * Classe che rappresenta la Direzione, utilizza un attributo di tipo <code>Point</code>
 * però si assicura che i valori siano compresi tra -1 e 1
 */
public class Direction {
    private final Point point;
    public Direction(double x, double y) {
        this.validateParameters(x,y);
        this.point = new Point(x, y);
    }

    public Direction() {
        this.point = new Point();
    }

    /**
     * Metodo che controlla che i parametri siano compresi tra -1 e 1, dichiarato come statico
     * così da poterlo richiamare all'interno del costruttore
     * @param x la coordinata x della direzione
     * @param y la coordinata y della direzione
     */
    private void validateParameters(double x, double y) {
        if(!NumericRangeChecker.DEFAULT_CHECKER.isBetween(x, -1.0,1.0)
            || !NumericRangeChecker.DEFAULT_CHECKER.isBetween(y, -1.0,1.0)) {
            throw new IllegalArgumentException("Il valore deve essere compreso tra -1 e 1");
        }
    }

    /**
     * Metodo che imposta la x della direzione,
     * anche qui avviene il controllo sul valore passato
     * @param x la coordinata da impostare
     */
    public void setX(double x) {
        this.validateParameters(x, this.point.getY());
        this.point.setX(x);
    }

    /**
     * Metodo che imposta la y della direzione,
     * anche qui avviene il controllo sul valore passato
     * @param y la coordinata da impostare
     */
    public void setY(double y) {
        this.validateParameters(this.point.getX(), y);
        this.point.setY(y);
    }

    /**
     * Metodo che ritorna la coordinata x della direzione
     * @return la coordinata x della direzione
     */
    public double getX(){
        return this.point.getX();
    }
    /**
     * Metodo che ritorna la coordinata y della direzione
     * @return la coordinata y della direzione
     */
    public double getY(){
        return this.point.getY();
    }
}
