package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;

public class Main {
    public static void main(String[] args) {
        ParserHandler handler = new ParserHandler();
        IEnvironment environment = new Environment();
        handler.setEnvironment(environment);
        Robot robot1 = new Robot();
        robot1.setPosition(new Point(5, 5));
        environment.getRobots().add(robot1);
        handler.moveRandomCommand(new double[]{10, 20, 10, 20, 5});
        System.out.println("Position: " + robot1.getPosition());
    }
}