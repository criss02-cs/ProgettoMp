package it.unicam.cs.mp.progettoesame.api.models.instructions;

/**
 * Classe astratta si occupa di rappresentare tutte quelle istruzioni
 * che sono iterative
 */
public abstract class IterativeInstruction implements RobotInstruction {
    /**
     * Indice della prima riga di codice del ciclo,
     * serve per fare in modo che una volta eseguita l'istruzione
     * di controllo si vada direttamente a quell'indice
     */
    protected final int firstRowIteration;
    /**
     * Indice della prima riga che viene situata dopo il comando
     * DONE, che sarà fuori dal ciclo. Serve per fare in modo che una volta eseguita
     * l'istruzione di controllo (e si deve uscire dal ciclo) si vada direttamente a quell'indice
     */
    protected int endOfIteration;

    protected IterativeInstruction(int firstRowIteration) {
        this.firstRowIteration = firstRowIteration;
    }
    /**
     * Metodo che imposta l'indice di riga che sta dopo il DONE
     * di un'istruzione iterativa
     * @param index l'indice di riga da impostare
     */
    public void setEndOfIteration(int index) {
        this.endOfIteration = index;
    }
}
