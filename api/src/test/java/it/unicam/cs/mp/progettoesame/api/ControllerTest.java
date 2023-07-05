package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.exceptions.RobotsNotLoadedException;
import it.unicam.cs.mp.progettoesame.api.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import it.unicam.cs.mp.progettoesame.api.models.instructions.MoveInstruction;
import it.unicam.cs.mp.progettoesame.api.models.instructions.SignalInstruction;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
    }

    @Test
    void readInstructionList_RobotsNotLoadedException() throws IOException {
        File programFile = createTempFileWithContent("MOVE_FORWARD 1", "MOVE_BACKWARD 2");
        assertThrows(RobotsNotLoadedException.class, () -> controller.readInstructionList(programFile));
    }

    @Test
    void readInstructionList_Success() throws IOException, RobotsNotLoadedException, FollowMeParserException {
        File programFile = createTempFileWithContent("MOVE 1 1 5", "SIGNAL label");
        controller.readRobotList(createTempFileWithContent("1 5 5", "2 10 10"));
        List<String> lines = controller.readInstructionList(programFile);
        assertEquals(2, lines.size());
        assertEquals("MOVE 1 1 5", lines.get(0));
        assertEquals("SIGNAL label", lines.get(1));
    }

    @Test
    void readShapeList_Success() throws IOException, FollowMeParserException {
        File shapeListFile = createTempFileWithContent("label RECTANGLE 1.0 2.0 3.0 4.0", "label CIRCLE 5.0 6.0 7.0");
        controller.readShapeList(shapeListFile);
        List<IShape> shapes = controller.getShapes();
        assertEquals(2, shapes.size());
        assertTrue(shapes.get(0) instanceof RectangularShape);
        assertTrue(shapes.get(1) instanceof CircularShape);
    }

    @Test
    void readRobotList_Success() throws IOException {
        File robotListFile = createTempFileWithContent("1 1.0 2.0", "2 3.0 4.0");
        controller.readRobotList(robotListFile);
        List<Robot> robots = controller.getRobots();
        assertEquals(2, robots.size());
        assertEquals(new Point(1.0, 2.0), robots.get(0).getPosition());
        assertEquals(new Point(3.0, 4.0), robots.get(1).getPosition());
    }

    @Test
    void generateRandomRobots_Success() {
        Point minPoint = new Point(0.0, 0.0);
        Point maxPoint = new Point(10.0, 10.0);
        controller.generateRandomRobots(3, minPoint, maxPoint);
        List<Robot> robots = controller.getRobots();
        assertEquals(3, robots.size());
        for (Robot robot : robots) {
            assertTrue(robot.getPosition().getX() >= minPoint.getX());
            assertTrue(robot.getPosition().getX() <= maxPoint.getX());
            assertTrue(robot.getPosition().getY() >= minPoint.getY());
            assertTrue(robot.getPosition().getY() <= maxPoint.getY());
        }
    }

    @Test
    void nextInstruction_Success() {
        List<Robot> robots = new ArrayList<>();
        Robot robot1 = new Robot(new Point(1.0, 2.0));
        Robot robot2 = new Robot(new Point(3.0, 4.0));
        robots.add(robot1);
        robots.add(robot2);
        controller = new Controller(robots, new ArrayList<>());
        Program p = new Program();
        p.addInstruction(new MoveInstruction(new Direction(1,1), 5));
        p.addInstruction(new SignalInstruction("pippo"));
        robot1.loadProgramToExecute(Program.copyOf(p));
        robot2.loadProgramToExecute(Program.copyOf(p));
        controller.nextInstruction();
        assertEquals(new Point(6.0, 7.0), robot1.getPosition());
        assertEquals(new Point(8.0, 9.0), robot2.getPosition());
    }

    @Test
    void isAllRobotFinished_AllFinished() {
        List<Robot> robots = new ArrayList<>();
        Robot robot1 = new Robot(new Point(1.0, 2.0));
        Robot robot2 = new Robot(new Point(3.0, 4.0));
        robots.add(robot1);
        robots.add(robot2);
        controller = new Controller(robots, new ArrayList<>());
        Program p = new Program();
        p.addInstruction(new MoveInstruction(new Direction(1,1), 5));
        robot1.loadProgramToExecute(Program.copyOf(p));
        robot2.loadProgramToExecute(Program.copyOf(p));
        controller.nextInstruction();
        controller.nextInstruction();
        assertTrue(controller.isAllRobotFinished());
    }

    @Test
    void isAllRobotFinished_NotAllFinished() {
        List<Robot> robots = new ArrayList<>();
        Robot robot1 = new Robot(new Point(1.0, 2.0));
        Robot robot2 = new Robot(new Point(3.0, 4.0));
        robots.add(robot1);
        robots.add(robot2);
        controller = new Controller(robots, new ArrayList<>());
        Program p = new Program();
        p.addInstruction(new MoveInstruction(new Direction(1,1), 5));
        p.addInstruction(new SignalInstruction("pippo"));
        robot1.loadProgramToExecute(Program.copyOf(p));
        robot2.loadProgramToExecute(Program.copyOf(p));
        controller.nextInstruction();
        assertFalse(controller.isAllRobotFinished());
    }

    private File createTempFileWithContent(String... lines) throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        Files.write(tempFile, String.join("\n", lines).getBytes());
        return tempFile.toFile();
    }
}