package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrapTest {

    private Trap trap;
    private DummyPlayer player;
    private StringBuilder output;

    @Before
    public void setUp() {
        trap = new Trap("Spiky", '^', 50, 20, 5, 15, 3, 2); // 3 visible, 2 invisible
        output = new StringBuilder();
        trap.initialize(msg -> output.append(msg).append("\n"));

        trap.setPosition(new Position(0, 0));
        player = new DummyPlayer("Adventurer", 100);
        player.setPosition(new Position(1, 0));
    }

    @Test
    public void testVisibilityCycle() {
        // Initially visible
        assertEquals("^", trap.toString());

        // Advance time: 2 visible ticks
        trap.onGameTick(); // tick 1
        assertEquals("^", trap.toString());
        trap.onGameTick(); // tick 2
        assertEquals("^", trap.toString());

        trap.onGameTick(); // tick 3 — first invisible tick
        assertEquals(".", trap.toString());
        trap.onGameTick(); // tick 4 — still invisible
        assertEquals(".", trap.toString());

        trap.onGameTick(); // tick 5 — resets to 0 → visible again
        assertEquals("^", trap.toString());
    }

    @Test
    public void testOnEnemyTurnAttacksPlayer() {
        trap.onEnemyTurn(player); // player within 1 distance

        String log = output.toString();
        assertTrue(log.contains("attacked"));
        assertTrue(log.contains("Adventurer"));
    }

    @Test
    public void testTrapAlwaysStays() {
        Direction dir = trap.onEnemyTurn(player);
        assertEquals(Direction.STAY, dir);
    }

    // DummyPlayer
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
            return 3;
        }

        @Override
        public void takeDamage(int dmg) {
            super.takeDamage(dmg);
        }
    }
}
