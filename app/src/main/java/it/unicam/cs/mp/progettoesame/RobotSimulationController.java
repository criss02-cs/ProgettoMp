package it.unicam.cs.mp.progettoesame;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class RobotSimulationController {
    @FXML
    private Group shapesGroup;
    @FXML
    private TextArea programTextArea;

    public void initialize() {
        for (int i = 0; i < 50; i++) {
            Circle circle = new Circle(10);
            circle.setFill(Color.RED);

            // Calcola la posizione iniziale del cerchio
            double initialX = Math.random() * (535 - 2 * 10) + 10;
            double initialY = Math.random() * (493 - 2 * 10) + 10;
            circle.setCenterX(initialX);
            circle.setCenterY(initialY);

            // Calcola la destinazione del cerchio
            double targetX = Math.random() * (535 - 2 * 10) + 10;
            double targetY = Math.random() * (493 - 2 * 10) + 10;

            // Crea l'animazione di traslazione
            TranslateTransition transition = new TranslateTransition(Duration.seconds(2), circle);
            transition.setToX(targetX - initialX);
            transition.setToY(targetY - initialY);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setAutoReverse(true);

            // Avvia l'animazione
            transition.play();

            shapesGroup.getChildren().add(circle);
        }
    }


}
