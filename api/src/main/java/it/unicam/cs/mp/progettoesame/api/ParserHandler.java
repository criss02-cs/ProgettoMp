package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.models.instructions.*;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;

import java.util.*;

/**
 * Classe che effettua il parsing dei comandi e crea il programma da eseguire
 */
public class ParserHandler implements FollowMeParserHandler {
    /**
     * Interfaccia funzionale per controllare che un numero sia in un range specifico
     */
    private NumericRangeChecker<Double> checker;
    /**
     * Programma che i robot devono eseguire
     */
    private Program program;
    /**
     * Lista dei robot presenti
     */
    private List<Robot> robots;
    /**
     * Lista delle figure presenti
     */
    private List<IShape> shapes;
    /**
     * Pila di interi che conterrà l'indice di riga di ogni istruzione
     * iterativa
     */
    private Stack<Integer> stack;
    /**
     * Mappa per salvare l'istruzione iterativa e il suo indice di riga
     */
    private Map<Integer, IterativeInstruction> dictionary;
    /**
     * Contatore per tenere conto dell'indice di riga
     */
    private int instructionCounter;

    public ParserHandler(List<Robot> robots, List<IShape> shapes) {
        this.program = new Program();
        this.robots = robots;
        this.shapes = shapes;
    }

    @Override
    public void parsingStarted() {
        this.checker = NumericRangeChecker.DEFAULT_CHECKER;
        this.stack = new Stack<>();
        this.dictionary = new HashMap<>();
        this.instructionCounter = 0;
    }

    @Override
    public void parsingDone() {
        this.checker = null;
        this.stack.clear();
        this.dictionary.clear();
        this.instructionCounter = 0;
        this.robots.forEach(r -> r.loadProgramToExecute(Program.copyOf(this.program)));
    }

    @Override
    public void moveCommand(double[] args) {
        if (isValidDirection(args[0], args[1])) {
            this.program.addInstruction(new MoveInstruction(new Direction(args[0], args[1]), args[2]));
            this.instructionCounter++;
        }
    }

    /**
     * Flag che controlla se la direzione passata è valida
     * @param x coordinata x della direzione
     * @param y coordinata y della direzione
     * @return true se è valida, false altrimenti
     */
    private boolean isValidDirection(double x, double y) {
        return checker.isBetween(x, -1.0, 1.0)
                && checker.isBetween(y, -1.0, 1.0);
    }

    @Override
    public void moveRandomCommand(double[] args) {
        Point point1 = new Point(args[0], args[2]);
        Point point2 = new Point(args[1], args[3]);
        this.program.addInstruction(new MoveRandomInstruction(point1, point2, args[4]));
        this.instructionCounter++;
    }

    @Override
    public void signalCommand(String label) {
        this.program.addInstruction(new SignalInstruction(label));
        this.instructionCounter++;
    }

    @Override
    public void unsignalCommand(String label) {
        this.program.addInstruction(new UnsignalInstruction(label));
        this.instructionCounter++;
    }

    @Override
    public void followCommand(String label, double[] args) {
        this.program.addInstruction(new FollowInstruction(label, args[0], args[1], this.robots));
        this.instructionCounter++;
    }

    @Override
    public void stopCommand() {
        this.program.addInstruction(new StopInstruction());
        this.instructionCounter++;
    }

    @Override
    public void continueCommand(int s) {
        this.program.addInstruction(new ContinueInstruction(s, this.instructionCounter));
        this.instructionCounter++;
    }

    @Override
    public void repeatCommandStart(int n) {
        IterativeInstruction instruction = new RepeatInstruction(n, this.instructionCounter + 1);
        this.addIterativeCommand(instruction);
    }

    @Override
    public void untilCommandStart(String label) {
        IterativeInstruction instruction = new UntilInstruction(label, shapes, this.instructionCounter + 1);
        this.addIterativeCommand(instruction);
    }

    @Override
    public void doForeverStart() {
        IterativeInstruction instruction = new DoForeverInstruction(this.instructionCounter + 1);
        this.addIterativeCommand(instruction);
    }

    /**
     * Metodo che si occupa di aggiungere i comandi iterativi in una dictionary
     * e il loro indice in uno stack (pila) così da riuscire a gestire eventuali iterazioni
     * innestate tra di loro tramite il comando DONE
     * @param instruction istruzione da aggiungere
     */
    private void addIterativeCommand(IterativeInstruction instruction) {
        this.dictionary.put(this.instructionCounter, instruction);
        this.stack.add(this.instructionCounter);
        this.program.addInstruction(instruction);
        this.instructionCounter++;
    }

    @Override
    public void doneCommand() {
        int row = this.stack.pop();
        IterativeInstruction iterativeInstruction = this.dictionary.get(row);
        iterativeInstruction.setEndOfIteration(this.instructionCounter + 1);
        RobotInstruction instruction = new DoneInstruction(row);
        this.program.addInstruction(instruction);
        this.instructionCounter++;
    }

}
