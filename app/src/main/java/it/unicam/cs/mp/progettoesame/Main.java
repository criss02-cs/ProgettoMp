package it.unicam.cs.mp.progettoesame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/fxml/RobotSimulation.fxml")));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        this.editStageConfiguration(primaryStage);
        RobotSimulationController controller = loader.getController();
        controller.setExitConfiguration(primaryStage);
        primaryStage.show();
    }

    private void editStageConfiguration(Stage stage) {
        stage.setTitle("SwarmRobot");
        stage.setResizable(false);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
    }
    public static void main(String[] args) {
        launch(args);
    }
}