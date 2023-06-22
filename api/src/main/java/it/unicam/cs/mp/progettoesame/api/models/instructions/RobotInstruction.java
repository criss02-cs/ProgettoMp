package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

public interface RobotInstruction {
    /**
     * Metodo per eseguire il comando
     * @param robot il robot su cui andrà eseguito il comando
     */
    void execute(Robot robot);

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
    RobotInstruction clone();
}
