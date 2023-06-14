package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumericRangeCheckerTest {

    @Test void isBetweenDoubleTest(){
        NumericRangeChecker<Double> doubleChecker = NumericRangeChecker.DEFAULT_CHECKER;
        assertEquals(doubleChecker.isBetween(5.0, 2.0,3.0), false);
        assertEquals(doubleChecker.isBetween(5.0, 2.0,6.0), true);
        assertEquals(doubleChecker.isBetween(5.0, 2.0,5.0), true);
        assertEquals(doubleChecker.isBetween(5.0, 2.0,4.0), false);
        assertEquals(doubleChecker.isBetween(3.14, 2.0,4.0), true);
        assertEquals(doubleChecker.isBetween(Math.PI, 2.0,4.0), true);
        assertEquals(doubleChecker.isBetween(5.01, 2.0,5.0), false);
    }

    @Test void isBetweenIntegerTest() {
        NumericRangeChecker<Integer> integerChecker = (value, minValue, maxValue) ->
                value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0;
        assertEquals(integerChecker.isBetween(5, 2,3), false);
        assertEquals(integerChecker.isBetween(5, 2,6), true);
        assertEquals(integerChecker.isBetween(5, 2,5), true);
        assertEquals(integerChecker.isBetween(5, 2,4), false);
    }

    @Test void isBetweenThrowIllegalArgument() {
        NumericRangeChecker<Double> doubleChecker = NumericRangeChecker.DEFAULT_CHECKER;
        assertThrows(IllegalArgumentException.class, () -> doubleChecker.isBetween(5.0, 6.0, 2.0));
        assertDoesNotThrow(() -> doubleChecker.isBetween(5.0, 2.0,6.0));
    }
}
