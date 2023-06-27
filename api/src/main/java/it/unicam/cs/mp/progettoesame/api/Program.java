package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.models.instructions.RobotInstruction;

import java.util.LinkedList;
import java.util.List;

/**
 * Classe che va a rappresentare il programma completo che ogni robot
 * deve eseguire
 */
public class Program {
    /**
     * Lista delle istruzioni che ogni robot deve eseguire
     */
    private List<RobotInstruction> robotInstructions;
    /**
     * Indice della riga in cui sto eseguendo l'istruzione
     */
    private int programCounter;


    public Program() {
        this.programCounter = 0;
        this.robotInstructions = new LinkedList<>();
    }

    /**
     * Metodo che aggiunge una nuova istruzione alla lista delle istruzioni
     * @param instruction istruzione da aggiungere
     */
    public void addInstruction(RobotInstruction instruction) {
        robotInstructions.add(instruction);
    }

    /**
     * Metodo che si occupa di eseguire la prossima istruzione,
     * controlla anche se sono arrivato a fine programma
     * @param robot il robot su cui effettuare il comando
     * @return true se ci sono altre istruzioni da eseguire, false se sono arrivato alla fine
     */
    public boolean executeInstruction(Robot robot) throws IllegalArgumentException {
        if(this.programCounter == robotInstructions.size()) { return false; }
        RobotInstruction instruction = this.robotInstructions.get(this.programCounter);
        instruction.execute(robot);
        int nextInstruction = instruction.canGoToNextInstruction();
        if(nextInstruction == -1)
            this.programCounter++;
        else
            this.programCounter =nextInstruction;
        return true;
    }

    /**
     * Metodo che crea una copia del programma
     * @param p il programma da passare
     * @return la nuova instanza del programma
     */
    public static Program copyOf(Program p) {
        Program copy = new Program();
        copy.robotInstructions = List.copyOf(p.robotInstructions.stream().map(RobotInstruction::cloneObject).toList());
        return copy;
    }
}
