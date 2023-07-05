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
        String osName = System.getProperty("os.name");
        ProcessBuilder processBuilder;
        if(osName.contains("Windows")) {
            processBuilder = new ProcessBuilder("..\\win10-x64\\Terminal.exe");
        } else {
            /*File appFile = new File("..\\maccatalyst-x64\\Terminal.app");
            Desktop.getDesktop().open(appFile);*/
            processBuilder = new ProcessBuilder("open ../maccatalyst-x64/Terminal.app");
        }
        this.terminalProcess = processBuilder.start();
    }

    /**
     * Metodo che chiude il terminale, se è stato avviato
     */
    public void closeTerminal() {
        if(terminalProcess != null && terminalProcess.isAlive()) {
            terminalProcess.destroy();
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
