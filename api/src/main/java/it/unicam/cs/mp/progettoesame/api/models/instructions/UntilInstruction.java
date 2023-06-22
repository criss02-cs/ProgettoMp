package it.unicam.cs.mp.progettoesame.api.models.instructions;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.DistanceCalculator;
import it.unicam.cs.mp.progettoesame.api.utils.NumericRangeChecker;

import java.util.List;

public class UntilInstruction implements RobotInstruction {
    private final String labelToFind;
    private final List<IShape> shapes;
    private boolean hasToExit;

    public UntilInstruction(String labelToFind, List<IShape> shapes) {
        this.labelToFind = labelToFind;
        this.shapes = shapes;
        this.hasToExit = false;
    }

    @Override
    public void execute(Robot robot) {
        IShape shape = this.shapes.stream().filter(x -> x.getLabel().equalsIgnoreCase(this.labelToFind)).findFirst().orElse(null);
        if(shape != null) {
            if(shape.getDimensions().getItem2() == -1) {
                this.checkCircularShape(robot, shape);
            } else {
                this.checkRectangularShape(robot, shape);
            }
        }
    }

    private void checkRectangularShape(Robot robot, IShape shape) {
        double width = shape.getDimensions().getItem1();
        double height = shape.getDimensions().getItem2();
        Point topLeft = new Point(shape.getCoordinates().getX() - (width / 2), shape.getCoordinates().getY() - (height / 2));
        Point bottomRight = new Point(shape.getCoordinates().getX() + (width / 2), shape.getCoordinates().getY() + (height / 2));
        this.hasToExit = NumericRangeChecker.DEFAULT_CHECKER.isBetween(robot.getPosition().getX(), topLeft.getX(), bottomRight.getX()) &&
                NumericRangeChecker.DEFAULT_CHECKER.isBetween(robot.getPosition().getY(), topLeft.getY(), bottomRight.getY());
    }

    private void checkCircularShape(Robot robot, IShape shape) {
        double radius = shape.getDimensions().getItem1();
        double distance = DistanceCalculator.calculate(robot.getPosition(), shape.getCoordinates());
        this.hasToExit = distance <= radius;
    }

    @Override
    public int canGoToNextInstruction() {
        return this.hasToExit ? -1 : 0;
    }

    @Override
    public RobotInstruction clone() {
        return new UntilInstruction(this.labelToFind, this.shapes);
    }


}
