package it.unicam.cs.mp.progettoesame.api.exceptions;

/**
 * Classe che gestisce l'eccezione che viene scaturita quando i robot non sono
 * stati caricati
 */
public class RobotsNotLoadedException extends Exception {
    public RobotsNotLoadedException(String message) {
        super(message);
    }
}
