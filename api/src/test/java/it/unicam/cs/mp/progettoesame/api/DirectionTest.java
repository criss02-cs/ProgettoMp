package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test void testThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Direction(5, 6);
        });
        assertDoesNotThrow(() -> new Direction(1,1));
    }

    @Test
    void testSetCoordinates(){
        Direction dir = new Direction();
        dir.setX(-1);
        assertEquals(-1.0, dir.getX());
        dir.setY(-0.5);
        assertEquals(-0.5, dir.getY());
        assertThrows(IllegalArgumentException.class, () -> dir.setY(-3));
        assertThrows(IllegalArgumentException.class, () -> dir.setX(-3));
        assertDoesNotThrow(() -> dir.setY(1));
        assertDoesNotThrow(() -> dir.setX(0.5));
    }
}
