package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.CircularShape;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.RectangularShape;
import it.unicam.cs.mp.progettoesame.api.utils.CircularShapeFactory;
import it.unicam.cs.mp.progettoesame.api.utils.IShapeFactory;
import it.unicam.cs.mp.progettoesame.api.utils.RectangularShapeFactory;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ShapeFactoryTest {
    @Test
    void testShapeFactoryCreation() {
        IShapeFactory shapeFactory = new RectangularShapeFactory();
        assertInstanceOf(RectangularShapeFactory.class, shapeFactory);
        IShapeFactory circle = new CircularShapeFactory();
        assertInstanceOf(CircularShapeFactory.class, circle);
    }

    @Test
    void testCreateShape(){
        IShapeFactory shapeFactory = new RectangularShapeFactory();
        IShape shape = shapeFactory.createShape(new ShapeData("pippo", "RECTANGLE", new double[] { 5,5,5,5 }));
        assertInstanceOf(RectangularShape.class, shape);
        assertEquals(5, shape.getDimensions().getItem1());
        assertEquals(5, shape.getDimensions().getItem2());
        assertEquals(5, shape.getCoordinates().getX());
        IShapeFactory shapeFactory1 = new CircularShapeFactory();
        IShape shape1 = shapeFactory1.createShape(new ShapeData("pippo", "CIRCLE", new double[] { 5,5,5}));
        assertInstanceOf(CircularShape.class, shape1);
        assertEquals(5, shape1.getDimensions().getItem1());
        assertEquals(-1, shape1.getDimensions().getItem2());
        assertEquals(5, shape1.getCoordinates().getX());
    }
}
