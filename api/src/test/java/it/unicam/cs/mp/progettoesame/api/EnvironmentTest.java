package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserException;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import it.unicam.cs.mp.progettoesame.utilities.ShapeData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class EnvironmentTest {
    private FollowMeParserHandler handler = new ParserHandler();
    @Test void testLoadAmbiente() throws FollowMeParserException {
        FollowMeParser parser = new FollowMeParser(handler);
        String lines = "stop CIRCLE 10 5 2";
        List<ShapeData> data = parser.parseEnvironment(lines);
        Environment a = new Environment();
        a.loadEnvironment(data);
        assertEquals(data, a.getShapes());
    }
}
