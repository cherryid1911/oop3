package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MageTest {

    private Mage mage;
    private DummyEnemy enemy;
    private StringBuilder output;

    @Before
    public void setUp() {
        mage = new Mage("Gandalf", 100, 10, 5, 100, 30, 40, 2, 3);
        output = new StringBuilder();
        mage.initialize(msg -> output.append(msg).append("\n"));

        enemy = new DummyEnemy("Orc", 50);
        enemy.setPosition(new Position(1, 1));
        mage.setPosition(new Position(0, 0));
        mage.setEnemies(List.of(enemy));
    }

    @Test
    public void testCastAbilityWithEnoughMana() {
        mage.castAbility(mage);
        String msg = output.toString();
        assertTrue("Expected ability cast output, but got:\n" + msg,
                msg.contains("Gandalf hits") || msg.contains("tried to cast Blizzard"));
    }

    @Test
    public void testCastAbilityNotEnoughMana() {
        mage = new Mage("Merlin", 100, 10, 5, 50, 40, 20, 1, 3);
        output = new StringBuilder();
        mage.initialize(msg -> output.append(msg).append("\n"));

        mage.castAbility(mage);
        assertTrue(output.toString().contains("doesn't have enough mana"));
    }

    @Test
    public void testManaRegenOnTick() {
        int before = mage.getCurrentMana();
        mage.onGameTick();
        assertTrue(mage.getCurrentMana() > before);
    }

    @Test
    public void testLevelUpIncreasesStats() {
        int beforeManaPool = mage.getCurrentMana();
        mage.gainExperience(1000); // trigger multiple level-ups
        assertTrue(mage.getSpellPower() > 40);
        assertTrue(mage.getCurrentMana() >= beforeManaPool);
    }

    // DummyEnemy is a simple mock class for testing
    private static class DummyEnemy extends Monster {
        public DummyEnemy(String name, int healthPool) {
            super(name, 'E', healthPool, 0, 0, 10, 5);
        }

        @Override
        public void takeDamage(int damage) {
            super.takeDamage(damage);
        }
    }
}
