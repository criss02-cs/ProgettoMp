package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.CircularShape;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.RectangularShape;
import it.unicam.cs.mp.progettoesame.api.utils.ShapeParser;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ShapeParserTest {
    @Test
    void testParseFromShapeDataThrows(){
        ShapeData shapeData = new ShapeData("pippo", "TRIANGLE", new double[] { 5,5,5});
        ShapeParser parser = new ShapeParser();
        ShapeData finalShapeData = shapeData;
        assertThrows(IllegalArgumentException.class, () -> parser.parseFromShapeData(finalShapeData));
        shapeData = new ShapeData("pippo", "RECTANGLE", new double[]{5,5,5,5});
        ShapeData finalShapeData1 = shapeData;
        assertDoesNotThrow(() -> parser.parseFromShapeData(finalShapeData1));
    }

    @Test
    void testParseFromShapeData() {
        ShapeData shapeData = new ShapeData("pippo", "RECTANGLE", new double[]{5,5,5,5});
        ShapeParser parser = new ShapeParser();
        IShape shape = parser.parseFromShapeData(shapeData);
        assertEquals(shapeData.label(), shape.getLabel());
        assertInstanceOf(RectangularShape.class, shape);
        ShapeData shapeData1 = new ShapeData("pippo", "CIRCLE", new double[]{5,5,5});
        IShape shape1 = parser.parseFromShapeData(shapeData1);
        assertEquals(shapeData.label(), shape1.getLabel());
        assertInstanceOf(CircularShape.class, shape1);
    }
}
