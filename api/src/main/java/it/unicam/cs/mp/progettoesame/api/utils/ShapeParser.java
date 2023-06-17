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

    public IShape parseFromShapeData(ShapeData shapeData) {
        String shapeLabel = shapeData.label();
        if (!factoryMap.containsKey(shapeLabel)) {
            throw new IllegalArgumentException("Invalid shape label: " + shapeLabel);
        }
        IShapeFactory factory = factoryMap.get(shapeLabel);
        return factory.createShape(shapeData);
    }
}
