package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.utils.DirectionCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DirectionCalculatorTest {
    @Test
    public void testCalculate() {
        // Punti validi
        Point source = new Point(1, 1);
        Point destination = new Point(4, 5);

        Direction result = DirectionCalculator.calculate(source, destination);

        assertEquals(0.6, result.getX(), 0.001);
        assertEquals(0.8, result.getY(), 0.001);

        // Movimento orizzontale
        Point source2 = new Point(2,3);
        Point destination2 = new Point(7,3);
        Direction result2 = DirectionCalculator.calculate(source2, destination2);
        assertEquals(1.0, result2.getX(), 0.001);
        assertEquals(0.0, result2.getY(), 0.001);

        //Movimento verticale
        Point source3 = new Point(-1, 4);
        Point destination3 = new Point(-1, -2);
        Direction result3 = DirectionCalculator.calculate(source3, destination3);
        assertEquals(0.0, result3.getX(), 0.001);
        assertEquals(-1.0, result3.getY(), 0.001);

        //Stessa source e stessa destinazione
        Point source4 = new Point(0, 0);
        Point destination4 = new Point(0, 0);
        Direction result4 = DirectionCalculator.calculate(source4, destination4);
        assertEquals(0.0, result4.getX(), 0.001);
        assertEquals(0.0, result4.getY(), 0.001);

        //Coordinate negative
        Point source5 = new Point(-3, -2);
        Point destination5 = new Point(-7, -5);
        Direction result5 = DirectionCalculator.calculate(source5, destination5);
        assertEquals(-0.8, result5.getX(), 0.001);
        assertEquals(-0.6, result5.getY(), 0.001);
    }
}
