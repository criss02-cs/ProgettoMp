package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.exceptions.RobotsNotLoadedException;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.ShapeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

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
     * @throws IOException se c'è qualche errore nella lettura del file
     * @throws FollowMeParserException se c'è qualche errore di sintassi nel programma
     */
    public List<String> readInstructionList(File programFile) throws IOException, FollowMeParserException, RobotsNotLoadedException {
        if(this.robots.size() == 0) {
            throw new RobotsNotLoadedException("Prima di caricare il programma devi caricare la lista di robot");
        }
        List<String> lines = Files.readAllLines(programFile.toPath());
        this.parser.parseRobotProgram(programFile);
        return lines;
    }

    /**
     * Metodo che legge il file in cui sono contenute tutte le figure,
     * e le converte in una lista di <code>IShape</code>
     * @param shapeListFile il file in cui sono contenute le figure
     * @throws IOException se c'è qualche errore nella lettura del file
     * @throws FollowMeParserException se c'è qualche errore di sintassi nella dichiarazione delle figure
     */
    public void readShapeList(File shapeListFile) throws IOException, FollowMeParserException {
        List<ShapeData> shapeDataList = this.parser.parseEnvironment(shapeListFile);
        List<IShape> shapeList = shapeDataList.stream().map(new ShapeParser()::parseFromShapeData).toList();
        this.shapes.addAll(shapeList);
    }

    /**
     * Metodo che legge il file in cui sono contenuti tutti i robot da inserire
     * @param robotListFile il file in cui sono contenuti i robot
     * @throws IOException se c'è qualche errore nella lettura del file
     */
    public void readRobotList(File robotListFile) throws IOException {
        List<String> lines = Files.readAllLines(robotListFile.toPath());
        for(String line : lines) {
            String[] elements = line.trim().toUpperCase().split(" ");
            this.robots.add(new Robot(new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]))));
        }
    }

    public void generateRandomRobots(int robotsNumber, Point minPoint, Point maxPoint) {
        for(int i = 0; i < robotsNumber; i++) {

        }
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
