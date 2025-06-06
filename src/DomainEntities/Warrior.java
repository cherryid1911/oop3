package DomainEntities;

import java.util.List;
import java.util.Random;

public class Warrior extends Player {

    // _____Fields_____
    private final int abilityCooldown;
    protected int remainingCooldown;


    //_____Constructor_____
    public Warrior(String name, int healthPool, int attack, int defense, int abilityCooldown) {
        super(name, healthPool, attack, defense);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }


    // _____Methods_____
    public void onGameTick() {
        if (remainingCooldown > 0)
            remainingCooldown--;
    }

    public void castAbility(Player player) {
        if (remainingCooldown > 0) {
            messageCallback.send(name + " tried to use Avenger's Shield but it's on cooldown (" + remainingCooldown + " turns left).");
            return;
        }

        List<Enemy> enemiesInRange = getEnemiesInRange(3);
        if (enemiesInRange.isEmpty()) {
            messageCallback.send(name + " tried to use Avenger's Shield, but no enemies are in range.");
            return;
        }

        Unit target = enemiesInRange.get(new Random().nextInt(enemiesInRange.size()));
        int damage = (int)(0.1 * healthPool);
        target.takeDamage(damage);

        int heal = 10 * defense;
        currentHealth = Math.min(currentHealth + heal, healthPool);

        remainingCooldown = abilityCooldown;

        messageCallback.send(String.format(
                "%s used Avenger's Shield on %s for %d damage and healed for %d.",
                name, target.getName(), damage, heal));
    }

    protected void levelUp() {
        super.levelUp();
        remainingCooldown = 0;
        healthPool += 5 * level;
        attack += 2 * level;
        defense += level;
        currentHealth = healthPool;
    }

    public String description() {
        return String.format("Warrior %s\tLevel %d\tExperience: %d\tHealth: %d/%d\tATK: %d\tDEF: %d\tCooldown: %d",
                name, level, experience, currentHealth, healthPool, attack, defense, remainingCooldown);
    }

    public int getAbilityCooldown(){
        return abilityCooldown;
    }

    public int getRemainingCooldown(){
        return remainingCooldown;
    }



}
