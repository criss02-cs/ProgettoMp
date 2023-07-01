package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import it.unicam.cs.mp.progettoesame.api.exceptions.RobotsNotLoadedException;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.RandomCoordinatesCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.ShapeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe che gestisce il flusso di esecuzione di tutti i robot
 */
public class Controller {
    private final List<Robot> robots;
    private final List<IShape> shapes;
    private final FollowMeParser parser;
    public Controller(List<Robot> robots, List<IShape> shapes) {
        this.robots = robots;
        this.shapes = shapes;
        final FollowMeParserHandler handler = new ParserHandler(this.robots, this.shapes);
        this.parser = new FollowMeParser(handler);
        try {
            Console.flushFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller() {
        this(new LinkedList<>(), new LinkedList<>());
    }

    /**
     * Metodo che si occupa di leggere il programma dei robot e di passarlo al
     * parser
     * @param programFile file da cui leggere
     * @return lista di stringhe contenente tutto il programma
     * @throws IOException se c'è qualche errore nella lettura del file
     * @throws FollowMeParserException se c'è qualche errore di sintassi nel programma
     */
    public List<String> readInstructionList(File programFile) throws IOException, FollowMeParserException, RobotsNotLoadedException {
        if(this.robots.isEmpty()) {
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

    /**
     * Metood che genera un numero n di robot in maniera randomica
     * @param robotsNumber il numero di robot da creare
     * @param minPoint il punto minimo in cui un robot può apparire
     * @param maxPoint il punto massimo in cui un robot può apparire
     */
    public void generateRandomRobots(int robotsNumber, Point minPoint, Point maxPoint) {
        for(int i = 0; i < robotsNumber; i++) {
            Point position = RandomCoordinatesCalculator.calculate(minPoint, maxPoint);
            this.robots.add(new Robot(position));
        }
    }

    /**
     * Metodo che esegue la prossima istruzione su tutti i robot
     * @throws IllegalArgumentException se c'è qualche errore nell'esecuzione
     */
    public void nextInstruction() throws IllegalArgumentException {
        for(Robot r : this.robots) {
            if(!r.isProgramTerminated()) {
                r.executeNextInstruction();
            }
        }
    }

    /**
     * Metodo che verifica che tutti i robot abbiano finito i propri programmi
     * @return true se tutti i robot hanno finito, false altrimenti
     */
    public boolean isAllRobotFinished() {
        return this.robots.stream().filter(Robot::isProgramTerminated).count() ==
                this.robots.size();
    }

    /**
     * Metodo che restituisce la lista di robot
     * @return la lista di robot
     */
    public List<Robot> getRobots() {
        return robots;
    }

    /**
     * Metodo che restituisce la lista delle aree
     * @return
     */
    public List<IShape> getShapes() {
        return shapes;
    }

}
