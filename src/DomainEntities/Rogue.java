package DomainEntities;

import java.util.ArrayList;
import java.util.List;

public class Rogue extends Player {

    // _____Fields_____
    private final int energyCost;
    protected int currentEnergy;
    private final int maxEnergy = 100;


    //_____Constructor_____
    public Rogue(String name, int healthPool, int attack, int defense, int energyCost) {
        super(name, healthPool, attack, defense);
        this.energyCost = energyCost;
        this.currentEnergy = maxEnergy;
    }


    // _____Methods_____
    @Override
    public void onGameTick() {
        currentEnergy = Math.min(currentEnergy + 10, maxEnergy);
    }

    @Override
    public void castAbility(Player player) {
        if (currentEnergy < energyCost) {
            messageCallback.send(name + " tried to use Fan of Knives, but doesn't have enough energy (" +
                    currentEnergy + "/" + energyCost + ").");
            return;
        }

        List<Enemy> enemies = getEnemiesInRange(2);
        if (enemies.isEmpty()) {
            messageCallback.send(name + " used Fan of Knives, but no enemies were hit.");
        } else {
            messageCallback.send(name + " used Fan of Knives:");
        }

        currentEnergy -= energyCost;

        for (Unit target : enemies) {
            int defenseRoll = target.rollDefense();
            int damage = Math.max(0, attack - defenseRoll);
            target.takeDamage(damage);
            messageCallback.send(String.format(" - Hit %s for %d damage (defense roll: %d)",
                    target.getName(), damage, defenseRoll));
            if (target.isDead()) {
                messageCallback.send("   " + target.getName() + " died!");
            }
        }
    }

    @Override
    protected void levelUp() {
        super.levelUp();
        attack += 3 * level;
        currentEnergy = maxEnergy;
    }

    @Override
    public String description() {
        return String.format("Rogue %s\tLevel %d\tExperience: %d\tHealth: %d/%d\tEnergy: %d/%d\tATK: %d\tDEF: %d",
                name, level, experience, currentHealth, healthPool,
                currentEnergy, maxEnergy, attack, defense);
    }

    public int getCurrentEnergy(){return currentEnergy;}

}