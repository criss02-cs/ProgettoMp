package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.api.models.IShape;
import it.unicam.cs.mp.progettoesame.api.models.Point;
import it.unicam.cs.mp.progettoesame.api.models.Robot;
import it.unicam.cs.mp.progettoesame.api.utils.ShapeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;

import java.util.List;

public class Main {
    public static void main(String[] args) throws FollowMeParserException {
        FollowMeParser parser = new FollowMeParser(null);
        List<ShapeData> shapeData = parser.parseEnvironment("pippo CIRCLE 5 5 5");
        ShapeParser shapeParser = new ShapeParser();
        List<IShape> shapes = shapeData.stream().map(shapeParser::parseFromShapeData).toList();
    }
}