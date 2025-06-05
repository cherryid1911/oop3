package UI;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class GameEngineTest {

    @Test
    public void testSendOutputsToConsole() {
        GameEngine engine = new GameEngine();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        engine.send("Hello from test");

        System.setOut(originalOut); // restore output
        assertTrue(out.toString().contains("Hello from test"));
    }
}
