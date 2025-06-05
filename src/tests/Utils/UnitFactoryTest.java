package Utils;

import DomainEntities.Monster;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitFactoryTest {

    @Test
    public void testGetMonsterByTile() {
        Monster m = UnitFactory.getMonsterByTile('s');
        assertNotNull("Monster with tile 's' should exist", m);
        assertEquals("Lannister Soldier", m.getName());

        Monster none = UnitFactory.getMonsterByTile('!');
        assertNull("Invalid tile should return null", none);
    }
}
