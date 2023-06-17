package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.RectangularShape;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

/**
 * Classe che implementa la creazione di una forma rettangolare
 */
public class RectangularShapeFactory implements IShapeFactory{
    @Override
    public IShape createShape(ShapeData shapeData) {
        double x = shapeData.args()[0];
        double y = shapeData.args()[1];
        double width = shapeData.args()[2];
        double height = shapeData.args()[3];
        return new RectangularShape(Tuple.of(width, height), new Point(x, y), shapeData.label());
    }
}
