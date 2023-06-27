package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.utils.DistanceCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class DistanceCalculatorTest {

    @Test
    void testFindCoordinatesDifference() {
        double difference = DistanceCalculator.findCoordinatesDifference(5,10);
        assertEquals(5, difference);
        assertNotEquals(10, DistanceCalculator.findCoordinatesDifference(5.0, 11.0));
    }

    @Test
    void testCalculateDistance() {
        double distance = DistanceCalculator.calculate(new Point(1,2), new Point(3,4));
        assertEquals(2* Math.sqrt(2), distance);
        distance = DistanceCalculator.calculate(new Point(1,-2), new Point(2,2));
        assertEquals(Math.sqrt(17), distance);
        distance = DistanceCalculator.calculate(new Point(0,0), new Point(5,12));
        assertEquals(13, distance);
        distance = DistanceCalculator.calculate(new Point(-1,-3), new Point(5,-3));
        assertEquals(6, distance);
    }
}
