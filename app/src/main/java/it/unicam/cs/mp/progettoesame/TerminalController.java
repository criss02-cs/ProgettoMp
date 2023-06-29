package it.unicam.cs.mp.progettoesame;

import it.unicam.cs.mp.progettoesame.api.console.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.util.List;

public class TerminalController {
    @FXML
    private TextArea terminal;
    public void initialize() throws IOException {
        this.terminal.setFont(Font.font("Cascadia Code", FontWeight.BOLD, 18));
        Thread t = new Thread(this::readAllLogs);
        t.start();
    }

    private synchronized void readAllLogs() {
        try {
            while (true) {
                String s = Console.readAll();
                if(s.length() != this.terminal.getText().length()) {
                    Platform.runLater(() -> {
                        this.terminal.setText("");
                        this.terminal.appendText(s);
                    });
                }
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
