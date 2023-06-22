package it.unicam.cs.mp.progettoesame;

import it.unicam.cs.mp.progettoesame.api.Controller;
import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.DistanceCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.ShapeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RobotSimulationController {
    @FXML
    private Group shapesGroup;
    @FXML
    private Pane paneShapes;
    @FXML
    private TextArea programTextArea;
    private FollowMeParser parser;
    private Controller controller;
    private Map<Robot, Circle> robotCircleMap = new HashMap<>();

    public void initialize() {
        this.controller = new Controller();
        /*for (int i = 0; i < 50; i++) {
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
        }*/
        clipChildren(paneShapes);
    }

    private void clipChildren(Region region) {
        final Rectangle clipPane = new Rectangle();
        region.setClip(clipPane);

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });
    }

    public void onMouseShapesClicked(MouseEvent mouseEvent) {
        parser = new FollowMeParser(null);
        File selectedFile = this.openFileDialogForShapes(mouseEvent);
        if(selectedFile != null) {
            try {
                List<ShapeData> shapeData = parser.parseEnvironment(selectedFile);
                List<IShape> shapes = shapeData.stream().map(new ShapeParser()::parseFromShapeData).toList();
                this.controller.loadShapes(shapes);
                this.drawShapes();
            } catch (IOException | FollowMeParserException e) {
                this.showErrorAlert(e.getMessage());
            }
        }
    }

    private void showErrorAlert(String text) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Errore");
        a.setContentText(text);
    }

    private void drawShapes(){
        this.shapesGroup.getChildren().clear();
        this.controller.getShapes().forEach(shape -> {
            if(shape.getDimensions().getItem2() == -1) {
                drawCircularShape(shape);
            } else {
                drawRectangularShape(shape);
            }
        });
    }

    private void drawRectangularShape(IShape shape) {
        double x = shape.getCoordinates().getX() - (shape.getDimensions().getItem1() / 2);
        double y = shape.getCoordinates().getY() - (shape.getDimensions().getItem2() / 2);
        Rectangle rectangle = new Rectangle(x, y, shape.getDimensions().getItem1(), shape.getDimensions().getItem2());
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        this.shapesGroup.getChildren().add(rectangle);
    }

    private void drawCircularShape(IShape shape) {
        Circle circle = new Circle(shape.getDimensions().getItem1());
        circle.setCenterX(shape.getCoordinates().getX());
        circle.setCenterY(shape.getCoordinates().getY());
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        this.shapesGroup.getChildren().add(circle);
    }

    private File openFileDialogForShapes(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare le figure",
                "*.rshape", "Robots Shapes");
    }

    private File openFileDialog(MouseEvent mouseEvent, String title, String extension, String description) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(description, extension));
        File selectedFile = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
        return selectedFile;
    }

    private File openFileDialogForRobots(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare i robot",
                "*.rrobots", "Robots");
    }

    public void onMouseRobotClicked(MouseEvent mouseEvent) {
        File selectedFile = this.openFileDialogForRobots(mouseEvent);
        List<Robot> robots = new LinkedList<>();
        try {
            List<String> lines = Files.readAllLines(selectedFile.toPath());
            for(String line : lines) {
                String[] elements = line.trim().toUpperCase().split(" ");
                robots.add(new Robot(new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]))));
            }
            this.controller.loadRobots(robots);
            this.drawRobots();
        } catch (IOException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    private void drawRobots() {
        this.controller.getRobots().forEach(robot -> {
            Circle circle = new Circle(50);
            circle.setCenterX(robot.getPosition().getX());
            circle.setCenterY(robot.getPosition().getY());
            circle.setFill(Color.RED);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(2);
            circle.setTranslateX(0);
            circle.setTranslateY(0);
            this.robotCircleMap.put(robot, circle);
            this.shapesGroup.getChildren().add(circle);
        });
    }

    public void onMoveDirectionClicked(MouseEvent mouseEvent) {
        this.controller.getRobots().forEach(x -> {
            x.move(50, new Direction(1.0, -1.0));
        });
        robotCircleMap.forEach((robot, circle) -> {
            double targetX = robot.getPosition().getX();
            double targetY = robot.getPosition().getY();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                    new KeyValue(circle.centerXProperty(), targetX),
                    new KeyValue(circle.centerYProperty(), paneShapes.getHeight() - targetY)));
            timeline.setOnFinished(event -> {
                System.out.println("Robot position: " + robot.getPosition());
                System.out.println("Circle position: { X: " + circle.getCenterX() + ", Y: " + circle.getCenterY() + "}");
                /*circle.setCenterX(robot.getPosition().getX());
                circle.setCenterY(robot.getPosition().getY());*/
            });
            timeline.play();
        });

    }

    public void onReadProgramClicked(MouseEvent mouseEvent) {
        robotCircleMap.forEach((robot, circle) -> {
            System.out.println("\t-----------------------------");
            System.out.println("\tRobot position: "+robot.getPosition());
            System.out.println("\tCircle position: X: " + (circle.getCenterX()) + ", Y: " + (circle.getCenterY()));
            System.out.println("\t-----------------------------");
        });
    }
}
