package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyTileTest {

    private EmptyTile emptyTile;
    private TestUnit unit;
    private Position initialPosition;
    private Position emptyTilePosition;

    @Before
    public void setUp() {
        emptyTilePosition = new Position(2, 2);
        initialPosition = new Position(0, 0);
        emptyTile = new EmptyTile(emptyTilePosition);
        unit = new TestUnit(initialPosition);
    }

    @Test
    public void testVisitMovesUnitToEmptyTile() {
        emptyTile.accept(unit);
        assertEquals(emptyTilePosition, unit.getPosition());
    }

    private static class TestUnit extends Unit {
        public TestUnit(Position pos) {
            super("Mover", '@', pos, 100, 10, 5);
        }

        @Override public void visit(EmptyTile e) {
            setPosition(e.getPosition());
        }

        @Override public void onGameTick() {}
        @Override public String description() { return "test unit"; }
        @Override public void accept(Unit o) {}
        @Override public void visit(Player p) {}
        @Override public void visit(Monster m) {}
        @Override public void visit(Trap t) {}
        @Override public void visit(WallTile w) {}
    }
}
