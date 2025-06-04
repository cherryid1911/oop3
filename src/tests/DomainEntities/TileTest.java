package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    private Tile tile;
    private Position position;

    @Before
    public void setUp() {
        position = new Position(4, 2);
        tile = new TestTile('%', position);
    }

    @Test
    public void testGetTileChar() {
        assertEquals('%', tile.getTileChar());
    }

    @Test
    public void testGetPosition() {
        assertEquals(position, tile.getPosition());
    }

    private static class TestTile extends Tile {
        public TestTile(char tileChar, Position position) {
            super(tileChar, position);
        }

        @Override public void accept(Unit unit) {}
        @Override public String toString() { return String.valueOf(tileChar); }
    }
}
