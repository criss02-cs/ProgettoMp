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

        // Test case 1: Positive cartesian coordinates
        Point cartesianPoint1 = new Point(3, 4);
        Point result1 = translator.translateToScreenCoordinates(cartesianPoint1);
        assertEquals(103.0, result1.getX(), 0.001);
        assertEquals(48.0, result1.getY(), 0.001);

        // Test case 2: Negative cartesian coordinates
        Point cartesianPoint2 = new Point(-2, -5);
        Point result2 = translator.translateToScreenCoordinates(cartesianPoint2);
        assertEquals(98.0, result2.getX(), 0.001);
        assertEquals(57.0, result2.getY(), 0.001);

        // Test case 3: Zero cartesian coordinates
        Point cartesianPoint3 = new Point(0, 0);
        Point result3 = translator.translateToScreenCoordinates(cartesianPoint3);
        assertEquals(100.0, result3.getX(), 0.001);
        assertEquals(50.0, result3.getY(), 0.001);

        // Test case 4: Non-integer coordinates
        Point cartesianPoint4 = new Point(2.5, -3.5);
        Point result4 = translator.translateToScreenCoordinates(cartesianPoint4);
        assertEquals(101.25, result4.getX(), 0.001);
        assertEquals(52.75, result4.getY(), 0.001);
    }
}
