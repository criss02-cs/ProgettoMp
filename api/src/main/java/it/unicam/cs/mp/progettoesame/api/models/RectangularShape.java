package it.unicam.cs.mp.progettoesame.api.models;

import it.unicam.cs.mp.progettoesame.api.utils.Tuple;

/**
 * Classe che definisce una figura rettangolare
 */
public class RectangularShape extends Shape {
    /**
     * Larghezza del rettangolo
     */
    private final double width;
    /**
     * Altezza del rettangolo
     */
    private final double height;

    public RectangularShape(double width, double height, Point position, String label) {
        super(position, label);
        this.width = width;
        this.height = height;
    }

    public RectangularShape(double width, double height, Point position) {
        this(width, height, position, "");
    }

    public RectangularShape(Tuple<Double, Double> dimensions, Point position) {
        this(dimensions.getItem1(), dimensions.getItem2(), position);
    }
    public RectangularShape(Tuple<Double, Double> dimensions, Point position, String label) {
        this(dimensions.getItem1(), dimensions.getItem2(), position, label);
    }

    @Override
    public Tuple<Double, Double> getDimensions() {
        return Tuple.of(width, height);
    }

}
