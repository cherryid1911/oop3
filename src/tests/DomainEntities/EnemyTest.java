package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EnemyTest {

    private TestEnemy enemy;

    @Before
    public void setUp() {
        enemy = new TestEnemy("Goblin", 'G', new Position(0, 0), 50, 10, 5, 25);
        enemy.initialize(msg -> {});
    }

    @Test
    public void testGetExperienceValue() {
        assertEquals(25, enemy.getExperienceValue());
    }

    @Test
    public void testInitializeDoesNotCrash() {
        // If no exception is thrown, test passes
        enemy.initialize(msg -> {});
    }

    @Test
    public void testDecideMoveDirectionReturnsStay() {
        Player dummy = new TestPlayer("Dummy", new Position(1, 1), 100, 20, 10);
        Direction dir = enemy.decideMoveDirection(dummy);
        assertEquals(Direction.STAY, dir);
    }
}
