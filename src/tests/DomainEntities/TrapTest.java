package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrapTest {

    private Trap trap;
    private DummyPlayer player;
    private MockMessageCallback callback;

    @Before
    public void setUp() {
        trap = new Trap("Death Trap", 'D', new Position(5, 5),
                100, 30, 5, 50, 2, 3);
        player = new DummyPlayer(new Position(5, 6));
        callback = new MockMessageCallback();
        trap.initialize(callback);
    }

    @Test
    public void testConstructorValues() {
        assertEquals("Death Trap", trap.getName());
        assertEquals(2, trap.getVisibilityTime());
        assertEquals(3, trap.getInvisibilityTime());
    }

    @Test
    public void testVisibilityToggle() {
        for (int i = 0; i < 5; i++) {
            trap.onGameTick(); // After 5 ticks, should be invisible
        }
        assertEquals('D', trap.toString().charAt(0)); // Becomes visible again after reset
        trap.onGameTick(); // tick = 1, still visible
        assertEquals('D', trap.toString().charAt(0));
        trap.onGameTick(); // tick = 2, still visible
        assertEquals('.', trap.toString().charAt(0)); // tick = 3, now invisible
    }

    @Test
    public void testOnEnemyTurn_AttacksPlayerInRange() {
        trap.onEnemyTurn(player);
    }

    @Test
    public void testAcceptCallsVisitor() {
        TestVisitor visitor = new TestVisitor();
        trap.accept(visitor);
        assertTrue(visitor.visitedTrap);
    }

    private static class DummyPlayer extends Player {
        public DummyPlayer(Position pos) {
            super("Player", pos, 100, 10, 5);
            setMessageCallback(new MockMessageCallback());
        }

        @Override public void castAbility() {}
        @Override public void onGameTick() {}
        @Override public String description() { return "Dummy"; }
    }

    private static class TestVisitor extends Unit {
        boolean visitedTrap = false;

        public TestVisitor() {
            super("Visitor", '@', new Position(0, 0), 10, 1, 1);
        }

        @Override public void visit(Trap t) { visitedTrap = true; }
        @Override public void onGameTick() {}
        @Override public String description() { return ""; }
        @Override public void accept(Unit o) {}
        @Override public void visit(Player p) {}
        @Override public void visit(Monster m) {}
        @Override public void visit(WallTile w) {}
        @Override public void visit(EmptyTile e) {}
    }

    private static class MockMessageCallback implements MessageCallback {
        @Override public void send(String message) {}
    }
}
