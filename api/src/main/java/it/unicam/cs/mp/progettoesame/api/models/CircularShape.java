package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.Tuple;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

/**
 * Classe che definisce una figura circolare
 */
public class CircularShape extends Shape {
    private final double radius;

    public CircularShape(double radius, Point position, String label) {
        super(position, label);
        this.radius = radius;
    }

    @Override
    public Tuple<Double, Double> getDimensions() {
        return Tuple.of(radius, -1.0);
    }
}
