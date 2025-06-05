package GameLogicLayer;

import DomainEntities.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameLevelTest {

    private GameLevel gameLevel;
    private DummyMessageCallback callback;
    private Player player;
    private Enemy dummyEnemy;
    private Board board;

    private static class DummyMessageCallback implements MessageCallback {
        StringBuilder log = new StringBuilder();
        @Override
        public void send(String message) {
            log.append(message).append("\n");
        }
        public String getLog() {
            return log.toString();
        }
    }

    @Before
    public void setUp() {
        callback = new DummyMessageCallback();
        player = new Warrior("TestHero", 100, 10, 2, 1);
        dummyEnemy = new Monster("Dummy", 'D', 1, 1, 1, 1, 1);
        player.setPosition(new Position(1, 1));
        dummyEnemy.setPosition(new Position(2, 1));
        board = new Board(3, 3);

        // Initialize tiles
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                board.setTile(new Position(x, y), new EmptyTile(new Position(x, y)));

        // Place entities
        board.setTile(player.getPosition(), player);
        board.setTile(dummyEnemy.getPosition(), dummyEnemy);

        gameLevel = new GameLevel(board, player, List.of(dummyEnemy), callback);
    }

    @Test
    public void testMovePlayerRightIntoEnemy() {
        // Move right toward dummyEnemy
        gameLevel.gameTick('d');
        assertTrue(callback.getLog().contains("engaged")); // Should engage in combat
    }

    @Test
    public void testInvalidInput() {
        gameLevel.gameTick('x');
        assertTrue(callback.getLog().contains("Invalid input"));
    }

    @Test
    public void testAbilityAndWait() {
        gameLevel.gameTick('e'); // Just test that no crash occurs
        gameLevel.gameTick('q');
        assertTrue(callback.getLog().contains("Player chose to wait."));
    }

    @Test
    public void testLevelCompletion() {
        dummyEnemy.takeDamage(1000); // kill it before tick
        gameLevel.gameTick('q');
        assertTrue(gameLevel.isLevelComplete());
    }

    @Test
    public void testDisplayBoard() {
        String display = gameLevel.display();
        assertEquals(3, display.split("\n").length);
    }
}
