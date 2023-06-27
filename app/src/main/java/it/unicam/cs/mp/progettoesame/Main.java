package it.unicam.cs.mp.progettoesame;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private static final int NUM_CIRCLES = 30;
    private static final int CIRCLE_RADIUS = 10;
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/RobotSimulation.fxml")));
        primaryStage.setTitle("Robot Simulation App");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public void start2(Stage primaryStage) {
        Group root = new Group();

        for (int i = 0; i < NUM_CIRCLES; i++) {
            Circle circle = new Circle(CIRCLE_RADIUS);
            circle.setFill(Color.RED);

            // Calcola la posizione iniziale del cerchio
            double initialX = Math.random() * (SCENE_WIDTH - 2 * CIRCLE_RADIUS) + CIRCLE_RADIUS;
            double initialY = Math.random() * (SCENE_HEIGHT - 2 * CIRCLE_RADIUS) + CIRCLE_RADIUS;
            circle.setCenterX(initialX);
            circle.setCenterY(initialY);

            // Calcola la destinazione del cerchio
            double targetX = Math.random() * (SCENE_WIDTH - 2 * CIRCLE_RADIUS) + CIRCLE_RADIUS;
            double targetY = Math.random() * (SCENE_HEIGHT - 2 * CIRCLE_RADIUS) + CIRCLE_RADIUS;

            // Crea l'animazione di traslazione
            TranslateTransition transition = new TranslateTransition(Duration.seconds(2), circle);
            transition.setToX(targetX - initialX);
            transition.setToY(targetY - initialY);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setAutoReverse(true);

            // Avvia l'animazione
            transition.play();

            root.getChildren().add(circle);
        }

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void start1(Stage primaryStage) {
        // Crea un nuovo cerchio
        Circle circle = new Circle(50); // Raggio del cerchio
        circle.setFill(Color.RED);

        circle.setCenterX(0);
        circle.setCenterY(0);

        // Calcola le coordinate di destinazione
        double targetX = 300; // Posizione X desiderata
        double targetY = 150; // Posizione Y desiderata

        // Calcola la distanza da spostare lungo l'asse X e Y
        double deltaX = targetX - circle.getCenterX();
        double deltaY = targetY - circle.getCenterY();

        // Crea un'animazione di traslazione
        TranslateTransition transition = new TranslateTransition(Duration.seconds(10), circle);
        transition.setByX(deltaX); // Sposta il cerchio lungo l'asse X
        transition.setByY(deltaY); // Sposta il cerchio lungo l'asse Y

        // Avvia l'animazione
        transition.play();

        // Crea un pannello e aggiungi il cerchio ad esso
        Pane pane = new Pane();
        pane.getChildren().add(circle);

        // Crea una scena e aggiungi il pannello ad essa
        Scene scene = new Scene(pane, 400, 200);

        // Imposta la scena primaria
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}