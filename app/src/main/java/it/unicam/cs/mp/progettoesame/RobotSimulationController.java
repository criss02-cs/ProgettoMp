package it.unicam.cs.mp.progettoesame;

import it.unicam.cs.mp.progettoesame.api.Controller;
import it.unicam.cs.mp.progettoesame.api.exceptions.RobotsNotLoadedException;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.CoordinatesTranslator;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    private Group group;
    @FXML
    private Pane pane;
    @FXML
    private TextArea programTextArea;
    private Controller controller;
    private final Map<Robot, Circle> robotCircleMap;
    private final Map<Circle, Text> circleTextMap;
    private final Map<Shape, Text> shapesTextMap;
    private CoordinatesTranslator coordinatesTranslator;

    public RobotSimulationController() {
        this.robotCircleMap = new HashMap<>();
        circleTextMap = new HashMap<>();
        shapesTextMap = new HashMap<>();
    }

    public void initialize() {
        this.controller = new Controller(new LinkedList<>(), new LinkedList<>());
        clipChildren(pane);
        Platform.runLater(() -> this.coordinatesTranslator = new CoordinatesTranslator(this.pane.getHeight(), this.pane.getWidth()));
    }

    private void clipChildren(Region region) {
        final Rectangle clipPane = new Rectangle();
        region.setClip(clipPane);
        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });
    }

    private void updateCircles() {
        robotCircleMap.forEach((robot, circle) -> {
            Point target = this.coordinatesTranslator.translateToScreenCoordinates(robot.getPosition());
            Text text = this.circleTextMap.get(circle);
            checkChangeText(text, robot, target);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                    this.getAnimations(circle, text, robot, target).toArray(KeyValue[]::new)));
            timeline.play();
        });
    }

    private void checkChangeText(Text text, Robot robot, Point target) {
        if (!robot.getSignaledLabel().equalsIgnoreCase(text.getText())) {
            text.setText(robot.getSignaledLabel());
            text.setLayoutX(this.getXPositionOfText(target.getX(), text));
            text.setLayoutY(this.getYPositionOfText(target.getY(), text));
        }
    }

    private List<KeyValue> getAnimations(Circle c, Text t, Robot r, Point target) {
        List<KeyValue> keyValues = new LinkedList<>();
        keyValues.add(new KeyValue(c.centerXProperty(), target.getX()));
        keyValues.add(new KeyValue(c.centerYProperty(), target.getY()));
        Point robotTraslation = this.coordinatesTranslator.translateToScreenCoordinates(r.getPosition());
        if ((robotTraslation.getX() != this.getXPositionOfText(target.getX(), t))
                || (robotTraslation.getY() != this.getYPositionOfText(target.getY(), t))) {
            keyValues.add(new KeyValue(t.layoutXProperty(), this.getXPositionOfText(target.getX(), t)));
            keyValues.add(new KeyValue(t.layoutYProperty(), this.getYPositionOfText(target.getY(), t)));
        }
        return keyValues;
    }

    private Tuple<Double, Double> getCoordinatesOfRectangle(IShape shape) {
        Point screenCoordinates = this.coordinatesTranslator.translateToScreenCoordinates(shape.getCoordinates());
        double x = screenCoordinates.getX() - (shape.getDimensions().getItem1() / 2);
        double y = screenCoordinates.getY() - (shape.getDimensions().getItem2() / 2);
        return Tuple.of(x, y);
    }

    private void reset() {
        this.robotCircleMap.clear();
        this.circleTextMap.clear();
        this.shapesTextMap.clear();
        this.controller = new Controller(new LinkedList<>(), new LinkedList<>());
        this.group.getChildren().clear();
    }

    //region TEXT POSITION
    private double getXPositionOfText(double x, Text text) {
        return x - text.getLayoutBounds().getWidth() / 2;
    }

    private double getYPositionOfText(double y, Text text) {
        return y + text.getLayoutBounds().getHeight() / 4;
    }
    //endregion

    //region ALERTS
    private void showErrorAlert(String text) {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Errore");
        a.setContentText(text);
        a.show();
    }
    //endregion

    //region DRAW ELEMENTS
    private void drawShapes() {
        this.controller.getShapes().forEach(this::drawShapes);
    }

    private void drawShapes(IShape shape) {
        if (shape.getDimensions().getItem2() == -1) {
            drawCircularShape(shape);
        } else {
            drawRectangularShape(shape);
        }
    }

    private void drawRectangularShape(IShape shape) {
        Tuple<Double, Double> coordinates = this.getCoordinatesOfRectangle(shape);
        Rectangle rectangle = new Rectangle(coordinates.getItem1(), coordinates.getItem2(),
                shape.getDimensions().getItem1(), shape.getDimensions().getItem2());
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        Text text = this.createTextFromRectangle(rectangle, shape.getLabel());
        this.shapesTextMap.put(rectangle, text);
        this.group.getChildren().addAll(rectangle, text);
    }

    private void drawCircularShape(IShape shape) {
        Circle circle = new Circle(shape.getDimensions().getItem1());
        Point screenPoint = this.coordinatesTranslator.translateToScreenCoordinates(shape.getCoordinates());
        circle.setCenterX(screenPoint.getX());
        circle.setCenterY(screenPoint.getY());
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        Text text = this.createTextFromCircle(circle, shape.getLabel());
        this.shapesTextMap.put(circle, text);
        this.group.getChildren().addAll(circle, text);
    }

    private void drawRobots() {
        this.controller.getRobots().forEach(robot -> {
            Circle circle = this.createCircleFromRobot(robot);
            Text text = this.createTextFromCircle(circle, robot.getSignaledLabel());
            this.robotCircleMap.put(robot, circle);
            this.circleTextMap.put(circle, text);
            this.group.getChildren().addAll(circle, text);
        });
    }
    //endregion

    //region CREATE ELEMENTS
    private Text createTextFromCircle(Circle circle, String label) {
        Text text = new Text(label);
        text.setFont(Font.font("Arial", 16));
        text.setFill(Color.BLACK);
        text.setLayoutX(circle.getCenterX() - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(circle.getCenterY() + text.getLayoutBounds().getHeight() / 4);
        return text;
    }

    private Text createTextFromRectangle(Rectangle rectangle, String label) {
        Text text = new Text(label);
        text.setFont(Font.font("Arial", 16));
        text.setFill(Color.BLACK);
        text.setLayoutX(rectangle.getX());
        text.setLayoutY(rectangle.getY() + rectangle.getLayoutBounds().getHeight() / 2);
        return text;
    }

    private Circle createCircleFromRobot(Robot r) {
        Point screenPoint = this.coordinatesTranslator.translateToScreenCoordinates(r.getPosition());
        Circle circle = new Circle(screenPoint.getX(),
                screenPoint.getY(), 50);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        circle.setTranslateX(0);
        circle.setTranslateY(0);
        return circle;
    }
    //endregion

    //region FileDialogs
    private File openFileDialog(MouseEvent mouseEvent, String title, String extension, String description) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(description, extension));
        return fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    private File openFileDialogForRobots(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare i robot",
                "*.rrobots", "Robots");
    }

    private File openFileDialogForShapes(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare le figure",
                "*.rshape", "Robots Shapes");
    }

    private File openFileDialogForProgram(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare il programma",
                "*.rprogram", "Robots Program");
    }

    //endregion

    //region EVENTS
    public void onMouseShapesClicked(MouseEvent mouseEvent) {
        try {
            File selectedFile = this.openFileDialogForShapes(mouseEvent);
            if (selectedFile != null) {
                this.controller.readShapeList(selectedFile);
                this.drawShapes();
            }
        } catch (IOException | FollowMeParserException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    public void onMouseRobotClicked(MouseEvent mouseEvent) {
        File selectedFile = this.openFileDialogForRobots(mouseEvent);
        this.controller.getRobots().clear();
        try {
            List<String> lines = Files.readAllLines(selectedFile.toPath());
            for (String line : lines) {
                String[] elements = line.trim().toUpperCase().split(" ");
                this.controller.getRobots().add(new Robot(new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]))));
            }
            this.drawRobots();
        } catch (IOException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    public void onExecuteClicked(MouseEvent mouseEvent) {
        this.controller.nextInstruction();
        this.updateCircles();
    }

    public void onReadProgramClicked(MouseEvent mouseEvent) {
        try {
            File selectedFile = this.openFileDialogForProgram(mouseEvent);
            if (selectedFile != null) {
                List<String> lines = this.controller.readInstructionList(selectedFile);
                lines.forEach(x -> this.programTextArea.appendText(x + "\n"));
            }
        } catch (IOException | FollowMeParserException | RobotsNotLoadedException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    public void onExecuteMultipleInstruction(MouseEvent mouseEvent) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    this.controller.nextInstruction();
                    this.updateCircles();
                    Thread.sleep(1000); // Pausa di un secondo (1000 millisecondi)
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
    //endregion
}
