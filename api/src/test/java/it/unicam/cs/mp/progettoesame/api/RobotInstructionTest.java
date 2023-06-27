package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.*;
import it.unicam.cs.mp.progettoesame.api.models.instructions.*;
import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RobotInstructionTest {
    @Test
    void testCloneObject() {
        RobotInstruction ri = new StopInstruction();
        RobotInstruction r1 = ri.cloneObject();
        assertNotEquals(r1, ri);
    }

    @Test
    void testContinueInstruction() {
        RobotInstruction continueInstruction = new ContinueInstruction(5, 0);
        assertEquals(0, continueInstruction.canGoToNextInstruction());
        for (int i = 0; i < 5; i++) {
            continueInstruction.execute(new Robot());
        }
        assertEquals(-1, continueInstruction.canGoToNextInstruction());
    }

    @Test
    void testDoForeverInstruction() {
        RobotInstruction doForever = new DoForeverInstruction(1);
        assertEquals(1, doForever.canGoToNextInstruction());
        doForever.execute(new Robot());
        assertEquals(1, doForever.canGoToNextInstruction());
    }

    @Test
    void testDoneInstruction() {
        RobotInstruction done = new DoneInstruction(1);
        assertEquals(1, done.canGoToNextInstruction());
        done.execute(new Robot());
        assertEquals(1, done.canGoToNextInstruction());
    }

    @Test
    void followInstruction() {
        Robot robot = new Robot(new Point(0, 0));
        List<Robot> robots = new ArrayList<>();
        Robot robot1 = new Robot(new Point(50, 50));
        Robot robot2 = new Robot(new Point(-100, -100));
        robots.add(robot);
        robots.add(robot1);
        robots.add(robot2);

        RobotInstruction follow = new FollowInstruction("pippo", 80, 25, robots);
        robot1.signalLabel("pippo");
        robot2.signalLabel("pippo");
        follow.execute(robot);

        assertTrue(robot.getPosition().getX() < 25);
        assertTrue(robot.getPosition().getY() < 25);
        assertEquals(-1, follow.canGoToNextInstruction());
    }

    @Test
    void followInstruction2() {
        Robot robot = new Robot(new Point(0, 0));
        List<Robot> robots = new ArrayList<>();
        Robot robot1 = new Robot(new Point(100, 100));
        Robot robot2 = new Robot(new Point(-100, -100));
        robots.add(robot);
        robots.add(robot1);
        robots.add(robot2);

        RobotInstruction follow = new FollowInstruction("pippo", 80, 25, robots);
        robot1.signalLabel("pippo");
        robot2.signalLabel("pippo");
        follow.execute(robot);

        assertTrue(robot.getPosition().getX() < 80);
        assertTrue(robot.getPosition().getY() < 80);
        assertEquals(-1, follow.canGoToNextInstruction());
    }

    @Test
    void moveInstruction() {
        Robot r = new Robot();
        RobotInstruction move = new MoveInstruction(new Direction(1, 1), 25);
        move.execute(r);
        assertEquals(25, r.getPosition().getY());
        assertEquals(25, r.getPosition().getX());
        assertEquals(-1, move.canGoToNextInstruction());
        move = new MoveInstruction(new Direction(-1, 1), 50);
        move.execute(r);
        assertEquals(-25, r.getPosition().getX());
        assertEquals(75, r.getPosition().getY());
    }

    @Test
    void moveRandomInstruction() {
        Robot r = new Robot();
        RobotInstruction moveRandom = new MoveRandomInstruction(new Point(0, 0), new Point(10, 10), 25);
        moveRandom.execute(r);
        assertTrue(NumericRangeChecker.DEFAULT_CHECKER.isBetween(r.getPosition().getX(), 0.0, 10.0));
        assertTrue(NumericRangeChecker.DEFAULT_CHECKER.isBetween(r.getPosition().getY(), 0.0, 10.0));
    }

    @Test
    void repeatInstruction() {
        Robot r = new Robot();
        RobotInstruction repeat = new RepeatInstruction(5, 1);
        assertEquals(1, repeat.canGoToNextInstruction());
        repeat.execute(r);
        assertEquals(1, repeat.canGoToNextInstruction());
        for(int i = 0; i < 4; i++) repeat.execute(r);
        assertNotEquals(1, repeat.canGoToNextInstruction());
    }

    @Test
    void signalInstruction() {
        Robot r = new Robot();
        RobotInstruction signal = new SignalInstruction("pippo");
        signal.execute(r);
        assertEquals("pippo", r.getSignaledLabel());
    }

    @Test
    void unsignalInstruction() {
        Robot r = new Robot();
        RobotInstruction unsignal = new UnsignalInstruction("pippo");
        r.signalLabel("pippo");
        unsignal.execute(r);
        assertEquals("", r.getSignaledLabel());
        unsignal = new UnsignalInstruction("pippo2");
        r.signalLabel("pippo");
        RobotInstruction finalUnsignal = unsignal;
        assertThrows(IllegalArgumentException.class, () -> finalUnsignal.execute(r));
    }

    @Test
    void stopInstruction() {
        Robot r = new Robot();
        RobotInstruction stop = new StopInstruction();
        r.move(25, new Direction(1,1));
        stop.execute(r);
        r.continueMove();
        assertEquals(25, r.getPosition().getX());
        assertEquals(25, r.getPosition().getY());
    }

    @Test
    void untilInstruction() {
        // Inside Circular
        Robot r = new Robot();
        List<IShape> shapes = new LinkedList<>();
        IShape shape = new CircularShape(25, new Point(0,0), "pippo");
        shapes.add(shape);
        RobotInstruction until = new UntilInstruction("pippo", shapes, 1);
        until.execute(r);
        assertNotEquals(1, until.canGoToNextInstruction());

        //Outside Circular
        Robot r2 = new Robot(new Point(30,30));
        List<IShape> shapes2 = new LinkedList<>();
        IShape shape2 = new CircularShape(25, new Point(0,0), "pippo");
        shapes2.add(shape2);
        RobotInstruction until2 = new UntilInstruction("pippo", shapes2, 1);
        until2.execute(r2);
        assertEquals(1, until2.canGoToNextInstruction());

        // Inside Rectangular
        Robot r3 = new Robot();
        List<IShape> shapes3 = new LinkedList<>();
        IShape shape3 = new RectangularShape(25, 25, new Point(0,0), "pippo");
        shapes3.add(shape3);
        RobotInstruction until3 = new UntilInstruction("pippo", shapes3, 1);
        until3.execute(r3);
        assertNotEquals(1, until3.canGoToNextInstruction());

        //Outside Rectangular
        Robot r4 = new Robot(new Point(45,45));
        List<IShape> shapes4 = new LinkedList<>();
        IShape shape4 = new RectangularShape(25, 25, new Point(0,0), "pippo");
        shapes4.add(shape4);
        RobotInstruction until4 = new UntilInstruction("pippo", shapes4, 1);
        until4.execute(r4);
        assertEquals(1, until4.canGoToNextInstruction());
    }
}
