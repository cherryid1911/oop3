package DomainEntities;

import org.junit.Test;

import static org.junit.Assert.*;

public class WallTileTest {

    @Test
    public void testToStringReturnsHash() {
        WallTile wall = new WallTile(new Position(3, 4));
        assertEquals("#", wall.toString());
    }

    @Test
    public void testAcceptCallsVisit() {
        WallTile wall = new WallTile(new Position(2, 2));
        DummyUnit dummy = new DummyUnit();

        wall.accept(dummy);

        assertTrue("Expected visit(WallTile) to be called", dummy.visitedWall);
    }

    // Dummy unit to verify visit is triggered
    private static class DummyUnit extends Unit {
        boolean visitedWall = false;

        public DummyUnit() {
            super("Dummy", '@', new Position(0, 0), 10, 5, 2);
        }

        @Override
        public void visit(WallTile wall) {
            visitedWall = true;
        }

        @Override public void visit(EmptyTile tile) {}
        @Override public void visit(Player player) {}
        @Override public void visit(Monster monster) {}
        @Override public void visit(Trap trap) {}
        @Override public void onGameTick() {}
        @Override public String description() { return "Dummy"; }
        @Override public void accept(Visitor visitor) {}
    }
}