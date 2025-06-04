package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WarriorTest {

    private TestWarrior warrior;
    private MockMessageCallback callback;

    @Before
    public void setUp() {
        warrior = new TestWarrior("Jon", new Position(1, 1),
                300, 30, 5, 3);
        callback = new MockMessageCallback();
        warrior.setMessageCallback(callback);
    }

    @Test
    public void testCooldownStartsAtZero() {
        assertEquals(0, warrior.getRemainingCooldown());
    }

    @Test
    public void testOnGameTick_DecreasesCooldown() {
        warrior.setRemainingCooldown(2);
        warrior.onGameTick();
        assertEquals(1, warrior.getRemainingCooldown());
    }

    @Test
    public void testCastAbility_OnCooldown() {
        warrior.setRemainingCooldown(1);
        warrior.setEnemies(List.of(new TestEnemy("Dummy", 100)));
        warrior.castAbility();
        assertTrue(callback.contains("it's on cooldown"));
    }

    @Test
    public void testCastAbility_NoEnemies() {
        warrior.setEnemies(List.of());
        warrior.castAbility();
        assertTrue(callback.contains("no enemies are in range"));
    }

    @Test
    public void testCastAbility_DealsDamageAndHeals() {
        warrior.setEnemies(List.of(new TestEnemy("Dummy", 50)));
        warrior.setRemainingCooldown(0);
        warrior.setCurrentHealth(100);
        warrior.castAbility();
        assertTrue(callback.contains("used Avenger's Shield on Dummy"));
        assertTrue(warrior.getCurrentHealth() > 100);
        assertEquals(warrior.getAbilityCooldown(), warrior.getRemainingCooldown());
    }

    @Test
    public void testLevelUp_ResetsCooldownAndIncreasesStats() {
        warrior.setRemainingCooldown(2);
        int oldHP = warrior.getHealthPool();
        int oldAtk = warrior.getAttack();
        int oldDef = warrior.getDefense();
        warrior.gainExperience(100);
        assertEquals(0, warrior.getRemainingCooldown());
        assertTrue(warrior.getHealthPool() > oldHP);
        assertTrue(warrior.getAttack() > oldAtk);
        assertTrue(warrior.getDefense() > oldDef);
    }

    private static class TestWarrior extends Warrior {
        private List<Unit> enemies = new ArrayList<>();

        public TestWarrior(String name, Position pos, int hp, int atk, int def, int cooldown) {
            super(name, pos, hp, atk, def, cooldown);
        }

        public void setEnemies(List<Unit> e) {
            enemies = e;
        }

        public void setRemainingCooldown(int cd) {
            this.remainingCooldown = cd;
        }

        public void setCurrentHealth(int hp) {
            this.currentHealth = hp;
        }

        protected List<Unit> getEnemiesInRange(int range) {
            return enemies;
        }
    }

    private static class TestEnemy extends Unit {
        public TestEnemy(String name, int hp) {
            super(name, 'E', new Position(0, 0), hp, 0, 0);
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
