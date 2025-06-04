package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MonsterTest {

    private Monster monster;
    private Player dummyPlayer;

    @Before
    public void setUp() {
        monster = new Monster("Orc", 'O', new Position(5, 5), 100, 20, 10, 50, 3);
        dummyPlayer = new DummyPlayer(new Position(6, 5));
        monster.initialize(new MockMessageCallback());
    }

    @Test
    public void testConstructorValues() {
        assertEquals("Orc", monster.getName());
        assertEquals('O', monster.getTileChar());
        assertEquals(3, monster.getVisionRange());
        assertEquals(50, monster.getExperienceValue());
    }

    @Test
    public void testDescriptionIncludesStats() {
        String desc = monster.description();
        assertTrue(desc.contains("Monster Orc"));
        assertTrue(desc.contains("HP:"));
        assertTrue(desc.contains("Vision:"));
    }

    @Test
    public void testAcceptDelegatesToVisitor() {
        TestVisitor visitor = new TestVisitor();
        monster.accept(visitor);
        assertTrue(visitor.visitedMonster);
    }

    @Test
    public void testDecideMoveDirection_TowardPlayer() {
        Direction dir = monster.decideMoveDirection(dummyPlayer);
        assertEquals(Direction.RIGHT, dir);
    }

    @Test
    public void testDecideMoveDirection_RandomOutsideRange() {
        dummyPlayer.setPosition(new Position(100, 100));
        Direction dir = monster.decideMoveDirection(dummyPlayer);
        assertNotNull(dir);
    }

    private static class DummyPlayer extends Player {
        public DummyPlayer(Position pos) {
            super("Hero", pos, 100, 10, 5);
        }

        public void setPosition(Position pos) {
            this.position = pos;
        }

        @Override public void castAbility() {}
        @Override public void onGameTick() {}
        @Override public String description() { return "Dummy"; }
    }

    private static class TestVisitor extends Unit {
        boolean visitedMonster = false;

        public TestVisitor() {
            super("Visitor", '@', new Position(0, 0), 10, 1, 1);
        }

        @Override public void visit(Monster m) { visitedMonster = true; }
        @Override public void onGameTick() {}
        @Override public String description() { return ""; }
        @Override public void accept(Unit o) {}
        @Override public void visit(Player p) {}
        @Override public void visit(Trap t) {}
        @Override public void visit(WallTile w) {}
        @Override public void visit(EmptyTile e) {}
    }

    private static class MockMessageCallback implements MessageCallback {
        @Override public void send(String message) {}
    }
}
