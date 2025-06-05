package DomainEntities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    @Test
    public void testGetXAndY() {
        Position pos = new Position(3, 7);
        assertEquals(3, pos.getX());
        assertEquals(7, pos.getY());
    }

    @Test
    public void testDistanceToItself() {
        Position pos = new Position(5, 5);
        assertEquals(0.0, pos.distance(pos), 0.0001);
    }

    @Test
    public void testDistanceToAnotherPosition() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(3, 4);
        assertEquals(5.0, pos1.distance(pos2), 0.0001);
    }

    @Test
    public void testOffset() {
        Position pos = new Position(2, 3);
        Position offsetPos = pos.offset(1, -1);
        assertEquals(new Position(3, 2), offsetPos);
        assertEquals(2, pos.getX()); // Ensure immutability
        assertEquals(3, pos.getY());
    }

    @Test
    public void testEqualsAndToString() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        Position p3 = new Position(2, 1);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals("(1,2)", p1.toString());
    }
}
