package it.unicam.cs.mp.progettoesame.api;
import it.unicam.cs.mp.progettoesame.api.models.CircularShape;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.RectangularShape;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ShapeTest {

    @Test
    void testRectangularShapeGetDimensions() {
        double width = 5.0;
        double height = 10.0;
        Point position = new Point(0,0);
        String label = "Rectangle";

        IShape rectangularShape = new RectangularShape(width, height, position, label);

        Tuple<Double, Double> dimensions = rectangularShape.getDimensions();
        assertEquals(5.0, dimensions.getItem1());
        assertEquals(10.0, dimensions.getItem2());
    }

    @Test
    void testShapeGetCoordinates() {
        double width = 5.0;
        double height = 10.0;
        Point position = new Point(0,0);
        String label = "Rectangle";
        IShape rectangularShape = new RectangularShape(width, height, position, label);
        Point coordinates = rectangularShape.getCoordinates();
        assertEquals(0.0, coordinates.getX());
        assertEquals(0.0, coordinates.getY());
    }

    @Test
    void testCircularShapeGetDimensions() {
        double radius = 5.0;
        Point position = new Point(0,0);
        String label = "Circle";
        IShape circularShape = new CircularShape(radius, position, label);
        assertEquals(-1, circularShape.getDimensions().getItem2());
        assertEquals(5.0, circularShape.getDimensions().getItem1());
    }
}
