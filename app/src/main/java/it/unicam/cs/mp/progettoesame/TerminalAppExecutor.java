package it.unicam.cs.mp.progettoesame;

import java.io.IOException;

/**
 * Classe che permette di eseguire l'applicazione del terminale
 */
public class TerminalAppExecutor {
    private Process terminalProcess;
    public TerminalAppExecutor() {}

    /**
     * Metodo che avvia il terminale, in base al sistema operativo in cui si sta eseguendo
     * il programma
     * @throws IOException se c'è qualche errore nell'avvio del programma
     */
    public void openTerminal() throws IOException {
        String osName = this.getOsName();
        ProcessBuilder processBuilder;
        if(osName.contains("Windows")) {
            processBuilder = new ProcessBuilder("..\\Terminal\\Terminal.exe");
        } else {
            processBuilder = new ProcessBuilder("sh", "-c", "open ../mac/Terminal.app");
        }
        this.terminalProcess = processBuilder.start();
    }

    /**
     * Metodo che ritorna il nome del sistema operativo in cui si sta eseguendo il programma
     * @return il nome del sistema operativo
     */
    private String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * Metodo che chiude il terminale, se è stato avviato
     * Nei sistemi operativi Mac OS esegue il comando per terminare il processo
     * @throws IOException se c'è qualche problema nell'esecuzione del comando
     */
    public void closeTerminal() throws IOException {
        if(terminalProcess != null && terminalProcess.isAlive()) {
            terminalProcess.destroy();
        }
        if(this.getOsName().contains("Mac")) {
            new ProcessBuilder("sh", "-c", "pkill Terminal").start();
        }
    }

    /**
     * Metodo che restituisce true se il terminale è aperto oppure no
     * @return true se il processo è attivo, false altrimenti
     */
    public boolean isOpened() {
        return terminalProcess != null && terminalProcess.isAlive();
    }
}
