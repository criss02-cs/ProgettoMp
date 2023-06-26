package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.Point;

/**
 * Classe che si occupa di traslare un punto di un piano cartesiano
 * in un punto all'interno di uno schermo di un PC (con le y invertite)
 */
public class CoordinatesTranslator {
    /**
     * Altezza del contenitore
     */
    private final double height;
    /**
     * Larghezza del contenitore
     */
    private final double width;

    public CoordinatesTranslator(double height, double width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Metodo che trasla il punto passato
     * @param cartesianCoordinates il punto in un piano cartesiano da traslare
     * @return il punto che sta in un piano di un pc
     */
    public Point translateToScreenCoordinates(Point cartesianCoordinates) {
        double x = cartesianCoordinates.getX() + (this.width / 2);
        double y = (this.height / 2) - cartesianCoordinates.getY();
        return new Point(x, y);
    }
}
