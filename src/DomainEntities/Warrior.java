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

        remainingCooldown = abilityCooldown+1;

        messageCallback.send(String.format(
                "%s used Avenger's Shield on %s for %d damage and healed for %d.",
                name, target.getName(), damage, heal));

        if (target.isDead()) {
            target.tileChar = '.';
            gainExperience(target.getExperienceValue());
            messageCallback.send(name + " gained " + target.getExperienceValue() + " xp points.");
        }
    }

    protected void levelUp() {
        super.levelUp();
        messageCallback.send(name+ " leveled up to level "+level);
        remainingCooldown = 0;
        healthPool += 5 * level;
        attack += 2 * level;
        defense += level;
        currentHealth = healthPool;
    }

    public String description() {
        return String.format("Warrior %s\tLevel %d\tExperience: %d/%d\tHealth: %d/%d\tATK: %d\tDEF: %d\tCooldown: %d/%d",
                name, level, experience, 50*level, currentHealth, healthPool, attack, defense, remainingCooldown, abilityCooldown);
    }

    public int getRemainingCooldown(){
        return remainingCooldown;
    }



}
