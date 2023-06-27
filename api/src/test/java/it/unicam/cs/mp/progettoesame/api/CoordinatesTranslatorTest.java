package it.unicam.cs.mp.progettoesame.api;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.utils.CoordinatesTranslator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CoordinatesTranslatorTest {
    @Test
    void testTranslateToScreenCoordinates() {
        double height = 100.0;
        double width = 200.0;
        CoordinatesTranslator translator = new CoordinatesTranslator(height, width);

        // Coordinate cartesiane positive
        Point cartesianPoint1 = new Point(3, 4);
        Point result1 = translator.translateToScreenCoordinates(cartesianPoint1);
        assertEquals(103.0, result1.getX(), 0.001);
        assertEquals(46.0, result1.getY(), 0.001);

        // Coordinate cartesiane negative
        Point cartesianPoint2 = new Point(-2, -5);
        Point result2 = translator.translateToScreenCoordinates(cartesianPoint2);
        assertEquals(98.0, result2.getX(), 0.001);
        assertEquals(55.0, result2.getY(), 0.001);

        // Coordinate cartesiane a 0
        Point cartesianPoint3 = new Point(0, 0);
        Point result3 = translator.translateToScreenCoordinates(cartesianPoint3);
        assertEquals(100.0, result3.getX(), 0.001);
        assertEquals(50.0, result3.getY(), 0.001);

        // Coordinate non intere
        Point cartesianPoint4 = new Point(2.5, -3.5);
        Point result4 = translator.translateToScreenCoordinates(cartesianPoint4);
        assertEquals(102.5, result4.getX(), 0.001);
        assertEquals(53.5, result4.getY(), 0.001);
    }
}
