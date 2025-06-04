package GameLogicLayer;

import DomainEntities.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private Position pos;
    private EmptyTile empty;
    private WallTile wall;

    @Before
    public void setUp() {
        board = new Board(3, 3);
        pos = new Position(1, 1);
        empty = new EmptyTile(pos);
        wall = new WallTile(pos);
    }

    @Test
    public void testGetRowsAndCols() {
        assertEquals(3, board.getRows());
        assertEquals(3, board.getCols());
    }

    @Test
    public void testSetAndGetTile() {
        board.setTile(pos, empty);
        assertEquals(empty, board.getTile(pos));

        board.setTile(pos, wall);
        assertEquals(wall, board.getTile(pos));
    }
}
