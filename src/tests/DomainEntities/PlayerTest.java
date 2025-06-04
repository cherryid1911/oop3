package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private TestPlayer player;
    private TestEnemy enemy;

    @Before
    public void setUp() {
        player = new TestPlayer("TestHero", new Position(1, 1), 100, 20, 10);
        player.setMessageCallback(msg -> {});
        enemy = new TestEnemy("TestEnemy", 'E', new Position(2, 2), 50, 5, 2, 20);
        enemy.initialize(msg -> {});
    }

    @Test
    public void testGainExperienceLevelUp() {
        assertEquals(1, player.getLevel());
        player.gainExperience(50); // Should trigger level up
        assertEquals(2, player.getLevel());
        assertTrue(player.getExperience() < 50);
    }

    @Test
    public void testEngageEnemy() {
        int healthBefore = enemy.getCurrentHealth();
        player.engage(enemy);
        assertTrue(enemy.getCurrentHealth() <= healthBefore);
    }

    @Test
    public void testDeadChar() {
        assertEquals('@', player.getTileChar());
        player.deadChar();
        assertEquals('X', player.getTileChar());
    }
}
