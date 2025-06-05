package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    private Position pos;

    @Before
    public void setUp() {
        pos = new Position(1, 1);
    }

    @Test
    public void testEmptyTileChar() {
        Tile empty = new EmptyTile(pos);
        assertEquals('.', empty.getTileChar());
    }

    @Test
    public void testWallTileChar() {
        Tile wall = new WallTile(pos);
        assertEquals('#', wall.getTileChar());
    }

    @Test
    public void testPositionIsCorrect() {
        Tile tile = new WallTile(pos);
        assertEquals(pos, tile.getPosition());
    }

    @Test
    public void testEmptyTileAcceptsPlayerDoesNotThrow() {
        Player rogue = new Rogue("Arya", 100, 30, 2, 10);
        rogue.setPosition(pos);
        rogue.initialize(message -> {}); // Dummy callback

        Tile empty = new EmptyTile(pos);

        try {
            empty.accept(rogue);
        } catch (Exception e) {
            fail("EmptyTile.accept should not throw exception when accepting Player");
        }
    }


    @Test
    public void testEmptyTileAcceptsEnemyDoesNotThrow() {
        Enemy enemy = new Monster("Zombie", 'x', 100, 10, 5, 50, 3);
        enemy.setPosition(pos);
        enemy.initialize(message -> {});

        Tile empty = new EmptyTile(pos);

        try {
            empty.accept(enemy);
        } catch (Exception e) {
            fail("EmptyTile.accept should not throw exception when accepting Enemy");
        }
    }

}
