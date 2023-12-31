package it.unicam.cs.mp.progettoesame;

import it.unicam.cs.mp.progettoesame.api.Controller;
import it.unicam.cs.mp.progettoesame.api.exceptions.RobotsNotLoadedException;
import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.CoordinatesTranslator;
import it.unicam.cs.mp.progettoesame.api.utils.Tuple;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * JavaFX Controller of RobotSimulationApp
 */
public class RobotSimulationController {
    @FXML
    public VBox container;
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
    private Translate translate;
    private CoordinatesTranslator coordinatesTranslator;
    private final TerminalAppExecutor terminalAppExecutor;

    public RobotSimulationController() {
        this.robotCircleMap = new HashMap<>();
        circleTextMap = new HashMap<>();
        shapesTextMap = new HashMap<>();
        terminalAppExecutor = new TerminalAppExecutor();
    }

    /**
     * Metodo che inizializza tutti i componenti grafici
     */
    public void initialize() {
        this.controller = new Controller();
        clipChildren(pane);
        Platform.runLater(() -> this.coordinatesTranslator = new CoordinatesTranslator(this.pane.getHeight(), this.pane.getWidth()));
        translate = new Translate();
        this.group.getTransforms().add(translate);
    }

    /**
     * Metodo che imposta le azioni da eseguire quando si chiude l'applicazione.
     * Nello specifico esso andrà a chiudere il terminale, se questo è stato aperto, e
     * in seguito andrà a chiudere l'applicazione
     * @param stage lo stage da cui deriva tutta l'app
     */
    public void setExitConfiguration(Stage stage) {
        stage.setOnCloseRequest(v -> {
            try {
                this.terminalAppExecutor.closeTerminal();
            } catch (IOException e) {
                this.showErrorAlert(e.getMessage());
            }
            Platform.exit();
        });
    }

