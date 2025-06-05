package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BossTest {

    private Boss boss;
    private DummyPlayer player;
    private StringBuilder output;

    @Before
    public void setUp() {
        boss = new Boss("Big Boss", 'B', 500, 50, 10, 100, 5, 2);
        output = new StringBuilder();
        boss.initialize(msg -> output.append(msg).append("\n"));

        player = new DummyPlayer("Hero", 200);
        player.setPosition(new Position(1, 0));
        boss.setPosition(new Position(0, 0));
    }

    @Test
    public void testOnEnemyTurn_AttackTriggeredAfterEnoughTicks() {
        boss.onEnemyTurn(player); // tick 1 → combatTicks = 1
        boss.onEnemyTurn(player); // tick 2 → combatTicks = 2
        Direction dir = boss.onEnemyTurn(player); // tick 3 → should cast and reset

        String log = output.toString();
        assertTrue("Expected castAbility message, but got:\n" + log, log.contains("cast Shoebodybop"));
        assertEquals(Direction.STAY, dir); // ← check STAY here, not on the 4th call
    }


    @Test
    public void testOnEnemyTurn_ApproachesPlayer() {
        Boss closeBoss = new Boss("Mini Boss", 'M', 100, 10, 5, 10, 5, 5);
        closeBoss.initialize(msg -> {});
        closeBoss.setPosition(new Position(0, 0));
        player.setPosition(new Position(2, 0));

        Direction dir = closeBoss.onEnemyTurn(player); // tick 1
        assertEquals(Direction.RIGHT, dir); // moving towards player
    }

    @Test
    public void testOnEnemyTurn_TooFar_RandomMoveAndResetTick() {
        player.setPosition(new Position(100, 100));
        boss.onEnemyTurn(player); // too far
        assertEquals(0, getCombatTicksViaReflection(boss));
    }

    // Reflection helper to read private field (only for test purpose)
    private int getCombatTicksViaReflection(Boss boss) {
        try {
            var field = Boss.class.getDeclaredField("combatTicks");
            field.setAccessible(true);
            return field.getInt(boss);
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
            return -1;
        }
    }

    // DummyPlayer as a lightweight test double
    private static class DummyPlayer extends Player {
        public DummyPlayer(String name, int hp) {
            super(name, hp, 10, 5);
            initialize(msg -> {});
        }

        @Override
        public void onGameTick() {}

        @Override
        public void castAbility(Player p) {}

        @Override
        public String description() {
            return "Dummy";
        }

        @Override
        public int rollDefense() {
            return 5;
        }

        @Override
        public void takeDamage(int dmg) {
            super.takeDamage(dmg);
        }
    }
}
