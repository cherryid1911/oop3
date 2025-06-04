package GameLogicLayer;

import DomainEntities.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameLevelTest {

    private GameLevel level;
    private Board board;
    private TestPlayer player;
    private TestEnemy enemy;
    private Position playerPos;
    private Position enemyPos;

    @Before
    public void setUp() {
        playerPos = new Position(1, 1);
        enemyPos = new Position(2, 1);
        board = new Board(3, 3);
        player = new TestPlayer("Tester", playerPos, 100, 10, 5);
        player.setMessageCallback(msg -> {});
        enemy = new TestEnemy("Goblin", 'G', enemyPos, 30, 5, 2, 10);
        enemy.initialize(msg -> {});

        board.setTile(playerPos, player);
        board.setTile(enemyPos, enemy);

        level = new GameLevel(board, player, List.of(enemy), msg -> {});
    }

    @Test
    public void testIsLevelCompleteInitiallyFalse() {
        assertFalse(level.isLevelComplete());
    }

    @Test
    public void testPlayerMovesToEmptyTile() {
        Position emptyPos = new Position(1, 2);
        board.setTile(emptyPos, new EmptyTile(emptyPos));

        level.gameTick('s'); // Move down
        assertEquals(emptyPos.getX(), player.getPosition().getX());
        assertEquals(emptyPos.getY(), player.getPosition().getY());
    }

    @Test
    public void testPlayerCastsAbilityWithoutError() {
        level.gameTick('e'); // Should call castAbility (empty override)
    }

    @Test
    public void testInvalidInputHandled() {
        level.gameTick('x'); // Should not crash
    }
}
