package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.models.instructions.ContinueInstruction;
import it.unicam.cs.mp.progettoesame.api.models.instructions.DoForeverInstruction;
import it.unicam.cs.mp.progettoesame.api.models.instructions.DoneInstruction;
import it.unicam.cs.mp.progettoesame.api.models.instructions.MoveInstruction;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    @Test
    void addInstruction() {
        Program p = new Program();
        assertDoesNotThrow(() -> p.addInstruction(new DoForeverInstruction(0)));
        assertThrows(IllegalArgumentException.class, () -> p.addInstruction(null));
    }

    @Test
    void executeInstruction() throws IOException {
        Program p = new Program();
        p.addInstruction(new DoForeverInstruction(1));
        p.addInstruction(new ContinueInstruction(5,1));
        p.addInstruction(new DoneInstruction(0));
        assertThrows(IllegalArgumentException.class, () -> p.executeInstruction(null));
        assertTrue(p.executeInstruction(new Robot(new Point(5, 5))));
        Program p1 = new Program();
        p1.addInstruction(new MoveInstruction(new Direction(1,1), 25));
        p1.executeInstruction(new Robot(new Point(5, 5)));
        assertFalse(p1.executeInstruction(new Robot(new Point(5, 5))));
    }

    @Test
    void copyOf() {
        Program p = new Program();
        Program copy = Program.copyOf(p);
        assertNotEquals(copy, p);
    }
}