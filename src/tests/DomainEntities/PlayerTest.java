package DomainEntities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    private DummyPlayer player;
    private DummyEnemy enemy;
    private StringBuilder output;

    @Before
    public void setUp() {
        player = new DummyPlayer("Jon", 100, 10, 5);
        output = new StringBuilder();
        player.initialize(msg -> output.append(msg).append("\n"));

        enemy = new DummyEnemy("Skeleton", 15, 10); // 30 HP, 10 XP value
        enemy.setPosition(new Position(1, 0));
    }

    @Test
    public void testGainExperienceAndLevelUp() {
        int oldLevel = player.getLevel();
        player.gainExperience(50); // exactly enough to level up

        assertEquals(oldLevel + 1, player.getLevel());
        assertTrue(player.getExperience() < 50); // leftover XP
    }

    @Test
    public void testMultipleLevelUps() {
        int beforeLevel = player.getLevel();
        player.gainExperience(300); // should level up multiple times
        assertTrue(player.getLevel() > beforeLevel);
    }

    @Test
    public void testEngageEnemyAndGainXP() {
        int oldXP = player.getExperience();

        // Force known damage values
        player.setFixedAttackRoll(20);
        enemy.setFixedDefenseRoll(5);

        player.engage(enemy);

        assertTrue(output.toString().contains("engaged Skeleton"));
        assertTrue(player.getExperience() > oldXP);
    }

    @Test
    public void testDeadCharacterGetsTileCharX() {
        player.deadChar();
        assertEquals('X', player.getTileChar());
    }

    // DummyPlayer with fixed attack roll
    private static class DummyPlayer extends Player {
        private int fixedAtk = -1;

        public DummyPlayer(String name, int hp, int atk, int def) {
            super(name, hp, atk, def);
        }

        public void setFixedAttackRoll(int val) {
            fixedAtk = val;
        }

        @Override
        public int rollAttack() {
            return fixedAtk >= 0 ? fixedAtk : super.rollAttack();
        }

        @Override
        public void onGameTick() {}

        @Override
        public void castAbility(Player p) {}

        @Override
        public String description() {
            return "Dummy";
        }
    }

    // DummyEnemy with fixed defense roll
    private static class DummyEnemy extends Monster {
        private int fixedDef = -1;

        public DummyEnemy(String name, int hp, int xpValue) {
            super(name, 'E', hp, 0, 0, xpValue, 5);
        }

        public void setFixedDefenseRoll(int val) {
            fixedDef = val;
        }

        @Override
        public int rollDefense() {
            return fixedDef >= 0 ? fixedDef : super.rollDefense();
        }
    }
}
