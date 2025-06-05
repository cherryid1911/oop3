package DomainEntities;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyTileTest {

    @Test
    public void testToStringReturnsDot() {
        EmptyTile empty = new EmptyTile(new Position(1, 2));
        assertEquals(".", empty.toString());
    }

    @Test
    public void testAcceptCallsVisit() {
        EmptyTile empty = new EmptyTile(new Position(0, 0));
        DummyUnit dummy = new DummyUnit();

        empty.accept(dummy);

        assertTrue("Expected visit(EmptyTile) to be called", dummy.visitedEmpty);
        assertEquals(empty.getPosition(), dummy.getPosition()); // position should be updated
    }

    // Dummy Unit to test accept/visit
    private static class DummyUnit extends Unit {
        boolean visitedEmpty = false;

        public DummyUnit() {
            super("TestUnit", '@', new Position(0, 0), 10, 5, 2);
        }

        @Override
        public void visit(EmptyTile tile) {
            this.visitedEmpty = true;
            this.setPosition(tile.getPosition());
        }

        // Stub everything else
        @Override public void visit(Player p) {}
        @Override public void visit(Monster m) {}
        @Override public void visit(Trap t) {}
        @Override public void onGameTick() {}
        @Override public String description() { return "Dummy"; }
        @Override public void accept(Unit other) {}
    }
}
