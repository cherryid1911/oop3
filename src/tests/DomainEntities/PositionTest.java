package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    private Position p1;
    private Position p2;

    @Before
    public void setUp() {
        p1 = new Position(3, 4);
        p2 = new Position(6, 8);
    }

    @Test
    public void testGetXandY() {
        assertEquals(3, p1.getX());
        assertEquals(4, p1.getY());
    }

    @Test
    public void testDistance() {
        double dist = p1.distance(p2);
        assertEquals(5.0, dist, 0.001);
    }

    @Test
    public void testOffset() {
        Position offset = p1.offset(2, -1);
        assertEquals(new Position(5, 3), offset);
    }

    @Test
    public void testEqualsTrue() {
        Position other = new Position(3, 4);
        assertTrue(p1.equals(other));
    }

    @Test
    public void testEqualsFalse() {
        Position other = new Position(4, 4);
        assertFalse(p1.equals(other));
    }

    @Test
    public void testToString() {
        assertEquals("(3,4)", p1.toString());
    }
}
