package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ambiente {
    private Map<Object, Point> robots;
    private List<ShapeData> shapes;

    public Ambiente() {
        robots = new HashMap<>();
        shapes = new LinkedList<>();
    }
    public void loadEnvironment(List<ShapeData> data) {
        this.shapes = data;
    }
}
