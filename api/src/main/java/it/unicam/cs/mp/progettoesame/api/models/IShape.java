package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

/**
 * Interfaccia che definisce una figura nello spazio
 */
public interface IShape {
    /**
     * Metodo che restituisce le coordinate della figura
     * @return le coordinate della figura
     */
    Point getCoordinates();

    /**
     * Metodo che restituisce le dimensioni della figura
     * @return le dimensioni della figura
     */
    Tuple<Double, Double> getDimensions();

    /**
     * Metodo che restituisce la label della figura
     * @return la label della figura
     */
    String getLabel();
}
