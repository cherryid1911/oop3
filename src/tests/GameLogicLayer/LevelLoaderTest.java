package GameLogicLayer;

import DomainEntities.*;
import Utils.UnitFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class LevelLoaderTest {

    private Player testPlayer;

    @Before
    public void setUp() {
        testPlayer = new Warrior("TestWarrior", 100, 10, 5, 1);
    }

    @Test
    public void testLoadValidLevel() throws IOException {
        // Create a temporary level file
        String levelContent = """
                .....
                ..@..
                ..s..
                ..#..
                .....""";

        Path tempLevelPath = Files.createTempFile("testLevel", ".txt");
        Files.writeString(tempLevelPath, levelContent);

        LevelLoader.LoadedLevel loaded = LevelLoader.loadLevel(tempLevelPath, testPlayer);

        assertNotNull(loaded);
        assertEquals(5, loaded.getBoard().getRows());
        assertEquals(5, loaded.getBoard().getCols());

        Tile playerTile = loaded.getBoard().getTile(loaded.getPlayerPos());
        assertSame(testPlayer, playerTile);

        List<Enemy> enemies = loaded.getEnemies();
        assertEquals(1, enemies.size());
        assertEquals('s', enemies.get(0).getTileChar());

        Files.deleteIfExists(tempLevelPath);
    }
}
