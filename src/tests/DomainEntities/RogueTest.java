package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RogueTest {

    private TestRogue rogue;
    private MockMessageCallback callback;

    @Before
    public void setUp() {
        rogue = new TestRogue("Arya", new Position(1, 1), 150, 40, 2, 20);
        callback = new MockMessageCallback();
        rogue.setMessageCallback(callback);
    }

    @Test
    public void testInitialEnergy() {
        assertEquals(100, rogue.getCurrentEnergy());
    }

    @Test
    public void testOnGameTickRegeneratesEnergy() {
        rogue.setCurrentEnergy(50);
        rogue.onGameTick();
        assertEquals(60, rogue.getCurrentEnergy());
    }

    @Test
    public void testCastAbility_NoEnergy() {
        rogue.setCurrentEnergy(0);
        rogue.setEnemies(new ArrayList<>());
        rogue.castAbility();
        assertTrue(callback.contains("doesn't have enough energy"));
    }

    @Test
    public void testCastAbility_NoEnemies() {
        rogue.setEnemies(new ArrayList<>());
        rogue.castAbility();
        assertTrue(callback.contains("no enemies were hit"));
    }

    @Test
    public void testCastAbility_HitsAndKillsEnemies() {
        rogue.setCurrentEnergy(100);
        List<Unit> enemies = List.of(new TestEnemy("Target", 5));
        rogue.setEnemies(enemies);
        rogue.castAbility();
        assertTrue(callback.contains("used Fan of Knives"));
        assertTrue(callback.contains("Hit Target"));
        assertTrue(callback.contains("Target died"));
    }

    @Test
    public void testLevelUp_ResetsEnergyAndIncreasesAttack() {
        int baseAtk = rogue.getAttack();
        rogue.gainExperience(100);
        assertEquals(100, rogue.getCurrentEnergy());
        assertTrue(rogue.getAttack() > baseAtk);
    }

    private static class TestRogue extends Rogue {
        private List<Unit> enemies;

        public TestRogue(String name, Position pos, int hp, int atk, int def, int energyCost) {
            super(name, pos, hp, atk, def, energyCost);
        }

        public void setEnemies(List<Unit> enemies) {
            this.enemies = enemies;
        }

        public void setCurrentEnergy(int energy) {
            this.currentEnergy = energy;
        }

        public int getCurrentEnergy() {
            return currentEnergy;
        }

        @Override
        protected List<Unit> getEnemiesInRange(int range) {
            return enemies;
        }
    }

    private static class TestEnemy extends Unit {
        public TestEnemy(String name, int hp) {
            super(name, 'e', new Position(0, 0), hp, 0, 0);
        }

        @Override public void onGameTick() {}
        @Override public String description() { return name; }
        @Override public void accept(Unit other) {}
        @Override public void visit(Player p) {}
        @Override public void visit(Monster m) {}
        @Override public void visit(Trap t) {}
        @Override public void visit(WallTile w) {}
        @Override public void visit(EmptyTile e) {}
        @Override public int rollDefense() { return 0; }
    }

    private static class MockMessageCallback implements MessageCallback {
        List<String> messages = new ArrayList<>();
        @Override public void send(String msg) { messages.add(msg); }
        public boolean contains(String fragment) {
            return messages.stream().anyMatch(msg -> msg.contains(fragment));
        }
    }
}
