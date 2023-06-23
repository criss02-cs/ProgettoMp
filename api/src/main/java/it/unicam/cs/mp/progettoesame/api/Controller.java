package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;

import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Controller {
    private List<Robot> robots;
    private List<IShape> shapes;
    private final FollowMeParser parser;
    public Controller(List<Robot> robots, List<IShape> shapes) {
        this.robots = robots;
        this.shapes = shapes;
        final FollowMeParserHandler handler = new ParserHandler(this.robots, this.shapes);
        this.parser = new FollowMeParser(handler);
    }

    public void loadRobots(List<Robot> robots) { this.robots = robots; }
    public void loadShapes(List<IShape> shapes) { this.shapes = shapes; }

    /**
     * Metodo che si occupa di leggere il programma dei robot e di passarlo al
     * parser
     * @param programFile file da cui leggere
     * @return lista di stringhe contenente tutto il programma
     * @throws IOException se c'è qualche errore nella lettura
     * @throws FollowMeParserException se c'è qualche errore nel programma
     */
    public List<String> readInstructionList(File programFile) throws IOException, FollowMeParserException {
        List<String> lines = Files.readAllLines(programFile.toPath());
        this.parser.parseRobotProgram(programFile);
        return lines;
    }

    public void loadProgramToRobots(Program program) {
        this.robots.forEach(x -> x.loadProgramToExecute(program));
    }

    public void nextInstruction() {
        this.robots.forEach(x -> {
            if(!x.isProgramTerminated()) {
                x.executeNextInstruction();
            }
        });
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<IShape> getShapes() {
        return shapes;
    }

}
