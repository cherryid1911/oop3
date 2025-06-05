package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonsterTest {

    private Monster monster;
    private DummyPlayer player;
    private StringBuilder output;

    @Before
    public void setUp() {
        monster = new Monster("Zombie", 'Z', 100, 20, 5, 10, 3);
        output = new StringBuilder();
        monster.initialize(msg -> output.append(msg).append("\n"));
        monster.setPosition(new Position(0, 0));

        player = new DummyPlayer("Knight", 100);
        player.setPosition(new Position(1, 0));
    }

    @Test
    public void testOnEnemyTurn_MoveTowardsIfInRange() {
        Direction dir = monster.onEnemyTurn(player);
        assertEquals(Direction.RIGHT, dir); // (0,0) to (1,0)
    }

    @Test
    public void testOnEnemyTurn_RandomMoveIfOutOfRange() {
        player.setPosition(new Position(100, 100));
        Direction dir = monster.onEnemyTurn(player);
        assertNotNull(dir);
    }

    @Test
    public void testVisitPlayerDealsDamage() {
        monster.visit(player);
        String log = output.toString();
        assertTrue(log.contains("attacked Knight"));
    }

    @Test
    public void testDescriptionContainsCorrectInfo() {
        String desc = monster.description();
        assertTrue(desc.contains("Monster Zombie"));
        assertTrue(desc.contains("HP"));
        assertTrue(desc.contains("ATK"));
        assertTrue(desc.contains("DEF"));
        assertTrue(desc.contains("EXP"));
        assertTrue(desc.contains("Vision"));
    }

    // DummyPlayer
    private static class DummyPlayer extends Player {
        public DummyPlayer(String name, int hp) {
            super(name, hp, 10, 5);
            initialize(msg -> {});
        }

        @Override public void onGameTick() {}
        @Override public void castAbility(Player p) {}
        @Override public String description() { return "Dummy"; }
        @Override public int rollDefense() { return 0; }
    }
}
