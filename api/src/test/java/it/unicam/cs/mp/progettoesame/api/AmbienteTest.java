package it.unicam.cs.mp.progettoesame.api;

import it.unicam.cs.mp.progettoesame.utilities.FollowMeParser;
import it.unicam.cs.mp.progettoesame.utilities.FollowMeParserHandler;
import org.junit.jupiter.api.Test;

public class AmbienteTest {
    private FollowMeParserHandler handler = new ParserHandler();
    @Test void testLoadAmbiente() {
        FollowMeParser parser = new FollowMeParser(handler);
    }
}
