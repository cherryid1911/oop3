package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class WarriorTest {

    private Warrior warrior;
    private DummyEnemy enemy;
    private StringBuilder output;

    @Before
    public void setUp() {
        warrior = new Warrior("Arthur", 300, 50, 10, 3);
        output = new StringBuilder();
        warrior.initialize(msg -> output.append(msg).append("\n"));

        enemy = new DummyEnemy("Dummy", 100);
        enemy.setPosition(new Position(1, 0));
        warrior.setPosition(new Position(0, 0));
        warrior.setEnemies(List.of(enemy));
    }

    @Test
    public void testCastAbilityWithEnemyInRange() {
        warrior.castAbility(warrior);

        String log = output.toString();
        assertTrue(log.contains("Arthur used Avenger's Shield on Dummy"));
        assertTrue(log.contains("healed"));
        assertEquals(3, warrior.getRemainingCooldown());
    }

    @Test
    public void testCastAbilityNoEnemies() {
        warrior.setEnemies(List.of());
        warrior.castAbility(warrior);

        String log = output.toString();
        assertTrue(log.contains("no enemies are in range"));
        assertEquals(0, warrior.getRemainingCooldown());
    }

    @Test
    public void testCastAbilityOnCooldown() {
        warrior.castAbility(warrior);
        output.setLength(0); // clear old output

        warrior.castAbility(warrior); // immediately try again
        String log = output.toString();
        assertTrue(log.contains("it's on cooldown"));
    }

    @Test
    public void testOnGameTickReducesCooldown() {
        warrior.castAbility(warrior);
        assertEquals(3, warrior.getRemainingCooldown());

        warrior.onGameTick();
        assertEquals(2, warrior.getRemainingCooldown());

        warrior.onGameTick();
        warrior.onGameTick();
        assertEquals(0, warrior.getRemainingCooldown());
    }

    @Test
    public void testLevelUpIncreasesStats() {
        warrior.gainExperience(500); // force level-up
        assertTrue(warrior.getCurrentHealth() > 300);
        assertTrue(warrior.getAttack() > 50);
        assertTrue(warrior.getDefense() > 10);
        assertEquals(0, warrior.getRemainingCooldown());
    }

    // DummyEnemy is a basic test double
    private static class DummyEnemy extends Monster {
        public DummyEnemy(String name, int hp) {
            super(name, 'M', hp, 0, 0, 10, 5);
        }

        @Override
        public void takeDamage(int dmg) {
            super.takeDamage(dmg);
        }
    }
}
