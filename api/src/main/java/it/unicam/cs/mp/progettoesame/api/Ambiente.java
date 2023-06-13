package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ambiente implements IEnvironment {
    private Map<Robot, Point> robots;
    private List<ShapeData> shapes;

    public Ambiente() {
        robots = new HashMap<>();
        shapes = new LinkedList<>();
    }

    public List<ShapeData> getShapes() {
        return shapes;
    }

    public void loadEnvironment(List<ShapeData> data) {
        this.shapes = data;
    }

    public Map<Robot, Point> getRobots() {
        return robots;
    }

    public void setRobots(Map<Robot, Point> robots) {
        this.robots = robots;
    }
}
