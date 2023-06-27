package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe che si occupa di convertire uno <code>ShapeData</code>
 * in uno <code>IShape</code> ed utilizza l'interfaccia
 * <code>IShapeFactory</code> per farlo
 */
public class ShapeParser {
    private Map<String, IShapeFactory> factoryMap;

    public ShapeParser() {
        factoryMap = new HashMap<>();
        factoryMap.put("CIRCLE", new CircularShapeFactory());
        factoryMap.put("RECTANGLE", new RectangularShapeFactory());
    }

    /**
     * Metodo che prende in ingresso uno {@code ShapeData} e mi crea un
     * {@code IShape}
     * @param shapeData lo shapeData da convertire
     * @return un oggetto di tipo {@code IShape}
     */
    public IShape parseFromShapeData(ShapeData shapeData) {
        String shapeType = shapeData.shape();
        if (!factoryMap.containsKey(shapeType)) {
            throw new IllegalArgumentException("Invalid shape: " + shapeType);
        }
        IShapeFactory factory = factoryMap.get(shapeType);
        return factory.createShape(shapeData);
    }
}