    /**
     * Metodo che aggiunge una specie di bordo per fare in modo
     * di mostrare una specie di taglio quando le figure vanno fuori
     * dall'area visibile
     * @param region
     */
    private void clipChildren(Region region) {
        final Rectangle clipPane = new Rectangle();
        region.setClip(clipPane);
        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            clipPane.setWidth(newValue.getWidth());
            clipPane.setHeight(newValue.getHeight());
        });
    }

    /**
     * Metodo che aggiorna graficamente i robot in base alla lista di robot
     */
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

    /**
     * Metodo che verifica se il robot ha cambiato la sua label,
     * se è cambiata aggiorno graficamente il testo e la sua posizione
     * @param text il testo da verificare
     * @param robot il robot che devo controllare
     * @param target il punto in cui dovrei eventualmente spostare il testo
     */
    private void checkChangeText(Text text, Robot robot, Point target) {
        if (!robot.getSignaledLabel().equalsIgnoreCase(text.getText())) {
            text.setText(robot.getSignaledLabel());
            text.setLayoutX(this.getXPositionOfText(target.getX(), text));
            text.setLayoutY(this.getYPositionOfText(target.getY(), text));
        }
    }

    /**
     * Metodo che ritorna una lista di <code>KeyValue</code> per vedere quali animazioni
     * devo andare ad eseguire. Il componente <code>Text</code> non sempre andrà animato in quanto
     * non sempre un robot si muove
     * @param c il cerchio da spostare
     * @param t il testo da spostare
     * @param r il robot da cui devo verificare le coordinate
     * @param target il punto di arrivo
     * @return la lista di animazioni da svolgere
     */
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

    /**
     * Metodo che restituisce le coordinate di un <code>Rectangle</code> in base all'area che mi interessa,
     * in quanto mi devo calcolare il punto in alto a sinistra
     * @param shape l'area che mi interessa mostrare
     * @return una tupla contenente le coordinate del punto in alto a sinistra del rettangolo
     */
    private Tuple<Double, Double> getCoordinatesOfRectangle(IShape shape) {
        Point screenCoordinates = this.coordinatesTranslator.translateToScreenCoordinates(shape.getCoordinates());
        double x = screenCoordinates.getX() - (shape.getDimensions().getItem1() / 2);
        double y = screenCoordinates.getY() - (shape.getDimensions().getItem2() / 2);
        return Tuple.of(x, y);
    }

    /**
     * Metodo che resetta il programma e pulisce tutte le strutture
     * dati presenti, permettendo di ricominciare da capo, senza dover
     * chiudere l'applicazione
     */
    private void reset() {
        this.robotCircleMap.clear();
        this.circleTextMap.clear();
        this.shapesTextMap.clear();
        this.controller = new Controller();
        this.group.getChildren().clear();
        this.programTextArea.setText("");
        this.container.getChildren().stream().filter(Node::isDisable).forEach(y -> y.setDisable(false));
    }

    /**
     * Metodo che controlla se i robot hanno finito di eseguire il programma,
     * se hanno finito mostra un alert con un messaggio
     */
    private void checkProgramFinished(){
        if(this.controller.isAllRobotFinished()) {
            this.showInformationAlert("Programma terminato", "Il programma \u00e8 stato terminato da tutti i robot");
        }
    }

    //region TEXT POSITION

    /**
     * Metodo che restituisce la coordinata X del componente di testo
     * @param x la coordinata X del contenitore del testo
     * @param text il componente di testo
     * @return la coordinata X del componente di testo
     */
    private double getXPositionOfText(double x, Text text) {
        return x - text.getLayoutBounds().getWidth() / 2;
    }
    /**
     * Metodo che restituisce la coordinata Y del componente di testo
     * @param y la coordinata Y del contenitore del testo
     * @param text il componente di testo
     * @return la coordinata Y del componente di testo
     */
    private double getYPositionOfText(double y, Text text) {
        return y + text.getLayoutBounds().getHeight() / 4;
    }
    //endregion

    //region ALERTS

    /**
     * Metodo che mostra un alert in caso di errore,
     * in genere utilizzato quando viene sollevata un'eccezzione
     * @param text il testo da mostrare nell'alert
     */
    private void showErrorAlert(String text) {
        Alert a = new Alert(AlertType.ERROR);
        a.setTitle("Errore");
        a.setHeaderText(null);
        a.initOwner(group.getScene().getWindow());
        a.setContentText(text);
        a.show();
    }

    /**
     * Metodo che mostra una input dialog, utilizzata per inserire un numero
     * @param headerText il testo da mostrare nella dialog
     * @return il valore intero inserito
     */
    private int showInputIntegerDialog(String headerText) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText(headerText);
        td.initOwner(group.getScene().getWindow());
        Optional<String> text = td.showAndWait();
        return text.map(Integer::parseInt).orElse(-1);
    }

    /**
     * Metodo che mostra un alert di conferma.
     * @param title il titolo dell'alert
     * @param content il messaggio da mostrare
     * @return la scelta fatta dall'utente
     */
    private Optional<ButtonType> showConfirmAlert(String title, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.initOwner(group.getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(group.getScene().getWindow());
        alert.setContentText(content);
        alert.show();
    }
    //endregion

    //region DRAW ELEMENTS

    /**
     * Metodo che per ogni area la disegna nell'area interessata
     */
    private void drawShapes() {
        this.controller.getShapes().forEach(this::drawShapes);
    }

    /**
     * Disegna un'area, andando a verificare se è un cerchio o un rettangolo
     * @param shape l'area da disegnare
     */
    private void drawShapes(IShape shape) {
        if (shape.getDimensions().getItem2() == -1) {
            drawCircularShape(shape);
        } else {
            drawRectangularShape(shape);
        }
    }

    /**
     * Metodo che disegna un'area rettangolare
     * @param shape area da cui costruire il rettangolo
     */
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

    /**
     * Metodo che disegna un'area circolare
     * @param shape area da cui costruire il cerchio
     */
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

    /**
     * Metodo che va a disegnare tutti i robot presenti
     */
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

    /**
     * Metodo che crea un <code>Text</code> in base ad un <code>Circle</code>
     * @param circle il cerchio da cui costruire il componente di testo
     * @param label il testo da inserire all'interno
     * @return il <code>Text</code> creato
     */
    private Text createTextFromCircle(Circle circle, String label) {
        Text text = new Text(label);
        text.setFont(Font.font("Arial", 16));
        text.setFill(Color.BLACK);
        text.setLayoutX(circle.getCenterX() - text.getLayoutBounds().getWidth() / 2);
        text.setLayoutY(circle.getCenterY() + text.getLayoutBounds().getHeight() / 4);
        return text;
    }
    /**
     * Metodo che crea un <code>Text</code> in base ad un <code>Rectangle</code>
     * @param rectangle il rettangolo da cui costruire il componente di testo
     * @param label il testo da inserire all'interno
     * @return il <code>Text</code> creato
     */
    private Text createTextFromRectangle(Rectangle rectangle, String label) {
        Text text = new Text(label);
        text.setFont(Font.font("Arial", 16));
        text.setFill(Color.BLACK);
        text.setLayoutX(rectangle.getX());
        text.setLayoutY(rectangle.getY() + rectangle.getLayoutBounds().getHeight() / 2);
        return text;
    }

    /**
     * Metodo che crea un <code>Circle</code> in base al <code>Robot</code>
     * corrispondente
     * @param r il robot da "disegnare"
     * @return il cerchio che corrisponde al robot
     */
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

    /**
     * Metodo che imposta un FileChooser per selezionare un file dal proprio pc
     * @param mouseEvent click del bottone da cui è scaturita l'azione
     * @param title titolo della finestra che si aprirà
     * @param extension estensione del file da cercare
     * @param description descrizione del tipo di estensione
     * @return il file che viene selezionato
     */
    private File openFileDialog(MouseEvent mouseEvent, String title, String extension, String description) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(description, extension));
        return fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
    }

    /**
     * Apre un FileChooser per selezionare i robot
     * @param mouseEvent click del bottone da cui è scaturita l'azione
     * @return il file che viene selezionato
     */
    private File openFileDialogForRobots(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare i robot",
                "*.rrobots", "Robots");
    }
    /**
     * Apre un FileChooser per selezionare le aree
     * @param mouseEvent click del bottone da cui è scaturita l'azione
     * @return il file che viene selezionato
     */
    private File openFileDialogForShapes(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare le figure",
                "*.rshape", "Robots Shapes");
    }
    /**
     * Apre un FileChooser per selezionare il programma
     * @param mouseEvent click del bottone da cui è scaturita l'azione
     * @return il file che viene selezionato
     */
    private File openFileDialogForProgram(MouseEvent mouseEvent) {
        return this.openFileDialog(mouseEvent, "Seleziona il file da cui importare il programma",
                "*.rprogram", "Robots Program");
    }

    //endregion

    //region EVENTS

    /**
     * Evento che resetta la simulazione, potendo ricominciare da capo
     * @param mouseEvent l'evento che scaturisce questa azione
     */
    public void onResetClicked(MouseEvent mouseEvent) {
        this.reset();
    }
    /**
     * Evento che gestisce la lettura delle aree da un file dentro il pc
     * @param mouseEvent click del mouse sul bottone che scaturisce l'azione
     */
    public void onMouseShapesClicked(MouseEvent mouseEvent) {
        try {
            File selectedFile = this.openFileDialogForShapes(mouseEvent);
            if (selectedFile != null) {
                this.controller.readShapeList(selectedFile);
                this.drawShapes();
                disableButton((Button)mouseEvent.getSource());
            }
        } catch (IOException | FollowMeParserException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    /**
     * Evento che gestisce l'esecuzione di una singola istruzione alla volta
     * @param mouseEvent click del mouse sul bottone che scaturisce l'azione
     */
    public void onExecuteClicked(MouseEvent mouseEvent) {
        try {
            hasToOpenTerminal();
            this.controller.nextInstruction();
            this.updateCircles();
            this.checkProgramFinished();
        }
        catch (NullPointerException e) { Platform.runLater(() -> this.showErrorAlert("Non è stato caricato nessun programma")); }
        catch (IllegalArgumentException | IOException ex) {
            this.showErrorAlert(ex.getMessage());
        }
    }

    /**
     * Metodo che chiede all'utente se vuole aprire il terminale, se non è stato già aperto
     * @throws IOException se c'è qualche problema nell'apertura dell'applicazine
     */
    private void hasToOpenTerminal() throws IOException {
        if(!this.terminalAppExecutor.isOpened() && System.getProperty("os.name").contains("Windows")) {
            Optional<ButtonType> result = this.showConfirmAlert("Terminale", "Vuoi aprire l'applicazione terminale?");
            if(result.isPresent() && result.get() == ButtonType.YES) {
                this.terminalAppExecutor.openTerminal();
            }
        }
    }

    /**
     * Evento che gestisce la lettura del programma da un file dentro il pc
     * @param mouseEvent click del mouse sul bottone che scaturisce l'azione
     */
    public void onLoadRobotsProgram(MouseEvent mouseEvent) {
        if(this.controller.getRobots().isEmpty()) {
            loadRobots(mouseEvent);
        }
        loadProgram(mouseEvent);
        if(!this.controller.getRobots().isEmpty() && !this.programTextArea.getText().isEmpty()) {
            disableButton((Button)mouseEvent.getSource());
        }
    }

    /**
     * Metodo che disabilita il bottone, così da non poterlo più cliccare
     * @param target il bottone da disabilitare
     */
    private void disableButton(Button target) {
        target.setDisable(true);
    }

    /**
     * Evento che gestice l'esecuzione di più istruzioni alla volta,
     * queste vengono effettutate in un altro thread per evitare di bloccare
     * l'interfaccia grafica
     * @param mouseEvent click del mouse sul bottone che scaturisce l'azione
     */
    public void onExecuteMultipleInstruction(MouseEvent mouseEvent) {
        try {
            hasToOpenTerminal();
        } catch (IOException e) {
            this.showErrorAlert(e.getMessage());
        }
        int numberOfInstruction = this.showInputIntegerDialog("Inserisci il numero di passi");
        if(numberOfInstruction == -1)
            numberOfInstruction = 1;
        Thread thread = new Thread(this.executeMultipleInstruction(numberOfInstruction));
        thread.start();
    }

    /**
     * Evento che gestice lo scroll della rotellina per aumentare o diminuire lo zoom
     * @param scrollEvent evento che scaturisce l'azione
     */
    public void scrollGroupEvent(ScrollEvent scrollEvent) {
        scrollEvent.consume();
        double scaleFactor = (scrollEvent.getDeltaY() > 0) ? 1.1 : 1 / 1.1;
        Scale scale = new Scale();
        scale.setPivotX(scrollEvent.getX());
        scale.setPivotY(scrollEvent.getY());
        scale.setX(this.group.getScaleX() * scaleFactor);
        scale.setY(this.group.getScaleY() * scaleFactor);
        this.group.getTransforms().add(scale);
    }

    /**
     * Evento che gestisce la pressione dei tasti per muovere la visuale
     * @param keyEvent evento che scaturisce l'azione
     */
    public void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT, A -> scrollLeft();
            case RIGHT, D -> scrollRight();
            case UP, W -> scrollUp();
            case DOWN, S -> scrollDown();
        }
    }

    /**
     * Evento che andrà ad aprire un'applicazione chiamata Terminal, che mostrerà
     * tutti i log dei vari robot, l'applicazione è stata scritta in C# in ausilio del framework
     * .NET MAUI, <a href="https://github.com/criss02-cs/Terminal">clicca qui</a> per vedere il codice su GitHub
     * @param mouseEvent l'evento che scaturisce l'azione
     */
    public void onShowTerminalClicked(MouseEvent mouseEvent) {
        try {
            if(!this.terminalAppExecutor.isOpened()) {
                this.terminalAppExecutor.openTerminal();
            } else {
                this.showInformationAlert("Attenzione", "L'applicazione terminale \u00e8 stata gi\u00e0 aperta");
            }
        } catch (IOException e) {
            this.showErrorAlert(e.getMessage());
        }
    }
    //endregion

    //region SCROLL HANDLER

    /**
     * Metodo utilizzato per muovere la visuale verso sinistra
     */
    private void scrollLeft() {
        this.translate.setX(translate.getX() + 10);
    }
    /**
     * Metodo utilizzato per muovere la visuale verso destra
     */
    private void scrollRight() {
        this.translate.setX(translate.getX() - 10);
    }
    /**
     * Metodo utilizzato per muovere la visuale verso l'alto
     */
    private void scrollUp() {
        this.translate.setY(translate.getY() + 10);
    }
    /**
     * Metodo utilizzato per muovere la visuale verso il basso
     */
    private void scrollDown() {
        this.translate.setY(translate.getY() - 10);
    }
    //endregion

    //region THREADS

    /**
     * Metodo che restituisce il thread da eseguire per eseguire
     * più istruzioni alla volta
     * @param n il numero d'istruzioni da eseguire
     * @return l'istanza runnable da eseguire
     */
    private Runnable executeMultipleInstruction(int n) {
        return () -> {
            try {
                for (int i = 0; i < n; i++) {
                    this.controller.nextInstruction();
                    Platform.runLater(this::updateCircles);
                    Thread.sleep(1000); // Pausa di un secondo (1000 millisecondi)
                }
                Platform.runLater(this::checkProgramFinished);
            }
            catch (NullPointerException e) { Platform.runLater(() -> this.showErrorAlert("Non è stato caricato nessun programma")); }
            catch (InterruptedException | IllegalArgumentException e) { Platform.runLater(() -> this.showErrorAlert(e.getMessage())); }
        };
    }

    //endregion

    //region LOAD ELEMENTS

    /**
     * Metodo che si occupa di caricare i robot dello swarm.
     * Prima chiederà all'utente se vuole generarli casualmente, oppure caricarli da un file
     * @param mouseEvent l'evento che scaturisce questa azione
     */
    private void loadRobots(MouseEvent mouseEvent) {
        Optional<ButtonType> result = this.showConfirmAlert("Caricamento robot", "Vuoi genererare randomicamente i robot?");
        if(result.isEmpty()) {
            return;
        }
        if (result.get() == ButtonType.YES) {
            loadRandomRobots(mouseEvent);
        } else if (result.get() == ButtonType.NO) {
            loadRobotFromFile(mouseEvent);
        }
        this.drawRobots();
    }

    /**
     * Metodo che genera i robot in maniera casuale, se il numero di robot è uguale a -1
     * chiedera di caricarli da un file
     * @param mouseEvent l'evento che scaturisce questa azione
     */
    private void loadRandomRobots(MouseEvent mouseEvent) {
        int numberOfRobots = this.showInputIntegerDialog("Quanti robot vuoi generare?");
        if(numberOfRobots > -1) {
            this.controller.generateRandomRobots(numberOfRobots, new Point(-(pane.getWidth() / 2), -(pane.getHeight())),
                    new Point(pane.getWidth() / 2, pane.getHeight() / 2));
        } else {
            loadRobotFromFile(mouseEvent);
        }
    }

    /**
     * Metodo che carica i robot da un file presente nel pc dell'utente
     * @param mouseEvent l'evento che scaturisce questa azione
     */
    private void loadRobotFromFile(MouseEvent mouseEvent) {
        try {
            this.controller.getRobots().clear();
            File selectedFile = this.openFileDialogForRobots(mouseEvent);
            if(selectedFile != null) {
                this.controller.readRobotList(selectedFile);
            }
        } catch (IOException e) {
            this.showErrorAlert(e.getMessage());
        }
    }

    /**
     * Metodo che carica il programma da un file di presente nel pc dell'utente
     * @param mouseEvent l'evento che scaturisce questa azione
     */
    private void loadProgram(MouseEvent mouseEvent){
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
    //endregion
}
