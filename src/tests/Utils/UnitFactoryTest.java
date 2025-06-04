package Utils;

import DomainEntities.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UnitFactoryTest {

    @Test
    public void testGetDefaultPlayers_NotEmpty() {
        List<Player> players = UnitFactory.getDefaultPlayers();
        assertFalse(players.isEmpty());
    }

    @Test
    public void testGetMonsterByTile_Valid() {
        Monster monster = UnitFactory.getMonsterByTile('s'); // Lannister Soldier
        assertNotNull(monster);
        assertEquals('s', monster.getTileChar());
    }

    @Test
    public void testGetMonsterByTile_Invalid() {
        Monster monster = UnitFactory.getMonsterByTile('!');
        assertNull(monster);
    }

    @Test
    public void testGetTrapByTile_Valid() {
        Trap trap = UnitFactory.getTrapByTile('Q'); // Queen’s Trap
        assertNotNull(trap);
        assertEquals('Q', trap.getTileChar());
    }

    @Test
    public void testGetTrapByTile_Invalid() {
        Trap trap = UnitFactory.getTrapByTile('?');
        assertNull(trap);
    }
}
