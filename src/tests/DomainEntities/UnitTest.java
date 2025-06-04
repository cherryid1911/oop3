package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    private TestUnit unit;

    @Before
    public void setUp() {
        unit = new TestUnit("Tester", '@', new Position(2, 3), 100, 20, 10);
    }

    @Test
    public void testGetters() {
        assertEquals("Tester", unit.getName());
        assertEquals('@', unit.getTileChar());
        assertEquals(100, unit.getHealthPool());
        assertEquals(100, unit.getCurrentHealth());
        assertEquals(20, unit.getAttack());
        assertEquals(10, unit.getDefense());
        assertEquals(new Position(2, 3), unit.getPosition());
    }

    @Test
    public void testSetPosition() {
        Position newPos = new Position(5, 5);
        unit.setPosition(newPos);
        assertEquals(newPos, unit.getPosition());
    }

    @Test
    public void testTakeDamage() {
        unit.takeDamage(30);
        assertEquals(70, unit.getCurrentHealth());
    }

    @Test
    public void testTakeDamageCannotGoBelowZero() {
        unit.takeDamage(999);
        assertEquals(0, unit.getCurrentHealth());
    }

    @Test
    public void testIsDead() {
        assertFalse(unit.isDead());
        unit.takeDamage(100);
        assertTrue(unit.isDead());
    }

    @Test
    public void testRollAttackWithinBounds() {
        for (int i = 0; i < 100; i++) {
            int roll = unit.rollAttack();
            assertTrue(roll >= 0 && roll <= 20);
        }
    }

    @Test
    public void testRollDefenseWithinBounds() {
        for (int i = 0; i < 100; i++) {
            int roll = unit.rollDefense();
            assertTrue(roll >= 0 && roll <= 10);
        }
    }

    @Test
    public void testToStringReturnsTileChar() {
        assertEquals("@", unit.toString());
    }
}
