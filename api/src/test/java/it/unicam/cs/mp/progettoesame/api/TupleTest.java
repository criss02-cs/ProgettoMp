package it.unicam.cs.mp.progettoesame.api;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class TupleTest {
    @Test
    void testCreateTupleDoubleInteger() {
        Tuple<Double, Integer> tuple = new Tuple<>(5.0, 5);
        assertInstanceOf(Double.class, tuple.getItem1());
        assertInstanceOf(Integer.class, tuple.getItem2());
        assertEquals(5.0, tuple.getItem1());
        assertEquals(5, tuple.getItem2());
    }

    @Test
    void testTupleOf() {
        Tuple<Double, Integer> tuple = new Tuple<>(5.0, 5);
        Tuple<Double, Integer> tuple1 = Tuple.of(5.0,5);
        assertEquals(tuple1, tuple);
    }
}
