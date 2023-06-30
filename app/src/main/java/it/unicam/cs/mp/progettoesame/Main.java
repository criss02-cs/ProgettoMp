package it.unicam.cs.mp.progettoesame;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/RobotSimulation.fxml")));
        Parent root = loader.load();
        primaryStage.setTitle("Robot Simulation App");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        RobotSimulationController controller = loader.getController();
        controller.setExitConfiguration(primaryStage);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}