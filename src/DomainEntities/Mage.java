package DomainEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    // _____Fields_____
    private int manaPool;
    private int currentMana;
    private final int manaCost;
    private int spellPower;
    private final int hitsCount;
    private final int abilityRange;


    //_____Constructor_____
    public Mage(String name, Position position, int healthPool, int attack, int defense, int manaPool, int manaCost, int spellPower, int hitsCount, int abilityRange) {
        super(name, '@', position, healthPool, attack, defense);
        this.manaPool = manaPool;
        this.currentMana = manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }


    // _____Methods_____
    @Override
    public void onGameTick() {
        currentMana = Math.min(manaPool, currentMana + level);
    }

    @Override
    public void castAbility() {
        if (currentMana < manaCost) {
            messageCallback.send(name + " tried to cast Blizzard, but doesn't have enough mana (" + currentMana + "/" + manaCost + ").");
            return;
        }

        List<Unit> enemies = getEnemiesInRange(abilityRange);
        if (enemies.isEmpty()) {
            messageCallback.send(name + " tried to cast Blizzard, but no enemies are in range.");
            return;
        }

        currentMana -= manaCost;
        int hits = 0;
        Random rand = new Random();

        while (hits < hitsCount && !enemies.isEmpty()) {
            Unit target = enemies.get(rand.nextInt(enemies.size()));
            int defenseRoll = target.rollDefense();
            int damage = Math.max(0, spellPower - defenseRoll);
            target.takeDamage(damage);
            messageCallback.send(String.format("%s hits %s for %d damage (defense roll: %d)",
                    name, target.getName(), damage, defenseRoll));
            if (target.isDead()) {
                messageCallback.send(target.getName() + " died!");
                // אפשר להסיר אותו מהרשימה:
                enemies.remove(target);
            }
            hits++;
        }
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        manaPool += 25 * level;
        currentMana = Math.min(currentMana + manaPool / 4, manaPool);
        spellPower += 10 * level;
    }

    @Override
    public String description() {
        return String.format("Mage %s\tLevel %d\tXP: %d\tHP: %d/%d\tMana: %d/%d\tSP: %d",
                name, level, experience, currentHealth, healthPool, currentMana, manaPool, spellPower);
    }

    @Override
    public void accept(Unit other) {}


    /// PLACEHOLDER
    private List<Unit> getEnemiesInRange(int range) {
        throw new UnsupportedOperationException("getEnemiesInRange should be set from Game context.");
    }


}