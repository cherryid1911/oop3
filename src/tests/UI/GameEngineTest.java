package UI;

import DomainEntities.*;
import Utils.UnitFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.Assert.*;

public class GameEngineTest {

    private GameEngine engine;

    @Before
    public void setUp() {
        engine = new GameEngine();
    }

    @Test
    public void testSendOutputsToConsole() {
        engine.send("Hello");
    }

    @Test
    public void testChoosePlayer_SelectsValidPlayer() {
        List<Player> allPlayers = UnitFactory.getDefaultPlayers();
        assertFalse(allPlayers.isEmpty());

        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        GameEngine localEngine = new GameEngine();
        localEngine.enableTestMode();
        localEngine.run();
    }
}
