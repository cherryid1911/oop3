package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MageTest {

    private TestMage mage;
    private MockMessageCallback callback;

    @Before
    public void setUp() {
        mage = new TestMage("Gandalf", new Position(1, 1),
                100, 10, 5, 200, 30, 50, 3, 5);
        callback = new MockMessageCallback();
        mage.setMessageCallback(callback);
    }

    @Test
    public void testInitialMana() {
        assertEquals(50, mage.getCurrentMana());
    }

    @Test
    public void testOnGameTickIncreasesMana() {
        int before = mage.getCurrentMana();
        mage.onGameTick();
        assertTrue(mage.getCurrentMana() > before);
    }

    @Test
    public void testLevelUpAffectsManaAndSpellPower() {
        int baseSP = mage.getSpellPower();
        mage.gainExperience(100);
        assertTrue(mage.getSpellPower() > baseSP);
    }

    @Test
    public void testCastAbilityWithNoMana() {
        mage.setCurrentMana(0);
        mage.setEnemies(List.of(new DummyEnemy("Target")));
        mage.castAbility();
        assertTrue(callback.contains("doesn't have enough mana"));
    }

    @Test
    public void testCastAbilityWithNoEnemies() {
        mage.setEnemies(List.of());
        mage.castAbility();
        assertTrue(callback.contains("no enemies are in range"));
    }

    @Test
    public void testCastAbilityHitsEnemies() {
        mage.setEnemies(new ArrayList<>(List.of(
                new DummyEnemy("Weakling", 1),
                new DummyEnemy("Strong", 100))));
        mage.setCurrentMana(200);
        mage.castAbility();
        assertTrue(callback.contains("hits") || callback.contains("died"));
    }

    private static class TestMage extends Mage {
        private List<Unit> enemies;

        public TestMage(String name, Position pos, int hp, int atk, int def, int manaPool,
                        int manaCost, int sp, int hits, int range) {
            super(name, pos, hp, atk, def, manaPool, manaCost, sp, hits, range);
        }

        public void setEnemies(List<Unit> enemies) {
            this.enemies = enemies;
        }

        public void setCurrentMana(int mana) {
            this.currentMana = mana;
        }

        protected List<Unit> getEnemiesInRange(int range) {
            return enemies;
        }
    }

    private static class DummyEnemy extends Unit {
        public DummyEnemy(String name) {
            this(name, 10);
        }

        public DummyEnemy(String name, int hp) {
            super(name, 'e', new Position(0, 0), hp, 0, 0);
        }

        @Override public void onGameTick() {}
        @Override public String description() { return name; }
        @Override public void accept(Unit o) {}
        @Override public void visit(Player p) {}
        @Override public void visit(Monster m) {}
        @Override public void visit(Trap t) {}
        @Override public void visit(EmptyTile e) {}
        @Override public int rollDefense() { return 0; }
    }

    private static class MockMessageCallback implements MessageCallback {
        private final List<String> messages = new ArrayList<>();
        @Override public void send(String message) { messages.add(message); }
        public boolean contains(String fragment) {
            return messages.stream().anyMatch(m -> m.contains(fragment));
        }
    }
}
