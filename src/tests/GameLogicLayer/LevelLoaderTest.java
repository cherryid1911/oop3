package GameLogicLayer;

import DomainEntities.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelLoaderTest {

    @Test
    public void testCreateMonsterFromChar() {
        Monster m = (Monster) LevelLoaderTestHelper.createEnemyFromChar('s');
        assertNotNull(m);
        assertEquals("Lannister Soldier", m.getName());
    }

    @Test
    public void testCreateTrapFromChar() {
        Trap t = (Trap) LevelLoaderTestHelper.createEnemyFromChar('B');
        assertNotNull(t);
        assertEquals("Bonus Trap", t.getName());
    }

    @Test
    public void testCreateUnknownCharReturnsNull() {
        Enemy unknown = LevelLoaderTestHelper.createEnemyFromChar('!');
        assertNull(unknown);
    }

    // Access LevelLoader’s private method via inner static subclass
    static class LevelLoaderTestHelper extends LevelLoader {
        public static Enemy createEnemyFromChar(char c) {
            Position dummyPos = new Position(0, 0);
            return LevelLoader.createEnemyFromChar(c, dummyPos);
        }
    }
}
