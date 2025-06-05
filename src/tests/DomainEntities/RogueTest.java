package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RogueTest {

    private Rogue rogue;
    private DummyEnemy enemy1;
    private DummyEnemy enemy2;
    private StringBuilder output;

    @Before
    public void setUp() {
        rogue = new Rogue("Arya", 150, 30, 10, 20); // energyCost = 20
        output = new StringBuilder();
        rogue.initialize(msg -> output.append(msg).append("\n"));
        rogue.setPosition(new Position(0, 0));

        enemy1 = new DummyEnemy("Orc", 40);
        enemy1.setPosition(new Position(1, 0));
        enemy2 = new DummyEnemy("Goblin", 40);
        enemy2.setPosition(new Position(0, 1));

        rogue.setEnemies(List.of(enemy1, enemy2));
    }

    @Test
    public void testCastAbilityWithEnoughEnergy() {
        rogue.castAbility(rogue);
        String log = output.toString();
        assertTrue(log.contains("used Fan of Knives"));
        assertTrue(log.contains("Hit Orc") || log.contains("Hit Goblin"));
        assertTrue(rogue.getCurrentEnergy() < 100);
    }

    @Test
    public void testCastAbilityWithoutEnoughEnergy() {
        // drain energy
        for (int i = 0; i < 6; i++)
            rogue.castAbility(rogue);

        output.setLength(0);
        rogue.castAbility(rogue);
        String log = output.toString();
        assertTrue(log.contains("doesn't have enough energy"));
    }

    @Test
    public void testEnergyRegenerationOnTick() {
        rogue.castAbility(rogue);
        int before = rogue.getCurrentEnergy();
        rogue.onGameTick();
        assertTrue(rogue.getCurrentEnergy() > before);
    }

    @Test
    public void testLevelUpIncreasesStatsAndResetsEnergy() {
        rogue.gainExperience(1000); // force level up
        assertEquals(100, rogue.getCurrentEnergy());
        assertTrue(rogue.getAttack() > 30);
    }

    // DummyEnemy class
    private static class DummyEnemy extends Monster {
        public DummyEnemy(String name, int hp) {
            super(name, 'M', hp, 0, 0, 10, 5);
        }

        @Override
        public void takeDamage(int damage) {
            super.takeDamage(damage);
        }
    }
}
