package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HunterTest {

    private Hunter hunter;
    private DummyEnemy closeEnemy;
    private DummyEnemy farEnemy;
    private StringBuilder output;

    @Before
    public void setUp() {
        hunter = new Hunter("Legolas", 100, 30, 5, 3); // range = 3
        output = new StringBuilder();
        hunter.initialize(msg -> output.append(msg).append("\n"));
        hunter.setPosition(new Position(0, 0));

        closeEnemy = new DummyEnemy("Orc", 50);
        closeEnemy.setPosition(new Position(1, 0));

        farEnemy = new DummyEnemy("Troll", 100);
        farEnemy.setPosition(new Position(5, 5));

        hunter.setEnemies(List.of(closeEnemy, farEnemy));
    }

    @Test
    public void testCastAbilityWithArrows() {
        int arrowsBefore = getArrowCount();
        hunter.castAbility(hunter);

        String log = output.toString();
        assertTrue(log.contains("shot Orc") || log.contains("shot Troll"));
        assertEquals(arrowsBefore - 1, getArrowCount());
    }

    @Test
    public void testCastAbilityNoEnemiesInRange() {
        // move both enemies out of range
        closeEnemy.setPosition(new Position(10, 10));
        hunter.castAbility(hunter);

        assertTrue(output.toString().contains("no enemies in range"));
    }

    @Test
    public void testCastAbilityNoArrows() {
        closeEnemy.setPosition(new Position(1, 1));
        hunter.setEnemies(List.of(closeEnemy));

        for (int i = 0; i < 10; i++) {
            closeEnemy.currentHealth = 9999; // keep enemy alive
            hunter.castAbility(hunter);
        }
        output.setLength(0);

        hunter.castAbility(hunter);
        assertTrue(output.toString().contains("no arrows"));
    }

    @Test
    public void testArrowRegenEvery10Ticks() {
        closeEnemy.setPosition(new Position(1, 0));
        hunter.setEnemies(List.of(closeEnemy));
        hunter.castAbility(hunter); // spend 1 arrow

        int before = getArrowCount();
        for (int i = 0; i < 11; i++) { // ⬅️ 11 not 10
            hunter.onGameTick();
        }

        int after = getArrowCount();
        assertTrue("Expected arrow regeneration, but before=" + before + ", after=" + after, after > before);
    }



    @Test
    public void testLevelUpGivesMoreArrowsAndStats() {
        int beforeArrows = getArrowCount();
        int beforeAttack = hunter.getAttack();
        int beforeDefense = hunter.getDefense();

        hunter.gainExperience(1000);

        assertTrue(getArrowCount() > beforeArrows);
        assertTrue(hunter.getAttack() > beforeAttack);
        assertTrue(hunter.getDefense() > beforeDefense);
    }

    // Access private arrow count via reflection
    private int getArrowCount() {
        try {
            var field = Hunter.class.getDeclaredField("arrowsCount");
            field.setAccessible(true);
            return field.getInt(hunter);
        } catch (Exception e) {
            fail("Could not access arrowsCount: " + e.getMessage());
            return -1;
        }
    }

    // Dummy enemy
    private static class DummyEnemy extends Monster {
        public DummyEnemy(String name, int hp) {
            super(name, 'E', hp, 0, 0, 10, 5);
        }
    }
}
