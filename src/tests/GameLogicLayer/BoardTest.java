package GameLogicLayer;

import DomainEntities.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private Position pos;

    @Before
    public void setUp() {
        board = new Board(5, 5);
        pos = new Position(2, 3);
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(5, board.getRows());
        assertEquals(5, board.getCols());
    }

    @Test
    public void testSetAndGetTile() {
        Tile wall = new WallTile(pos);
        board.setTile(pos, wall);
        Tile retrieved = board.getTile(pos);
        assertSame("Expected same tile instance", wall, retrieved);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testOutOfBoundsGet() {
        board.getTile(new Position(10, 10));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testOutOfBoundsSet() {
        board.setTile(new Position(-1, 0), new EmptyTile(new Position(-1, 0)));
    }
}