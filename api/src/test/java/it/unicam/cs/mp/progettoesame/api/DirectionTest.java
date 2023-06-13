package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test void testThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Direction(5, 6);
        });
    }
}
