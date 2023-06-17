package it.unicam.cs.mp.progettoesame.api.utils;

import it.unicam.cs.mp.progettoesame.api.models.CircularShape;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

public class CircularShapeFactory implements IShapeFactory{
    @Override
    public IShape createShape(ShapeData shapeData) {
        double x = shapeData.args()[0];
        double y = shapeData.args()[1];
        double radius = shapeData.args()[2];
        return new CircularShape(radius, new Point(x, y), shapeData.label());
    }
}
