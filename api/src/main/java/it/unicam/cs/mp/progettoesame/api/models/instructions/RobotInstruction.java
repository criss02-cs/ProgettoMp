package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.io.IOException;

/**
 * Interfaccia che rappresenta una istruzione che ogni robot può eseguire
 */
public interface RobotInstruction {
    /**
     * Metodo per eseguire il comando
     * @param robot il robot su cui andrà eseguito il comando
     * @throws IllegalArgumentException se c'è qualche errore nei parametri passati
     * @throws IOException se ci sono problemi nella scrittura del log
     */
    void execute(Robot robot) throws IllegalArgumentException;

    /**
     * Flag intero per vedere se posso proseguire al prossimo
     * comando
     * @return -1 se posso proseguire, altrimenti la riga a cui devo tornare
     */
    int canGoToNextInstruction();

    /**
     * Metodo che si occupa di creare una nuova istanza
     * dell'oggetto con gli stessi valori
     * @return la nuova istanza dell'oggetto
     */
    RobotInstruction cloneObject();
}
