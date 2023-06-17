package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

/**
 * Interfaccia che permette di creare un <code>IShape</code>
 */
public interface IShapeFactory {
    /**
     * Metodo per creare l'oggetto <code>IShape</code> a partire
     * da un oggetto <code>ShapeData</code>
     * @param shapeData l'oggetto da convertire in <code>IShape</code>
     * @return l'oggetto convertito
     */
    IShape createShape(ShapeData shapeData);
}
