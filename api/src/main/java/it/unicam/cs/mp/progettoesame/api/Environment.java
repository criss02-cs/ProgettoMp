package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Environment implements IEnvironment {
    private List<Robot> robots;
    private List<ShapeData> shapes;

    public Environment() {
        robots = new LinkedList<>();
        shapes = new LinkedList<>();
    }

    public List<ShapeData> getShapes() {
        return shapes;
    }

    public void loadEnvironment(List<ShapeData> data) {
        this.shapes = data;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void setRobots(List<Robot> robots) {
        this.robots = robots;
    }
}
