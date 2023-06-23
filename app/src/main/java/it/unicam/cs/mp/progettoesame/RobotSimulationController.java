package it.unicam.cs.mp.progettoesame;

import it.unicam.cs.mp.progettoesame.api.Controller;
import it.unicam.cs.mp.progettoesame.api.ParserHandler;
import it.unicam.cs.mp.progettoesame.api.Program;
import it.unicam.cs.mp.progettoesame.api.models.Direction;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.ShapeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
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
    private Group shapesGroup;
    @FXML
    private Pane paneShapes;
    @FXML
    private TextArea programTextArea;
    private FollowMeParser parser;
    private Controller controller;
    private final Map<Robot, Circle> robotCircleMap = new HashMap<>();
    private final Map<Circle, Text> circleTextMap = new HashMap<>();

    public void initialize() {
        this.controller = new Controller(new LinkedList<>(), new LinkedList<>());
        final FollowMeParserHandler parserHandler = new ParserHandler(this.controller.getRobots(), this.controller.getShapes());
        this.parser = new FollowMeParser(parserHandler);
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
        a.show();
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

    private File openFileDialogForProgram(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare il programma",
                "*.rprogram", "Robots Program");
    }

    public void onMouseRobotClicked(MouseEvent mouseEvent) {
        File selectedFile = this.openFileDialogForRobots(mouseEvent);
        this.controller.getRobots().clear();
        try {
            List<String> lines = Files.readAllLines(selectedFile.toPath());
            for(String line : lines) {
                String[] elements = line.trim().toUpperCase().split(" ");
                this.controller.getRobots().add(new Robot(new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[2]))));
            }
            this.drawRobots();
        } catch (IOException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    private void drawRobots() {
        this.controller.getRobots().forEach(robot -> {
            Circle circle = this.createCircleFromRobot(robot);
            robot.signalLabel("Ciao robot");
            Text text = this.createTextFromCircle(circle, robot.getSignaledLabel());
            this.robotCircleMap.put(robot, circle);
            this.circleTextMap.put(circle, text);
            this.shapesGroup.getChildren().addAll(circle, text);
        });
    }

    private Text createTextFromCircle(Circle circle, String label) {
        Text text = new Text(label);
        text.setFont(Font.font("Arial", 16));
        text.setFill(Color.BLACK);
        text.setLayoutX(circle.getCenterX() - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(circle.getCenterY() + text.getLayoutBounds().getHeight() / 4);
        return text;
    }

    private Circle createCircleFromRobot(Robot r) {
        Circle circle = new Circle(r.getPosition().getX(), r.getPosition().getY(),50);
        circle.setFill(Color.RED);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        circle.setTranslateX(0);
        circle.setTranslateY(0);
        return circle;
    }

    public void onExecuteClicked(MouseEvent mouseEvent) {
        this.controller.nextInstruction();
        this.updateCircles();
    }

    private void updateCircles() {
        robotCircleMap.forEach((robot, circle) -> {
            double targetX = robot.getPosition().getX();
            double targetY = robot.getPosition().getY();
            Text text = this.circleTextMap.get(circle);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                    new KeyValue(circle.centerXProperty(), targetX),
                    new KeyValue(circle.centerYProperty(), targetY),
                    new KeyValue(text.layoutXProperty(), targetX - text.getLayoutBounds().getWidth() / 2),
                    new KeyValue(text.layoutYProperty(), targetY + text.getLayoutBounds().getHeight() / 4)));
            timeline.play();
        });
    }

    public void onReadProgramClicked(MouseEvent mouseEvent) {
        File selectedFile = this.openFileDialogForProgram(mouseEvent);
        if(selectedFile != null) {
            try {
                parser.parseRobotProgram(selectedFile);
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                lines.forEach(x -> this.programTextArea.appendText(x + "\n"));
            } catch (IOException | FollowMeParserException e) {
                this.showErrorAlert(e.getMessage());
            }
        }
    }

    public void onExecuteMultipleInstruction(MouseEvent mouseEvent) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                for(int i = 0; i < 10; i++) {
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
}
