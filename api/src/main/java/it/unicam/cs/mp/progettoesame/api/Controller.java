package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Robot;

import java.awt.event.MouseEvent;
import java.util.List;

public class Controller {
    private List<Robot> robots;
    private List<IShape> shapes;
    public Controller(List<Robot> robots, List<IShape> shapes) {
        this.robots = robots;
        this.shapes = shapes;
    }

    public void loadRobots(List<Robot> robots) { this.robots = robots; }
    public void loadShapes(List<IShape> shapes) { this.shapes = shapes; }

    public void loadProgramToRobots(Program program) {
        this.robots.forEach(x -> x.loadProgramToExecute(program));
    }

    public void nextInstruction() {
        this.robots.forEach(x -> {
            if(!x.isProgramTerminated()) {
                x.executeNextInstruction();
            }
        });
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public List<IShape> getShapes() {
        return shapes;
    }

}
