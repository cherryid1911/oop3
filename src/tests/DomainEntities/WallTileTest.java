package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WallTileTest {

    private WallTile wallTile;
    private Position pos;

    @Before
    public void setUp() {
        pos = new Position(2, 3);
        wallTile = new WallTile(pos);
    }

    @Test
    public void testToString() {
        assertEquals("#", wallTile.toString());
    }

    @Test
    public void testGetTileChar() {
        assertEquals('#', wallTile.getTileChar());
    }

    @Test
    public void testGetPosition() {
        assertEquals(pos, wallTile.getPosition());
    }

    @Test
    public void testAcceptCallsVisitOnUnit() {
        TestVisitor unit = new TestVisitor();
        wallTile.accept(unit);
        assertTrue(unit.visitedWall);
        assertEquals(pos, unit.visitedPosition);
    }

    private static class TestVisitor extends Unit {
        boolean visitedWall = false;
        Position visitedPosition = null;

        public TestVisitor() {
            super("TestUnit", '@', new Position(0, 0), 10, 1, 1);
        }

        @Override public void visit(WallTile w) {
            visitedWall = true;
            visitedPosition = w.getPosition();
        }

        @Override public void onGameTick() {}
        @Override public String description() { return "test"; }
        @Override public void accept(Unit other) {}
        @Override public void visit(Player p) {}
        @Override public void visit(Monster m) {}
        @Override public void visit(Trap t) {}
        @Override public void visit(EmptyTile e) {}
    }
}
